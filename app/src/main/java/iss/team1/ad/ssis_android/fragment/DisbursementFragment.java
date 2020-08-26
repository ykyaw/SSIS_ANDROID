package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONArray;
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
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DisbursementFragment extends Fragment {

    private TextView disbursement_date;
    private TextView disbursement_dept;
    private Button disbursement_search;
    private ListView disbursement_list;


    //XJ
    private List<RequisitionDetail> requisitionDetails = null;
    private TextView received_by_rep;
    private TextView received_date;
    private TextView ack_by_clerk;
    private TextView ack_date;
    private Button clerk_update_remark_button;
    private LinearLayout requisition_info;

    private Context context;
    private MyAdapter<Department> spinnerItemMyAdapter;
    private MyAdapter<RequisitionDetail> disbursementAdapter;


    String dept_select;
    int mYear;
    int mMonth;
    int mDay;
    String selectDay = null;

    private int tableRowRenderTime = 0;
    private List<Department> departments = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.fragment_disbursement, container, false);
        context = ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void getAllDept() {
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_ALL_DEPARTMENT,
                        new JSONObject(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if (result.getCode() == 200) {
                                    departments = new ArrayList<>();
                                    for (int i = 0; i < ((ArrayList) result.getData()).size(); i++) {
                                        departments.add((Department) EntityUtil.map2Object((Map<String, Object>) ((ArrayList) result.getData()).get(i), Department.class));
                                    }
                                } else {
                                    Toast.makeText(context, result.getMsg(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "invalid token", Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }

    private void init(View view) {
        disbursement_dept = (TextView) view.findViewById(R.id.disbursement_dept);
        disbursement_date = (TextView) view.findViewById(R.id.disbursement_date);
        disbursement_search = (Button) view.findViewById(R.id.disbursement_search);
        disbursement_list = (ListView) view.findViewById(R.id.disbursement_list);
        received_by_rep=(TextView) view.findViewById(R.id.received_by_rep);
        received_date=(TextView) view.findViewById(R.id.received_date);
        ack_by_clerk=(TextView) view.findViewById(R.id.ack_by_clerk);
        ack_date=(TextView) view.findViewById(R.id.ack_date);
        clerk_update_remark_button=(Button)view.findViewById(R.id.clerk_update_remark_button) ;
        requisition_info=(LinearLayout)view.findViewById(R.id.requisition_info);
        requisition_info.setVisibility(View.INVISIBLE);
        getAllDept();


        disbursement_dept.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getContext())
                        .hasShadowBg(false)
                        .isDestroyOnDismiss(false)
                        .atView(disbursement_dept)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(departments.stream().map(item -> item.getName()).toArray(String[]::new),
                                new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        disbursement_dept.setText(text);
                                        dept_select = departments.get(position).getId();
//                                        Toast.makeText(context,"click " + departments.get(position).getName(),Toast.LENGTH_LONG).show();
                                    }
                                })
                        .show();
            }
        });

        disbursement_date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View arg0) {
                Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MARCH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
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


        disbursement_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectDay == null || selectDay.equals("")) {
                    Toast.makeText(context, "please select a date", Toast.LENGTH_LONG).show();
                    return;
                }
                Requisition requisition = new Requisition();
                requisition.setDepartmentId(dept_select);
                requisition.setCollectionDate(TimeUtil.convertyyyyMMddToTimestamp(selectDay));
                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST, CommonConstant.HttpUrl.GET_DISBURSEMENT,
                                new JSONObject(EntityUtil.object2Map(requisition)), new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                        if (result.getCode() == 200) {
                                            //final List<RequisitionDetail> requisitionDetails=new ArrayList<>();
                                            requisitionDetails = new ArrayList<>();
                                            for (int i = 0; i < ((ArrayList) result.getData()).size(); i++) {
                                                requisitionDetails.add((RequisitionDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList) result.getData()).get(i), RequisitionDetail.class));
                                            }

                                            //XJ

                                            if(!requisitionDetails.isEmpty()) {
                                                requisition_info.setVisibility(View.VISIBLE);
                                                if (requisitionDetails.get(0).getRequisition()!=null){
                                                    System.out.println(requisitionDetails.get(0).getRequisition().getReceivedByRepId());
                                                    received_by_rep.setText(requisitionDetails.get(0).getRequisition().getReceivedByRep().getName());}
                                                else{
                                                    received_by_rep.setVisibility(View.INVISIBLE);

                                                }
                                                if(!StringUtil.isEmpty(String.valueOf(requisitionDetails.get(0).getRequisition().getReceivedDate()))){
                                                    received_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisitionDetails.get(0).getRequisition().getReceivedDate()));}
                                                else{
                                                    received_date.setVisibility(View.INVISIBLE);
                                                }
                                                if (!StringUtil.isEmpty(String.valueOf(requisitionDetails.get(0).getRequisition().getAckByClerkId()))){
                                                ack_by_clerk.setText(requisitionDetails.get(0).getRequisition().getAckByClerk().getName());}
                                                else{
                                                    ack_by_clerk.setVisibility(View.INVISIBLE);
                                                }
                                                if(!StringUtil.isEmpty(String.valueOf(requisitionDetails.get(0).getRequisition().getAckDate()))){
                                                    ack_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisitionDetails.get(0).getRequisition().getAckDate()));}
                                                else{
                                                    ack_date.setVisibility(View.INVISIBLE);
                                                }
                                            }
                                            final int renderSize = requisitionDetails.size();
                                            disbursementAdapter = new MyAdapter<RequisitionDetail>((ArrayList) requisitionDetails, R.layout.item_disbursement_detail) {
                                                @Override
                                                public void bindView(ViewHolder holder, RequisitionDetail obj) {
                                                    holder.setText(R.id.item_code, obj.getProduct().getId());
                                                    holder.setText(R.id.item_description, obj.getProduct().getDescription());
                                                    holder.setText(R.id.qty, obj.getQtyNeeded() + "");
                                                    holder.setText(R.id.qty_received, obj.getQtyReceived() + "");
                                                    holder.setText(R.id.qty_disbursed, obj.getQtyDisbursed() + "");

                                                    //xj start
                                                    //received by +ack by
                                                    if (!StringUtil.isEmpty(obj.getDisburseRemark())) {
                                                        holder.setVisibility(R.id.dis_remark, View.VISIBLE);
                                                        holder.setVisibility(R.id.remarks, View.VISIBLE);
                                                        holder.setText(R.id.remarks, obj.getDisburseRemark());
                                                    } else {
                                                        holder.setVisibility(R.id.dis_remark, View.INVISIBLE);
                                                        holder.setVisibility(R.id.remarks, View.INVISIBLE);
                                                    }
                                                    if (!StringUtil.isEmpty(obj.getRepRemark())) {
                                                        holder.setVisibility(R.id.rep_remark, View.VISIBLE);
                                                        holder.setVisibility(R.id.dept_remarks, View.VISIBLE);
                                                        holder.setText(R.id.dept_remarks, obj.getRepRemark());
                                                    } else {
                                                        holder.setVisibility(R.id.rep_remark, View.INVISIBLE);
                                                        holder.setVisibility(R.id.dept_remarks, View.INVISIBLE);
                                                    }
                                                    if (!StringUtil.isEmpty(obj.getClerkRemark())) {
                                                        holder.setVisibility(R.id.cl_remark, View.VISIBLE);
                                                        holder.setVisibility(R.id.clerk_remarks, View.INVISIBLE);
                                                        holder.setText(R.id.clerk_remarks, obj.getClerkRemark());
                                                        holder.setVisibility(R.id.clerk_set_remarks, View.VISIBLE);
                                                    } else {
                                                        holder.setVisibility(R.id.cl_remark, View.INVISIBLE);
                                                        holder.setVisibility(R.id.clerk_remarks, View.INVISIBLE);
                                                        holder.setVisibility(R.id.clerk_set_remarks, View.INVISIBLE);
                                                    }

                                                    final ViewHolder thisHolder = holder;
                                                    if (obj.getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.RECEIVED)) {
                                                        holder.setVisibility(R.id.cl_remark, View.VISIBLE);
                                                        holder.setVisibility(R.id.clerk_remarks, View.INVISIBLE);
                                                        holder.setVisibility(R.id.clerk_set_remarks, View.VISIBLE);

                                                        holder.setOnClickListener(R.id.clerk_set_remarks, new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                showClerkRemarksInputDialog(view, thisHolder.getItemPosition());
                                                            }
                                                        });
                                                    }

                                                }
                                            };
                                            disbursement_list.setAdapter(disbursementAdapter);
                                        } else {
                                            Toast.makeText(context, result.getMsg(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, "invalid token", Toast.LENGTH_LONG).show();
                                        System.out.println("error");
                                        error.printStackTrace();
                                        System.out.println(error.getMessage());

                                    }
                                });
            }
        });

        clerk_update_remark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clerkUpdateRemark();

            }


        });
    }


    //xj
    private void showClerkRemarksInputDialog(final View view, final int position) {
        new XPopup.Builder(getContext()).asInputConfirm("Remarks From Clerk", "please input your remarks",
                new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (!StringUtil.isEmpty(text.trim())) {
                            Toast.makeText(context, "No input found", Toast.LENGTH_LONG).show();
                        } else {

                            ((TextView) view).setText(text);
                            requisitionDetails.get(position).setClerkRemark(text);
                            //System.out.println("asdf");
                        }
                    }
                })
                .show();
    }

        //update into database, via requisition Details, from clerk controller
        private void clerkUpdateRemark() {
            JSONArray jsonArray = null;
            try {
                jsonArray=new JSONArray(new Gson().toJson(requisitionDetails));
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpUtil.getInstance()
                    .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.ACK_DISBURSEMENT_COMPLETION,
                            jsonArray,new Response.Listener<JSONObject>(){
                                @Override
                                public void onResponse(JSONObject response) {
                                    Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                    if(result.getCode()==200){
                                        clerk_update_remark_button.setVisibility(View.INVISIBLE);
                                        disbursement_search.setVisibility(View.INVISIBLE);
                                        disbursement_date.setEnabled(false);
                                        disbursement_dept.setEnabled(false);
                                        ack_by_clerk.setText(requisitionDetails.get(0).getRequisition().getAckByClerk().getName());
                                        ack_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisitionDetails.get(0).getRequisition().getAckDate()));

                                        Toast.makeText(context,"successfully saved",Toast.LENGTH_LONG).show();
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



}