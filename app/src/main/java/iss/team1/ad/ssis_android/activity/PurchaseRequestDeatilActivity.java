package iss.team1.ad.ssis_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.PurchaseRequestDetail;
import iss.team1.ad.ssis_android.modal.TenderQuotation;

public class PurchaseRequestDeatilActivity extends AppCompatActivity {

    private String id;
    private Context context;
    private List<PurchaseRequestDetail> purchaseRequestDetails;
    private Map<PurchaseRequestDetail,List<TenderQuotation>> renderData;
    private MyAdapter<PurchaseRequestDetail> adapter;

    private TextView purchase_request_detail_id;
    private TextView purchase_request_detail_status;
    private ListView purchase_request_detail_list;
    private LinearLayout purchase_request_detail_reason_panel;
    private EditText purchase_request_detail_reason;
    private Button purchase_request_detail_reject_btn;
    private Button purchase_request_detail_approve_btn;
    private TextView purchase_request_detail_reason_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_request_deatil);

        context=this;
        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        init();
    }

    private void init() {
        purchase_request_detail_id=findViewById(R.id.purchase_request_detail_id);
        purchase_request_detail_status=findViewById(R.id.purchase_request_detail_status);
        purchase_request_detail_list=findViewById(R.id.purchase_request_detail_list);
        purchase_request_detail_reason=findViewById(R.id.purchase_request_detail_reason);
        purchase_request_detail_reason_panel=findViewById(R.id.purchase_request_detail_reason_panel);
        purchase_request_detail_reject_btn=findViewById(R.id.purchase_request_detail_reject_btn);
        purchase_request_detail_approve_btn=findViewById(R.id.purchase_request_detail_approve_btn);
        purchase_request_detail_reason_txt=findViewById(R.id.purchase_request_detail_reason_txt);

        fetchDetail();

        purchase_request_detail_reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePurchaseRequest(CommonConstant.PurchaseRequestStatus.REJECTED);
            }
        });

        purchase_request_detail_approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePurchaseRequest(CommonConstant.PurchaseRequestStatus.APPROVED);
            }
        });
    }

    private void fetchDetail(){
        purchaseRequestDetails=new ArrayList<>();

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.PURCHASE_REQUEST_DETAIL(id),
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        purchaseRequestDetails.add((PurchaseRequestDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),PurchaseRequestDetail.class));
                                    }
                                    render();
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

    private void render() {
        try{
            purchase_request_detail_id.setText(purchaseRequestDetails.get(0).getPurchaseRequestId()+"");
            purchase_request_detail_status.setText(purchaseRequestDetails.get(0).getStatus());
            purchase_request_detail_reason_txt.setText(purchaseRequestDetails.get(0).getRemarks());
            if(purchaseRequestDetails.get(0).getStatus().equals(CommonConstant.PurchaseRequestStatus.PENDING_APPROVAL)){
                purchase_request_detail_reason_panel.setVisibility(View.VISIBLE);
            }
            adapter=new MyAdapter<PurchaseRequestDetail>((ArrayList<PurchaseRequestDetail>) purchaseRequestDetails,R.layout.item_purchase_request_detail) {
                @Override
                public void bindView(ViewHolder holder, PurchaseRequestDetail obj) {
                    holder.setText(R.id.item_purchase_request_detail_item_code,obj.getProductId());
                    holder.setText(R.id.item_purchase_request_detail_desc,obj.getProduct().getDescription());
                    holder.setText(R.id.item_purchase_request_detail_current_stock,obj.getCurrentStock()+"");
                    holder.setText(R.id.item_purchase_request_detail_qty_order,obj.getReorderQty()+"");
                    holder.setText(R.id.item_purchase_request_detail_vender,obj.getSupplier().getName());
                    holder.setText(R.id.item_purchase_request_detail_vender_quote,obj.getVenderQuote());
                    holder.setText(R.id.item_purchase_request_detail_total_price,"$"+obj.getTotalPrice());
                }
            };
            purchase_request_detail_list.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void updatePurchaseRequest(String status){
        String remarks = purchase_request_detail_reason.getText().toString();
        for (PurchaseRequestDetail purchaseRequestDetail : purchaseRequestDetails) {
            purchaseRequestDetail.setRemarks(remarks);
            purchaseRequestDetail.setStatus(status);
        }
        JSONArray jsonArray=null;
        try {
            jsonArray = new JSONArray(new Gson().toJson(purchaseRequestDetails));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.UPDATE_PURCHASE_REQUEST,
                        jsonArray,new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    fetchDetail();
                                    purchase_request_detail_reason_panel.setVisibility(View.INVISIBLE);
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