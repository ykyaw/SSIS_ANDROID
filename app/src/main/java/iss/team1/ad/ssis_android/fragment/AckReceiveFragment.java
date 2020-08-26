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
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.json.JSONArray;
import org.json.JSONException;
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

    private TextView disbursement_date,receive_by,ack_by;
    private Button ack_filter_btn,ack_btn;
    private ListView disbursement_list;

    private MyAdapter<RequisitionDetail> disbursementsAdapter;

    int mYear;
    int mMonth ;
    int mDay;
    String selectDay=null;
    private int tableRowRenderTime=0;
    private List<RequisitionDetail> requisitionDetails=new ArrayList<>();
    private boolean isAck=false;

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
        ack_btn=(Button)view.findViewById(R.id.ack_btn);
        receive_by=(TextView)view.findViewById(R.id.receied_by);
        ack_by=(TextView)view.findViewById(R.id.ack_by);

        context= ApplicationUtil.getContext();

        ack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getContext()).asConfirm("Acknowledge", "Please ensure all the products have received",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                ack();
                            }
                        })
                        .show();

            }
        });

        ack_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectDay==null||selectDay.equals("")){
                    Toast.makeText(context,"please select a date",Toast.LENGTH_LONG).show();
                    return;
                }
//                tableRowRenderTime=0;
               fetchData();
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

    private void fetchData() {
        requisitionDetails = new ArrayList<>();
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_DEPT_DISBURSEMENT_DETAIL(TimeUtil.convertyyyyMMddToTimestamp(selectDay)),
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        requisitionDetails.add((RequisitionDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),RequisitionDetail.class));
                                    }
                                    if(requisitionDetails.size()==0){
                                        Toast.makeText(context,"No data in that day",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (requisitionDetails.get(0).getRequisition().getAckByClerk()!=null){
                                        ack_by.setText(requisitionDetails.get(0).getRequisition().getAckByClerk().getName()
                                                +" "+TimeUtil.convertTimestampToyyyyMMddHHmm(requisitionDetails.get(0).getRequisition().getAckDate()));
                                    }
                                    if(requisitionDetails.get(0).getRequisition().getReceivedByRep()!=null){
                                        receive_by.setText(requisitionDetails.get(0).getRequisition().getReceivedByRep().getName()
                                                +" "+TimeUtil.convertTimestampToyyyyMMddHHmm(requisitionDetails.get(0).getRequisition().getReceivedDate()));
                                    }
                                    isAck=requisitionDetails.get(0).getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.COMPLETED)||
                                            requisitionDetails.get(0).getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.RECEIVED);
                                    if(!isAck){
                                        ack_btn.setVisibility(View.VISIBLE);
                                    }

                                    final int size = requisitionDetails.size();
                                    disbursementsAdapter = new MyAdapter<RequisitionDetail>((ArrayList) requisitionDetails,R.layout.item_disbursement) {
                                        @Override
                                        public void bindView(final ViewHolder holder, RequisitionDetail obj) {
//                                                    if(tableRowRenderTime<size){
                                            holder.setText(R.id.item_desc, obj.getProduct().getDescription());
                                            holder.setText(R.id.qty_dis,obj.getQtyDisbursed()+"");
                                            holder.setText(R.id.qty_req,obj.getQtyNeeded()+"");
                                            if(StringUtil.isEmpty(obj.getDisburseRemark())){
                                                holder.setVisibility(R.id.remarks_title, View.INVISIBLE);
                                            }else{
                                                holder.setText(R.id.remarks,obj.getDisburseRemark());
                                            }
                                            holder.setText(R.id.qty_rec,obj.getQtyReceived()+"");
                                            holder.setText(R.id.dept_remarks,obj.getRepRemark());
                                            final ViewHolder thisHolder=holder;
                                            if(!isAck){
                                                holder.setOnClickListener(R.id.qty_rec, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        showQtyRecInputDialog(view,thisHolder.getItemPosition());
                                                    }
                                                });
                                                final ViewHolder thizHolder=holder;
                                                holder.setOnClickListener(R.id.dept_remarks, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        showDeptRemarksInputDialog(view,thizHolder.getItemPosition());
                                                    }
                                                });
                                            }else{
                                                ((TextView)holder.getView(R.id.qty_rec)).setCompoundDrawables(null,null,null,null);
                                                ((TextView)holder.getView(R.id.dept_remarks)).setCompoundDrawables(null,null,null,null);
                                            }
//                                                        tableRowRenderTime++;
//                                                    }
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

    private void showQtyRecInputDialog(final View view, final int position){
        new XPopup.Builder(getContext()).asInputConfirm("Quantity Receive", "",
                new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if(!StringUtil.isNumeric(text)){
                            Toast.makeText(context,"please input a numberic",Toast.LENGTH_LONG).show();
                            return;
                        }else if(Integer.valueOf(text)> requisitionDetails.get(position).getQtyDisbursed()){
                            Toast.makeText(context,"receieve qty can not more than disbursement qty",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            ((TextView)view).setText(text);
                            requisitionDetails.get(position).setQtyReceived(Integer.valueOf(text));
                        }
                    }
                })
                .show();
    }

    private void showDeptRemarksInputDialog(final View view,final int position){
        new XPopup.Builder(getContext()).asInputConfirm("Remarks", "please input your remarks",
                new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        ((TextView)view).setText(text);
                        requisitionDetails.get(position).setRepRemark(text);
                        System.out.println("asdf");
                    }
                })
                .show();
    }

    private void ack(){
        JSONArray jsonArray = null;
        try {
            jsonArray=new JSONArray(new Gson().toJson(requisitionDetails));
            System.out.println("asdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.ACK_DISBURSEMENT,
                        jsonArray,new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    ack_btn.setVisibility(View.INVISIBLE);
                                    fetchData();
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