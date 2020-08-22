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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

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
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DisbursementFragment extends Fragment{

    private TextView disbursement_date;
    private Spinner disbursement_dept;
    private Button disbursement_search;
    private ListView disbursement_list;

    private Context context;
    private MyAdapter<Department> spinnerItemMyAdapter;
    private MyAdapter<RequisitionDetail> disbursementAdapter;

    String dept_select;
    int mYear;
    int mMonth ;
    int mDay;
    String selectDay=null;

    private int tableRowRenderTime=0;

    public DisbursementFragment() {
        // Required empty public constructor
    }


    public static DisbursementFragment newInstance(String param1, String param2) {
        DisbursementFragment fragment = new DisbursementFragment();
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
        View view=inflater.inflate(R.layout.fragment_disbursement, container, false);
        context= ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void getAllDept(){
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_ALL_DEPARTMENT,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    List<Department> departments = new ArrayList<>();
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        departments.add((Department) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),Department.class));
                                    }
                                    spinnerItemMyAdapter = new MyAdapter<Department>((ArrayList) departments,R.layout.item_spin_dept) {
                                        @Override
                                        public void bindView(ViewHolder holder, Department obj) {
                                            holder.setText(R.id.dept_name, obj.getName());
                                            holder.setText(R.id.dept_id,obj.getId());
                                        }
                                    };
                                    disbursement_dept.setAdapter(spinnerItemMyAdapter);
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

    private void init(View view){
        disbursement_dept=(Spinner)view.findViewById(R.id.disbursement_dept);
        disbursement_date=(TextView)view.findViewById(R.id.disbursement_date);
        disbursement_search=(Button)view.findViewById(R.id.disbursement_search);
        disbursement_list=(ListView)view.findViewById(R.id.disbursement_list);

        getAllDept();

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

        disbursement_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.dept_id);
                dept_select=textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        disbursement_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectDay==null||selectDay.equals("")){
                    Toast.makeText(context,"please select a date",Toast.LENGTH_LONG).show();
                    return;
                }
                Requisition requisition=new Requisition();
                requisition.setDepartmentId(dept_select);
                requisition.setCollectionDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay));
                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST, CommonConstant.HttpUrl.GET_DISBURSEMENT,
                                new JSONObject(EntityUtil.object2Map(requisition)),new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                        if(result.getCode()==200){
                                            final List<RequisitionDetail> requisitionDetails=new ArrayList<>();
                                            for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                                requisitionDetails.add((RequisitionDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),RequisitionDetail.class));
                                            }
                                            final int renderSize=requisitionDetails.size();
                                            disbursementAdapter = new MyAdapter<RequisitionDetail>((ArrayList) requisitionDetails,R.layout.item_disbursement) {
                                                @Override
                                                public void bindView(ViewHolder holder, RequisitionDetail obj) {
                                                    holder.setText(R.id.item_code,obj.getProduct().getId());
                                                    holder.setText(R.id.item_description,obj.getProduct().getDescription());
                                                    holder.setText(R.id.qty,obj.getQtyNeeded()+"");
                                                    holder.setText(R.id.qty_received,obj.getQtyReceived()+"");
                                                    holder.setText(R.id.qty_disbursed,obj.getQtyDisbursed()+"");

                                                }
                                            };
                                            disbursement_list.setAdapter(disbursementAdapter);
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
    }

}