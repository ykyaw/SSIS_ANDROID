package iss.team1.ad.ssis_android.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.call.CallState;
import iss.team1.ad.ssis_android.components.call.CallStateListener;
import iss.team1.ad.ssis_android.fragment.DelegateFragment;
import iss.team1.ad.ssis_android.modal.Employee;

public class SelectDelegateTimeRange extends BottomPopupView {

    ListView recyclerView;
    private ArrayList<Employee> data;
    private MyAdapter<Employee> commonAdapter;


    CallState callState=new CallState();

    private Context context;

    public SelectDelegateTimeRange(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public SelectDelegateTimeRange(@NonNull Context context, ArrayList<Employee> data, CallStateListener callStateListener){
        super(context);
        this.data=data;
        for(Employee emp : this.data){
            emp.setDelegateToDate(null);
            emp.setDelegateFromDate(null);
        }
        callState.registerObserver(callStateListener);
        this.context=context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.customize_delegate_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recyclerView);

        commonAdapter = new MyAdapter<Employee>(data, R.layout.item_select_delegate_time_range) {
            @Override
            public void bindView(ViewHolder holder, Employee obj) {
                holder.setText(R.id.item_delegate_select_name,obj.getName());

                holder.setOnClickListener(R.id.item_delegate_select_start_time,new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mCalendar= null;
                        int year=0;
                        int month=0;
                        int day=0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            mCalendar = Calendar.getInstance(Locale.CHINA);
                            year=mCalendar.get(Calendar.YEAR);
                            month=mCalendar.get(Calendar.MARCH);
                            day=mCalendar.get(Calendar.DAY_OF_MONTH);
                        }
                        int mYear=0;
                        int mMonth = 0;
                        int mDay=0;
                        View thizView=view;
                        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                String selectDay=null;
                                if (monthOfYear + 1 < 10) {
                                    if (dayOfMonth < 10) {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append("0")
                                                .append(monthOfYear + 1)
                                                .append("-").append("0")
                                                .append(dayOfMonth).toString();
                                    } else {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append("0")
                                                .append(monthOfYear + 1).append("-")
                                                .append(dayOfMonth).toString();
                                    }
                                } else {
                                    if (dayOfMonth < 10) {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append(monthOfYear + 1)
                                                .append("-").append("0")
                                                .append(dayOfMonth).toString();
                                    } else {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append(monthOfYear + 1).append("-")
                                                .append(dayOfMonth)
                                                .toString();
                                    }
                                }
                                ((TextView)thizView).setText(selectDay);
                                data.get(holder.getItemPosition()).setDelegateFromDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay));
                            }
                        };
                        new DatePickerDialog(context, onDateSetListener, year,
                                month, day).show();
                    }
                });
                holder.setOnClickListener(R.id.item_delegate_select_end_time,new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mCalendar= null;
                        int year=0;
                        int month=0;
                        int day=0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            mCalendar = Calendar.getInstance(Locale.CHINA);
                            year=mCalendar.get(Calendar.YEAR);
                            month=mCalendar.get(Calendar.MARCH);
                            day=mCalendar.get(Calendar.DAY_OF_MONTH);
                        }
                        int mYear=0;
                        int mMonth = 0;
                        int mDay=0;
                        View thizView=view;
                        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                String selectDay=null;
                                if (monthOfYear + 1 < 10) {
                                    if (dayOfMonth < 10) {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append("0")
                                                .append(monthOfYear + 1)
                                                .append("-").append("0")
                                                .append(dayOfMonth).toString();
                                    } else {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append("0")
                                                .append(monthOfYear + 1).append("-")
                                                .append(dayOfMonth).toString();
                                    }
                                } else {
                                    if (dayOfMonth < 10) {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append(monthOfYear + 1)
                                                .append("-").append("0")
                                                .append(dayOfMonth).toString();
                                    } else {
                                        selectDay = new StringBuffer().append(year)
                                                .append("-").append(monthOfYear + 1).append("-")
                                                .append(dayOfMonth)
                                                .toString();
                                    }
                                }
                                ((TextView)thizView).setText(selectDay);
                                data.get(holder.getItemPosition()).setDelegateToDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay)+1000*60*60*24-1000);

                            }
                        };
                        new DatePickerDialog(context, onDateSetListener, year,
                                month, day).show();
                    }
                });
                holder.setOnClickListener(R.id.item_delegate_select_assign_btn, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Employee currentItem = data.get(holder.getItemPosition());
                        if(currentItem.getDelegateToDate()==null||currentItem.getDelegateFromDate()==null){
                            Toast.makeText(context,"please set the delegate time range",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(currentItem.getDelegateToDate()<=currentItem.getDelegateFromDate()){
                            Toast.makeText(context,"the end date can not less than start date",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(currentItem.getDelegateToDate()<=System.currentTimeMillis()){
                            Toast.makeText(context,"the end date can not less than today",Toast.LENGTH_LONG).show();
                            return;
                        }
                        HttpUtil.getInstance()
                                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.ASSIGN_DELEGATE_EMP,
                                        new JSONObject(EntityUtil.object2Map(currentItem)),new Response.Listener<JSONObject>(){
                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                                if(result.getCode()==200){
                                                    Toast.makeText(context,"assign successfully",Toast.LENGTH_LONG).show();
                                                    callState.notifyObserver(true);
                                                }else{
                                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
//                                                    callState.notifyObserver(false);
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context,"invalid token",Toast.LENGTH_LONG).show();
                                                System.out.println("error");
                                                error.printStackTrace();
                                                System.out.println(error.getMessage());

                                            }
                                        });
                    }
                });
            }

        };

        recyclerView.setAdapter(commonAdapter);
    }



    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
    }


}
