package iss.team1.ad.ssis_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.StringUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Department;
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DisbursementDetailActivity extends AppCompatActivity {

    private ListView disbursement_list;
    private TextView disbursement_detail_date;


    //XJ
    private List<RequisitionDetail> requisitionDetails = null;
    private TextView received_by_rep;
    private TextView received_date;
    private TextView ack_by_clerk;
    private TextView ack_date;
    private Button clerk_update_remark_button;
    private LinearLayout requisition_info;

    private Context context;
    private MyAdapter<RequisitionDetail> disbursementAdapter;
    private TextView confirm_complete;


    String dept_select;
    long selectDay;

    private List<Department> departments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);

        //get intent
        Intent intent = getIntent();
        selectDay=intent.getLongExtra("date",0);
        dept_select=intent.getStringExtra("deptId");

        context= this;
        init();
    }



    private void init() {
        disbursement_detail_date=findViewById(R.id.disbursement_detail_date);
        disbursement_list = (ListView) findViewById(R.id.disbursement_list);
        received_by_rep=(TextView) findViewById(R.id.received_by_rep);
        received_date=(TextView) findViewById(R.id.received_date);
        ack_by_clerk=(TextView) findViewById(R.id.ack_by_clerk);
        ack_date=(TextView) findViewById(R.id.ack_date);
        clerk_update_remark_button=(Button)findViewById(R.id.clerk_update_remark_button) ;
        requisition_info=(LinearLayout)findViewById(R.id.requisition_info);
        requisition_info.setVisibility(View.INVISIBLE);
        confirm_complete=(TextView)findViewById(R.id.confirm_complete);

        disbursement_detail_date.setText(TimeUtil.convertTimestampToyyyyMMdd(selectDay));

        fetchRequisitionDetailsByDate();
        clerk_update_remark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clerkUpdateRemark();

            }


        });
    }

    private void fetchRequisitionDetailsByDate() {


        Requisition requisition = new Requisition();
        requisition.setDepartmentId(dept_select);
        requisition.setCollectionDate(selectDay);
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
                                            if(requisitionDetails.get(0).getRequisition().getReceivedByRep()!=null){
                                                received_by_rep.setText(requisitionDetails.get(0).getRequisition().getReceivedByRep().getName());
                                            }
                                            else{
                                                received_by_rep.setText("");
                                            }
                                        }
                                        if(requisitionDetails.get(0).getRequisition().getReceivedDate()!=0){
                                            received_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisitionDetails.get(0).getRequisition().getReceivedDate()));}
                                        else{
                                            received_date.setText("");
                                        }
                                        if (requisitionDetails.get(0).getRequisition().getAckByClerk()!=null){
                                            ack_by_clerk.setText(requisitionDetails.get(0).getRequisition().getAckByClerk().getName());
                                        }else{
                                            ack_by_clerk.setText("");
                                        }
                                        if(requisitionDetails.get(0).getRequisition().getAckDate()!=0){
                                            ack_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisitionDetails.get(0).getRequisition().getAckDate()));
                                        }else{
                                            ack_date.setText("");
                                        }

                                        if(!requisitionDetails.get(0).getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.RECEIVED)){
                                            clerk_update_remark_button.setVisibility(View.GONE);
                                        }else{
                                            clerk_update_remark_button.setVisibility(View.VISIBLE);
                                            confirm_complete.setVisibility(View.GONE);
                                        }

                                        if(requisitionDetails.get(0).getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.COMPLETED)){
                                            confirm_complete.setVisibility(View.VISIBLE);
                                        }
                                    }
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
                                            if (obj.getRequisition().getStatus().equals(CommonConstant.RequsitionStatus.COMPLETED)){
                                                holder.setVisibility(R.id.cl_remark, View.VISIBLE);
                                                holder.setVisibility(R.id.clerk_remarks, View.VISIBLE);
                                                holder.setVisibility(R.id.clerk_set_remarks, View.INVISIBLE);
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


    //xj
    private void showClerkRemarksInputDialog(final View view, final int position) {
        new XPopup.Builder(context).asInputConfirm("Remarks From Clerk", "please input your remarks",
                new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (StringUtil.isEmpty(text.trim())) {
                            Toast.makeText(context, "No input found", Toast.LENGTH_LONG).show();
                        } else {

                            ((TextView) view).setText(text);
                            requisitionDetails.get(position).setClerkRemark(text);
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
                                    Toast.makeText(context,"successfully saved",Toast.LENGTH_LONG).show();
                                    fetchRequisitionDetailsByDate();
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