package iss.team1.ad.ssis_android.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
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
import iss.team1.ad.ssis_android.modal.RequisitionDetail;
import iss.team1.ad.ssis_android.modal.Retrieval;

public class RetrievalFormActivity extends AppCompatActivity {

    private ListView retrieval_list;
    private CheckBox retrieval_is_need_voucher;
    private Button retrieval_update_btn;
    private Button retrieval_finalise_btn;
    private TextView retrieval_retrieved_by;
    private TextView retrieval_status;
    private TextView retrieval_id;
    private TextView retrieval_date_of_retrieval;
    private LinearLayout retrieval_btn_panel;
    private LinearLayout retrieval_info_row;
    private EditText retrieval_comments;

    int mYear;
    int mMonth ;
    int mDay;
    String startday;
    String id;

    private Context context;

    private Retrieval retrieval=null;


    private MyAdapter<RequisitionDetail> myAdapter1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_form);

        Intent intent = getIntent();
        startday = intent.getStringExtra("collectionDate");
        id=intent.getStringExtra("id");
        context= this;
        init();
    }

    private void init() {
        retrieval_list=(ListView)findViewById(R.id.retrieval_list);
        retrieval_is_need_voucher=findViewById(R.id.retrieval_is_need_voucher);
        retrieval_update_btn=findViewById(R.id.retrieval_update_btn);
        retrieval_finalise_btn=findViewById(R.id.retrieval_finalise_btn);
        retrieval_retrieved_by=findViewById(R.id.retrieval_retrieved_by);
        retrieval_status=findViewById(R.id.retrieval_status);
        retrieval_id=findViewById(R.id.retrieval_id);
        retrieval_date_of_retrieval=findViewById(R.id.retrieval_date_of_retrieval);
        retrieval_btn_panel=findViewById(R.id.retrieval_btn_panel);
        retrieval_info_row=findViewById(R.id.retrieval_info_row);
        retrieval_comments=findViewById(R.id.retrieval_comments);

        try {
            generateRetrievalForm();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        retrieval_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRetrieval(CommonConstant.RetrievalStatus.CREATED);
            }
        });
        retrieval_finalise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRetrieval(CommonConstant.RetrievalStatus.RETRIEVED);
            }
        });
        retrieval_is_need_voucher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                retrieval.setNeedAdjustment(b);
                if(b){
                    retrieval_comments.setVisibility(View.VISIBLE);
                }else{
                    retrieval_comments.setVisibility(View.GONE);
                }
            }
        });


    }


    private void updateRetrieval(String status) {
        retrieval.setRemark(retrieval_comments.getText().toString());
        retrieval.setStatus(status);
        Map<String, Object> stringObjectMap = EntityUtil.object2Map(retrieval);
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(retrieval));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        jsonObject.
//        JSONArray jsonArray=null;
//        try {
//            jsonArray = new JSONArray(new G);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.UPDATE_RETRIEVAL,
                        jsonObject,new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    if ((boolean)result.getData()){
                                        try {
                                            generateRetrievalForm();
                                            Toast.makeText(context,"update successfully",Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        Toast.makeText(context,"some thing error when update retrieval",Toast.LENGTH_LONG).show();
                                    }
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

    private void generateRetrievalForm() throws JSONException {
        String url="";
        if(StringUtil.isEmpty(startday)){
            url=CommonConstant.HttpUrl.GET_RETRIEVAL_FORM_BY_ID(id);
        }else{
            long date = TimeUtil.convertyyyyMMddToTimestamp(startday);
            url=CommonConstant.HttpUrl.GENERATE_RETRIEVAL_FORM(date);
        }

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, url,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    retrieval = (Retrieval) EntityUtil.map2Object((Map<String, Object>) result.getData(), Retrieval.class);
                                    displayRetrievalTable();
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

    private void displayRetrievalTable(){

        retrieval_id.setText(retrieval.getId()+"");
        retrieval_status.setText(retrieval.getStatus());
        retrieval_retrieved_by.setText(retrieval.getClerk().getName());
        retrieval_info_row.setVisibility(View.VISIBLE);
        if(retrieval.getStatus().equals(CommonConstant.RetrievalStatus.CREATED)){
            retrieval_btn_panel.setVisibility(View.VISIBLE);
        }
        if(retrieval.getRetrievedDate()!=0){
            retrieval_date_of_retrieval.setText(TimeUtil.convertTimestampToyyyyMMdd(retrieval.getRetrievedDate()));
        }

        myAdapter1 = new MyAdapter<RequisitionDetail>((ArrayList)retrieval.getRequisitionDetails(),R.layout.item_retrieval_detail) {
            @Override
            public void bindView(ViewHolder holder, RequisitionDetail detail) {
                holder.setText(R.id.item_code,detail.getProduct().getId());
                holder.setText(R.id.item_description,detail.getProduct().getDescription());
                holder.setText(R.id.qty_needed,detail.getQtyNeeded()+"");
                holder.setText(R.id.qty_retrieved,detail.getQtyDisbursed()+"");
                holder.setText(R.id.item_retrieval_remarks,detail.getDisburseRemark());

                if(retrieval.getStatus().equals(CommonConstant.RetrievalStatus.CREATED)){
                    holder.setOnClickListener(R.id.item_retrieval_remarks, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new XPopup.Builder(context).asInputConfirm("Remarks", "please input remarks",
                                    new OnInputConfirmListener() {
                                        @Override
                                        public void onConfirm(String text) {
                                            ((TextView)view).setText(text);
                                            retrieval.getRequisitionDetails().get(holder.getItemPosition()).setDisburseRemark(text);
                                        }
                                    })
                                    .show();
                        }
                    });
                    holder.setOnClickListener(R.id.qty_retrieved, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new XPopup.Builder(context).asInputConfirm("Qty Retrieved", "",
                                    new OnInputConfirmListener() {
                                        @Override
                                        public void onConfirm(String text) {
                                            if(StringUtil.isNumeric(text)){
                                                ((TextView)view).setText(text);
                                                retrieval.getRequisitionDetails().get(holder.getItemPosition()).setQtyDisbursed(Integer.valueOf(text));
                                            }else{
                                                Toast.makeText(context,"please input a numberic",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    });
                }else{
                    ((TextView)holder.getView(R.id.qty_retrieved)).setCompoundDrawables(null,null,null,null);
                    ((TextView)holder.getView(R.id.item_retrieval_remarks)).setCompoundDrawables(null,null,null,null);
                }

            }
        };

        retrieval_list.setAdapter(myAdapter1);
    }
}