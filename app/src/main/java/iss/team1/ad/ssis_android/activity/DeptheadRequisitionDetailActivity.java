package iss.team1.ad.ssis_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Employee;
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DeptheadRequisitionDetailActivity extends AppCompatActivity {


    private TextView depthead_requisition_detail_detail_form_id;
    private TextView depthead_requisition_detail_detail_employee_name;
    private TextView depthead_requisition_detail_detail_requested_date;
    private TextView depthead_requisition_detail_detail_status;
    private ListView depthead_requisition_detail_detail_list;
    private EditText depthead_requisition_detail_reject_reason;
    private Button depthead_requisition_detail_reject_btn;
    private Button depthead_requisition_detail_approve_btn;
    private LinearLayout depthead_requisition_detail_btn_panel;
    private LinearLayout depthead_requisition_detail_collection_point_panel;
    private LinearLayout depthead_requisition_detail_disbursement_date_panel;
    private TextView depthead_requisition_detail_disbursement_date;
    private TextView depthead_requisition_detail_collection_point;
    private LinearLayout depthead_requisition_detail_reject_reason_display_panel;
    private TextView depthead_requisition_detail_reject_reason_display;

    private MyAdapter<RequisitionDetail> requisitionDetailMyAdapter;

    private String id;
    private Context context;
    private Requisition requisition=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depthead_requisition_detail);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        context=this;

        init();
        
    }
    
    private void init(){
        depthead_requisition_detail_detail_employee_name=(TextView)findViewById(R.id.depthead_requisition_detail_employee_name);
        depthead_requisition_detail_detail_form_id=(TextView)findViewById(R.id.depthead_requisition_detail_form_ID);
        depthead_requisition_detail_detail_requested_date=(TextView)findViewById(R.id.depthead_requisition_detail_requested_date);
        depthead_requisition_detail_detail_status=(TextView)findViewById(R.id.depthead_requisition_detail_status);
        depthead_requisition_detail_detail_list=(ListView)findViewById(R.id.depthead_requisition_detail_list);
        depthead_requisition_detail_reject_btn=(Button)findViewById(R.id.depthead_requisition_detail_reject_btn);
        depthead_requisition_detail_reject_reason=(EditText)findViewById(R.id.depthead_requisition_detail_reject_reason);
        depthead_requisition_detail_approve_btn=(Button)findViewById(R.id.depthead_requisition_detail_approve_btn);
        depthead_requisition_detail_btn_panel=(LinearLayout)findViewById(R.id.depthead_requisition_detail_btn_panel);
        depthead_requisition_detail_collection_point=(TextView)findViewById(R.id.depthead_requisition_detail_collection_point);
        depthead_requisition_detail_collection_point_panel=(LinearLayout)findViewById(R.id.depthead_requisition_detail_collection_point_panel);
        depthead_requisition_detail_disbursement_date=(TextView)findViewById(R.id.depthead_requisition_detail_disbursement_date);
        depthead_requisition_detail_disbursement_date_panel=(LinearLayout)findViewById(R.id.depthead_requisition_detail_disbursement_date_panel);
        depthead_requisition_detail_reject_reason_display_panel=(LinearLayout)findViewById(R.id.depthead_requisition_detail_reject_reason_display_panel);
        depthead_requisition_detail_reject_reason_display=(TextView)findViewById(R.id.depthead_requisition_detail_reject_reason_display);
        fetchRequisitionDeatil();

        depthead_requisition_detail_approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRequisition();
            }
        });
        depthead_requisition_detail_reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRequisition();
            }
        });

    }

    private void fetchRequisitionDeatil(){
        LoadingPopupView loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
                .asLoading("loading")
                .show();

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_DEPT_HEAD_REQUISITIONS_DEATIL(id),
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                loadingPopup.dismiss();
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    requisition= (Requisition) EntityUtil.map2Object((Map<String, Object>) result.getData(),Requisition.class);
                                    setData();

                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadingPopup.dismiss();
                                Toast.makeText(context,"invalid token",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }

    private void setData() {
        depthead_requisition_detail_detail_employee_name.setText(requisition.getReqByEmp().getName());
        depthead_requisition_detail_detail_form_id.setText(requisition.getId()+"");
        depthead_requisition_detail_detail_requested_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisition.getCreatedDate()));
        depthead_requisition_detail_detail_status.setText(requisition.getStatus());

        if(requisition.getStatus().equals(CommonConstant.RequsitionStatus.APPROVED)
                ||requisition.getStatus().equals(CommonConstant.RequsitionStatus.CONFIRMED)
                ||requisition.getStatus().equals(CommonConstant.RequsitionStatus.COMPLETED)
                ||requisition.getStatus().equals(CommonConstant.RequsitionStatus.REJECT)){
            depthead_requisition_detail_btn_panel.setVisibility(View.INVISIBLE);
        }

        if(requisition.getStatus().equals(CommonConstant.RequsitionStatus.CONFIRMED)
                ||requisition.getStatus().equals(CommonConstant.RequsitionStatus.COMPLETED)){
            depthead_requisition_detail_disbursement_date.setText(TimeUtil.convertTimestampToyyyyMMdd(requisition.getCollectionDate()));
            depthead_requisition_detail_collection_point.setText(requisition.getCollectionPoint().getLocation()+"("+requisition.getCollectionPoint().getCollectionTime()+")");
            depthead_requisition_detail_disbursement_date_panel.setVisibility(View.VISIBLE);
            depthead_requisition_detail_collection_point_panel.setVisibility(View.VISIBLE);
        }

        if(requisition.getStatus().equals(CommonConstant.RequsitionStatus.REJECT)){
            depthead_requisition_detail_reject_reason_display.setText(requisition.getRemarks());
            depthead_requisition_detail_reject_reason_display_panel.setVisibility(View.VISIBLE);
        }

        requisitionDetailMyAdapter=new MyAdapter<RequisitionDetail>((ArrayList<RequisitionDetail>) requisition.getRequisitionDetails(),R.layout.item_dept_head_requisition_detail){

            @Override
            public void bindView(ViewHolder holder, RequisitionDetail obj) {
                holder.setText(R.id.item_dept_head_requisition_detail_desc,obj.getProduct().getDescription());
                holder.setText(R.id.item_dept_head_requisition_detail_qty,obj.getQtyNeeded()+"");
            }
        };
        depthead_requisition_detail_detail_list.setAdapter(requisitionDetailMyAdapter);
    }

    private void updateRequisition(){
        String remarks=depthead_requisition_detail_reject_reason.getText().toString();
        requisition.setRemarks(remarks);
        requisition.setStatus(CommonConstant.RequsitionStatus.APPROVED);
        LoadingPopupView loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
                .asLoading("loading")
                .show();

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.DEPT_HEAD_UPDATE_REQUISITION_DETAIL,
                        new JSONObject(EntityUtil.object2Map(requisition)),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                loadingPopup.dismiss();
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    fetchRequisitionDeatil();
                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadingPopup.dismiss();
                                Toast.makeText(context,"invalid token",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }
}