package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.DeptheadRequisitionDetailActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.StringUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DeptHeadRequisitionFragment extends Fragment {


    private TextView depthead_requisition_date;
    private ListView depthead_requisition_list;
    private SwipeRefreshLayout depthead_requisition_swipl;

    private List<Requisition> requisitions=new ArrayList<>();
    private MyAdapter<Requisition> requisitionMyAdapter;
    private Context context;

    int mYear;
    int mMonth ;
    int mDay;
    String selectDay=null;

    public DeptHeadRequisitionFragment() {
    }


    public static DeptHeadRequisitionFragment newInstance(String param1, String param2) {
        DeptHeadRequisitionFragment fragment = new DeptHeadRequisitionFragment();
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
        View view=inflater.inflate(R.layout.fragment_dept_head_requisition, container, false);
        context= ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void init(View view){
        depthead_requisition_list=(ListView)view.findViewById(R.id.depthead_requisition_list);
        depthead_requisition_date=(TextView)view.findViewById(R.id.depthead_requisition_date);
        depthead_requisition_swipl=view.findViewById(R.id.depthead_requisition_swipl);

        depthead_requisition_swipl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRequisition();
            }
        });

        fetchRequisition();

        depthead_requisition_date.setOnClickListener(new View.OnClickListener() {

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
                        depthead_requisition_date.setText(selectDay);
                        filterRequisitions(selectDay);
                    }
                };
                new DatePickerDialog(getActivity(), onDateSetListener, year,
                        month, day).show();
            }
        });

    }

    private void setAdapter(List<Requisition> requisitionList){
        requisitionMyAdapter = new MyAdapter<Requisition>((ArrayList) requisitionList,R.layout.item_dept_head_requisition) {
            @Override
            public void bindView(ViewHolder holder, Requisition obj) {
                holder.setText(R.id.item_dept_head_requisition_form_id,obj.getId()+"");
                holder.setText(R.id.item_dept_head_requisition_requested_date,TimeUtil.convertTimestampToyyyyMMdd(obj.getCreatedDate()));
                holder.setText(R.id.item_dept_head_requisition_requested_by,obj.getReqByEmp().getName());
                holder.setText(R.id.item_dept_head_requisition_status,obj.getStatus());

                holder.setOnClickListener(R.id.item_dept_head_requisition_row, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = (String) ((TextView) holder.getView(R.id.item_dept_head_requisition_form_id)).getText();
                        Intent intent=new Intent(context, DeptheadRequisitionDetailActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });
            }
        };
        depthead_requisition_list.setAdapter(requisitionMyAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filterRequisitions(String date){
        List<Requisition> collect = requisitions.stream()
                .filter(item -> item.getCreatedDate() == TimeUtil.convertyyyyMMddToTimestamp(date))
                .collect(Collectors.toList());
        setAdapter(collect);
    }

    private void fetchRequisition(){
        requisitions=new ArrayList<>();
        depthead_requisition_swipl.setRefreshing(true);
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_DEPT_HEAD_REQUISITIONS,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                depthead_requisition_swipl.setRefreshing(false);
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    for(int i = 0; i<((ArrayList)result.getData()).size(); i++){
                                        requisitions.add((Requisition) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),Requisition.class));
                                    }
                                    setAdapter(requisitions);

                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                depthead_requisition_swipl.setRefreshing(false);
                                Toast.makeText(context,"invalid token",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }
}