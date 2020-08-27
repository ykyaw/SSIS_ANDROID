package iss.team1.ad.ssis_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import org.json.JSONObject;

import java.util.ArrayList;
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
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.AdjustmentVoucher;
import iss.team1.ad.ssis_android.modal.AdjustmentVoucherDetail;

public class StoreSupAVDetailActivity extends AppCompatActivity {
    private TextView adjustment_voucher_id;
    private TextView adjustment_vouchr_inititated_by;
    private TextView adjustment_voucher_status;
    private TextView av_approve_by_sup;
    private TextView av_approve_by_man;
    private TextView av_reject_reason;
    private ListView store_sup_av_detail_list;
    private EditText store_sup_av_reject_reason;
    private Button store_sup_av_reject_btn;
    private Button store_sup_av_approve_btn;
    private Button store_man_av_approve_btn;
    private TextView av_alert;


    private LinearLayout read_only_panel;
    private LinearLayout store_sup_av_detail_list_btn_panel;
    private TextView av_item_description;
    private TextView qty_adjsuted;
    private TextView item_unit_price;
    private TextView item_total_price;

    private MyAdapter<AdjustmentVoucherDetail> AVDetailMyAdapter;

    private String avid;
    private Context context;
    private AdjustmentVoucher adjustmentvoucher=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_av_detail);

        Intent intent=getIntent();
        avid=intent.getStringExtra("adjustmentvoucherId");

        context=this;
        init();
    }

    private void init() {
       adjustment_voucher_id=(TextView)findViewById(R.id.av_id);
       adjustment_vouchr_inititated_by=(TextView)findViewById(R.id.initiated_by);
       adjustment_voucher_status=(TextView)findViewById(R.id.adjustmentvoucher_status);
       av_approve_by_sup=(TextView)findViewById(R.id.av_approve_by_sup);
       av_approve_by_man=(TextView)findViewById(R.id.av_approve_by_man);
       av_reject_reason=(TextView)findViewById(R.id.av_reject_reason);
       store_sup_av_detail_list=(ListView)findViewById(R.id.store_sup_av_detail_list);
       read_only_panel=(LinearLayout)findViewById(R.id.av_read_only);
        fetchAVDeatil();
        store_sup_av_reject_reason=(EditText) findViewById(R.id.store_sup_av_reject_reason);
        store_sup_av_reject_btn=(Button)findViewById(R.id.store_sup_av_reject_btn);
        store_sup_av_approve_btn=(Button)findViewById(R.id.store_sup_av_approve_btn);
        store_man_av_approve_btn=(Button)findViewById(R.id.store_man_av_approve_btn);
        store_sup_av_detail_list_btn_panel=(LinearLayout)findViewById((R.id.store_sup_av_detail_list_btn_panel));
        item_total_price=(TextView)findViewById(R.id.item_price);
        av_alert=(TextView)findViewById(R.id.av_alert);

        store_sup_av_reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprRejAdjustmentVoucher(view);
            }
        });
        store_sup_av_approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprRejAdjustmentVoucher(view);
            }
        });
        store_man_av_approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprRejAdjustmentVoucher(view);
            }
        });
    }



    private void fetchAVDeatil() {
        LoadingPopupView loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
                .asLoading("loading")
                .show();

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.Get_AV_DETAIL_BY_AVID(avid),
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                loadingPopup.dismiss();
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    adjustmentvoucher= (AdjustmentVoucher) EntityUtil.map2Object((Map<String, Object>) result.getData(),AdjustmentVoucher.class);
                                    DisplayDetails();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DisplayDetails() {
        adjustment_voucher_id.setText(adjustmentvoucher.getId());
        adjustment_vouchr_inititated_by.setText(adjustmentvoucher.getInitiatedClerk().getName());
        adjustment_voucher_status.setText(adjustmentvoucher.getStatus());

        if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.APPROVED)
                || adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.REJECTED)
                || adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDMANAPPROV)) {
            read_only_panel.setVisibility(View.VISIBLE);
            store_sup_av_detail_list_btn_panel.setVisibility(View.INVISIBLE);
            if(adjustmentvoucher.getApprovedSup()!=null){
                av_approve_by_sup.setText(adjustmentvoucher.getApprovedSup().getName());
            }

            if (StringUtil.isEmpty(adjustmentvoucher.getReason())) {
                av_reject_reason.setVisibility(View.INVISIBLE);
            }
            if (adjustmentvoucher.getApprovedMgrId() != 0) {
                av_approve_by_man.setVisibility(View.INVISIBLE);

            }
            if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDMANAPPROV)) {
                if (ApplicationUtil.getCurrentUser().getRole().equals(CommonConstant.ROLE.STORE_MANAGER)) {
                    store_sup_av_detail_list_btn_panel.setVisibility(View.VISIBLE);
                    store_man_av_approve_btn.setVisibility(View.INVISIBLE);
                }

            }

        }

        if(adjustmentvoucher.getAdjustmentVoucherDetails().stream()
                .filter(item->item.getTotalPrice()>250)
                .collect(Collectors.toList()).size()>0){
            av_alert.setVisibility(View.VISIBLE);
            if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDING_APPROVAL)) {
                if (ApplicationUtil.getCurrentUser().getRole().equals(CommonConstant.ROLE.STORE_SUPERVISOR)) {
                    store_sup_av_approve_btn.setVisibility(View.INVISIBLE);
                    store_man_av_approve_btn.setVisibility(View.VISIBLE);//this button set status="pendmanaprov"
                } else {
                    store_sup_av_detail_list_btn_panel.setVisibility(View.VISIBLE);
                    av_alert.setText("Pending Supervisor Review");
                    store_sup_av_approve_btn.setVisibility(View.INVISIBLE);
                    store_man_av_approve_btn.setVisibility(View.INVISIBLE);//this button set status="pendmanaprov"

                }
            }
        }else {
            av_alert.setVisibility(View.INVISIBLE);
            if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDING_APPROVAL)) {
                //to both supervisor and manager
                store_sup_av_detail_list_btn_panel.setVisibility(View.VISIBLE);
                store_sup_av_approve_btn.setVisibility(View.VISIBLE);
                store_man_av_approve_btn.setVisibility(View.INVISIBLE);
            }
        }

        AVDetailMyAdapter = new MyAdapter<AdjustmentVoucherDetail>((ArrayList<AdjustmentVoucherDetail>) adjustmentvoucher.getAdjustmentVoucherDetails(), R.layout.item_avdetail) {

            @Override
            public void bindView(ViewHolder holder, AdjustmentVoucherDetail obj) {
                holder.setText(R.id.av_item_description, obj.getProduct().getDescription());
                holder.setText(R.id.qty_adjusted, obj.getQtyAdjusted() + "");
                holder.setText(R.id.item_unit_price, "$" + obj.getUnitprice() + "");
                holder.setText(R.id.item_price, "$" + obj.getTotalPrice() + "");
                holder.setText(R.id.item_adjusted_reason,obj.getReason());
//                if (obj.getTotalPrice() >= 250) {
//                    av_alert.setVisibility(View.VISIBLE);
//                    if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDING_APPROVAL)) {
//                        if (ApplicationUtil.getCurrentUser().getRole().equals(CommonConstant.ROLE.STORE_SUPERVISOR)) {
//                            store_sup_av_approve_btn.setVisibility(View.INVISIBLE);
//                            store_man_av_approve_btn.setVisibility(View.VISIBLE);//this button set status="pendmanaprov"
//                        } else {
//                            store_sup_av_detail_list_btn_panel.setVisibility(View.VISIBLE);
//                            av_alert.setText("Pending Supervisor Review");
//
//                        }
//                    }
//
//                }else {
//                    av_alert.setVisibility(View.INVISIBLE);
//                    if (adjustmentvoucher.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDING_APPROVAL)) {
//                        //to both supervisor and manager
//                        store_sup_av_detail_list_btn_panel.setVisibility(View.VISIBLE);
//                        store_sup_av_approve_btn.setVisibility(View.VISIBLE);
//                        store_man_av_approve_btn.setVisibility(View.INVISIBLE);
//                    }
//                }

            }
        };

        store_sup_av_detail_list.setAdapter(AVDetailMyAdapter);

    }


    private void ApprRejAdjustmentVoucher(View view) {
        String rejreason = store_sup_av_reject_reason.getText().toString();
        adjustmentvoucher.setReason(rejreason);

        if (view.getId() == R.id.store_sup_av_reject_btn) {
            adjustmentvoucher.setStatus(CommonConstant.AdjsutmentVoucherStatus.REJECTED);
        }
        if (view.getId() == R.id.store_sup_av_approve_btn) {
            adjustmentvoucher.setStatus(CommonConstant.AdjsutmentVoucherStatus.APPROVED);
        }
        if (view.getId() == R.id.store_man_av_approve_btn) {
            adjustmentvoucher.setStatus(CommonConstant.AdjsutmentVoucherStatus.PENDMANAPPROV);
//            adjustmentvoucher.setApprovedSup(ApplicationUtil.getCurrentUser());

        }


        LoadingPopupView loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
                .asLoading("loading")
                .show();

        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.PUT, CommonConstant.HttpUrl.APPRO_REJ_ADJUSTMENT_VOUCHER(adjustmentvoucher),
                        new JSONObject(EntityUtil.object2Map(adjustmentvoucher)), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                loadingPopup.dismiss();
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if (result.getCode() == 200) {
                                    Toast.makeText(context, "successfully saved", Toast.LENGTH_LONG).show();
                                    fetchAVDeatil();
                                } else {
                                    Toast.makeText(context, result.getMsg(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadingPopup.dismiss();
                                Toast.makeText(context, "invalid token", Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }

}