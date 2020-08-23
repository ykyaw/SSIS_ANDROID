package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.StringUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.components.SelectDelegateTimeRange;
import iss.team1.ad.ssis_android.components.call.CallState;
import iss.team1.ad.ssis_android.components.call.CallStateListener;
import iss.team1.ad.ssis_android.modal.Department;
import iss.team1.ad.ssis_android.modal.Employee;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DelegateFragment extends Fragment implements CallStateListener {


    private ListView delegate_list;
    private TextView delegate_non_delegate_tip;
    private Button delegate_assign_btn;

    private MyAdapter<Employee> delegateEmployeeAdapter;
    private Context context;

    private List<Employee> delegates=new ArrayList<>();
    private List<Employee> nonDelegates=new ArrayList<>();

    private int tableRowRenderTime=0;
    private BasePopupView popupView=null;

    public DelegateFragment() {
        // Required empty public constructor
    }


    public static DelegateFragment newInstance(String param1, String param2) {
        DelegateFragment fragment = new DelegateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_delegate, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        delegate_list=(ListView)view.findViewById(R.id.delegate_list);
        delegate_non_delegate_tip=(TextView)view.findViewById(R.id.delegate_non_delegate_tip);
        delegate_assign_btn=(Button)view.findViewById(R.id.delegate_assign_btn);

        context= ApplicationUtil.getContext();

        fetchDelegate();

        CallStateListener thiz=this;

        delegate_assign_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                popupView = new XPopup.Builder(getContext())
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(new SelectDelegateTimeRange(getContext(), (ArrayList<Employee>) nonDelegates, thiz)/*.enableDrag(false)*/)
                        .show();
            }
        });
    }



    private void fetchDelegate(){
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_DEPT_EMP,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    List<Employee> employees = new ArrayList<>();
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        employees.add((Employee) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),Employee.class));
                                    }
                                    delegates = employees.stream()
                                            .filter(emp -> emp.getDelegateToDate()!=null&&emp.getDelegateToDate()!=0&&emp.getDelegateToDate() > System.currentTimeMillis())
                                            .collect(Collectors.toList());
                                    nonDelegates=employees.stream()
                                            .filter(emp->emp.getDelegateToDate()==null||emp.getDelegateToDate()==0||emp.getDelegateToDate()<System.currentTimeMillis())
                                            .collect(Collectors.toList());
                                    setDelegateAdapter();
                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
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

    private void setDelegateAdapter(){
        if(delegateEmployeeAdapter!=null){
            delegateEmployeeAdapter.clear();
        }
        if(delegates.size()==0){
            delegate_non_delegate_tip.setVisibility(View.VISIBLE);
            return;
        }
        delegate_non_delegate_tip.setVisibility(View.INVISIBLE);
        tableRowRenderTime=0;
        delegateEmployeeAdapter = new MyAdapter<Employee>((ArrayList) delegates,R.layout.item_delegate) {
            @Override
            public void bindView(final ViewHolder holder, Employee obj) {
                if(tableRowRenderTime<delegates.size()){
                    holder.setText(R.id.item_delegate_name,obj.getName());
                    holder.setText(R.id.item_delegate_start_date, TimeUtil.convertTimestampToyyyyMMdd(obj.getDelegateFromDate()));
                    holder.setText(R.id.item_delegate_end_date,TimeUtil.convertTimestampToyyyyMMdd(obj.getDelegateToDate()));
                    ViewHolder thisHolder=holder;
                    holder.setOnClickListener(R.id.item_delegate_update_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Employee currentItem = delegates.get(thisHolder.getItemPosition());
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
                                                        Toast.makeText(context,"update successfully",Toast.LENGTH_LONG).show();
//                                                        callState.notifyObserver(true);
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

                    holder.setOnClickListener(R.id.item_delegate_delete_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Employee currentItem = delegates.get(thisHolder.getItemPosition());
                            currentItem.setDelegateFromDate(null);
                            currentItem.setDelegateToDate(null);
                            HttpUtil.getInstance()
                                    .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.ASSIGN_DELEGATE_EMP,
                                            new JSONObject(EntityUtil.object2Map(currentItem)),new Response.Listener<JSONObject>(){
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                                    if(result.getCode()==200){
                                                        Toast.makeText(context,"delete successfully",Toast.LENGTH_LONG).show();
                                                        fetchDelegate();
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
                    holder.setOnClickListener(R.id.item_delegate_end_date, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {
                            Calendar mCalendar=Calendar.getInstance(Locale.CHINA);
                            int year=mCalendar.get(Calendar.YEAR);
                            int month=mCalendar.get(Calendar.MARCH);
                            int day=mCalendar.get(Calendar.DAY_OF_MONTH);
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
                                    Employee currentEmp = delegates.get(thisHolder.getItemPosition());
                                    currentEmp.setDelegateToDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay)+1000*60*60*24-1000);

                                }
                            };
                            new DatePickerDialog(getActivity(), onDateSetListener, year,
                                    month, day).show();
                        }
                    });

                    holder.setOnClickListener(R.id.item_delegate_start_date, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {
                            Calendar mCalendar=Calendar.getInstance(Locale.CHINA);
                            int year=mCalendar.get(Calendar.YEAR);
                            int month=mCalendar.get(Calendar.MARCH);
                            int day=mCalendar.get(Calendar.DAY_OF_MONTH);
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
                                    Employee currentEmp = delegates.get(thisHolder.getItemPosition());
                                    currentEmp.setDelegateFromDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay));

                                }
                            };
                            new DatePickerDialog(getActivity(), onDateSetListener, year,
                                    month, day).show();
                        }
                    });
                    tableRowRenderTime++;
                }
            }
        };
        delegate_list.setAdapter(delegateEmployeeAdapter);
    }

    @Override
    public void update(boolean reslut) {
        if(reslut){
            popupView.dismiss();
            fetchDelegate();
        }
    }
}