package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.transition.Visibility;
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
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import iss.team1.ad.ssis_android.modal.Department;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class AckReceiveFragment extends Fragment {

    private TextView disbursement_date;
    private Button ack_filter_btn;
    private ListView disbursement_list;

    private MyAdapter<RequisitionDetail> disbursementsAdapter;

    int mYear;
    int mMonth ;
    int mDay;
    String selectDay=null;
    private int tableRowRenderTime=0;

    private Context context;


    public AckReceiveFragment() {
        // Required empty public constructor
    }

    public static AckReceiveFragment newInstance(String param1, String param2) {
        AckReceiveFragment fragment = new AckReceiveFragment();
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
        View view=inflater.inflate(R.layout.fragment_ack_receive, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        disbursement_date=(TextView)view.findViewById(R.id.disbursement_date);
        disbursement_list=(ListView)view.findViewById(R.id.disbursement_list);
        ack_filter_btn=(Button)view.findViewById(R.id.ack_filter_btn);

        context= ApplicationUtil.getContext();


        ack_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectDay==null||selectDay.equals("")){
                    Toast.makeText(context,"please select a date",Toast.LENGTH_LONG).show();
                    return;
                }
                tableRowRenderTime=0;
                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_DEPT_DISBURSEMENT_DETAIL(TimeUtil.convertyyyyMMddToTimestamp(selectDay)),
                                new JSONObject(),new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                        if(result.getCode()==200){
                                            List<RequisitionDetail> requisitionDetails = new ArrayList<>();
                                            for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                                requisitionDetails.add((RequisitionDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),RequisitionDetail.class));
                                            }
                                            final int size = requisitionDetails.size();
                                            disbursementsAdapter = new MyAdapter<RequisitionDetail>((ArrayList) requisitionDetails,R.layout.item_disbursement) {
                                                @Override
                                                public void bindView(ViewHolder holder, RequisitionDetail obj) {
                                                    if(tableRowRenderTime<size){
                                                        holder.setText(R.id.item_desc, obj.getProduct().getDescription());
                                                        holder.setText(R.id.qty_dis,obj.getQtyDisbursed()+"");
                                                        holder.setText(R.id.qty_req,obj.getQtyNeeded()+"");
                                                        if(!StringUtil.isEmpty(obj.getRequisition().getRemarks())){
                                                            holder.setVisibility(R.id.remarks_title, View.INVISIBLE);
                                                        }else{
                                                            holder.setText(R.id.remarks,obj.getRequisition().getRemarks());
                                                        }
                                                        holder.setOnClickListener(R.id.qty_rec, new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                showInputDialog(view);
                                                            }
                                                        });
                                                        tableRowRenderTime++;
                                                    }
                                                }
                                            };
                                            disbursement_list.setAdapter(disbursementsAdapter);
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
        });

        disbursement_date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View arg0) {
                Calendar mCalendar=Calendar.getInstance(Locale.CHINA);
                int year=mCalendar.get(Calendar.YEAR);
                int month=mCalendar.get(Calendar.MARCH);
                int day=mCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1).append("-")
                                        .append(mDay).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1).append("-")
                                        .append(mDay)
                                        .toString();
                            }
                        }
                        disbursement_date.setText(selectDay);

                    }
                };
                new DatePickerDialog(getActivity(), onDateSetListener, year,
                        month, day).show();
            }
        });
    }

    private void showInputDialog(final View view){
        new XPopup.Builder(getContext()).asInputConfirm("Quantity Receive", "",
                new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if(!StringUtil.isNumeric(text)){
                            Toast.makeText(context,"please input a numberic",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            ((TextView)view).setText(text);
                        }
                    }
                })
                .show();
    }
}