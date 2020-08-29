package iss.team1.ad.ssis_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.StoreSupAVDetailActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.AdjustmentVoucher;

public class RetrieveAllVouchersFragment extends Fragment  {

    private SwipeRefreshLayout adjsutment_voucher_swl;
    private ListView allvoucher_list;
    private Button search_adjustmentvoucher_button;
    private TextView search_adjustmentvoucher;


    private MyAdapter<AdjustmentVoucher> myAdapter1 = null;
    private Context context;
    private List<AdjustmentVoucher> avlist =null;
    private boolean isSubmitted=false;


    public RetrieveAllVouchersFragment() {
        // Required empty public constructor
    }

    public static RetrieveAllVouchersFragment newInstance(String param1, String param2) {
        RetrieveAllVouchersFragment fragment = new RetrieveAllVouchersFragment();
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
        //inflate list view
        View view=inflater.inflate(R.layout.fragment_retrieve_allav, container, false);
        init(view);
        context= ApplicationUtil.getContext();
        return view;

    }

    private void init(View view) {
        adjsutment_voucher_swl=view.findViewById(R.id.adjustment_voucher_swl);
        allvoucher_list = (ListView) view.findViewById(R.id.allvoucher_list);
        search_adjustmentvoucher_button = (Button) view.findViewById(R.id.search_adjustmentvoucher_button);
        search_adjustmentvoucher = (TextView) view.findViewById(R.id.search_adjustmentvoucher);
        context= ApplicationUtil.getContext();
        adjsutment_voucher_swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllVouchers();
            }
        });

        search_adjustmentvoucher_button.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View arg0) {
            }
        });
        getAllVouchers();

    }

    private void getAllVouchers() {
        adjsutment_voucher_swl.setRefreshing(true);
        avlist=new ArrayList<>();
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.Get_All_ADJUSTMENT_VOUCHERS,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                adjsutment_voucher_swl.setRefreshing(false);
                                //convert form json to object
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    //map result data to local model class
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        AdjustmentVoucher av= (AdjustmentVoucher) EntityUtil.map2Object((Map<String, Object>) ((ArrayList) result.getData()).get(i), AdjustmentVoucher.class);
                                        isSubmitted=av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDING_APPROVAL)||av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.PENDMANAPPROV)
                                                ||av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.APPROVED)||av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.REJECTED);
                                        if(isSubmitted){
                                        avlist.add(av);

                                      }
                                    }
                                    displayAdjustmentVoucherList(avlist);
                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                adjsutment_voucher_swl.setRefreshing(false);
                                Toast.makeText(context,"error in retrieving adjustment vouchers",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });

    }

    private void displayAdjustmentVoucherList(List<AdjustmentVoucher> avlist) {
        //adapter to each item
        myAdapter1 = new MyAdapter<AdjustmentVoucher>((ArrayList) avlist, R.layout.item_adjustmentvoucher) {
            @Override
            public void bindView(ViewHolder holder, AdjustmentVoucher av) {
                holder.setText(R.id.av_id, av.getId());
                holder.setText(R.id.initiated_by, av.getInitiatedClerk().getName());
                holder.setText(R.id.date_issued, TimeUtil.convertTimestampToyyyyMMddHHmm(av.getInitiatedDate()));
                holder.setText(R.id.adjustmentvoucher_status, av.getStatus() + "");
                holder.setOnClickListener(R.id.item_adjustmentvoucher_row, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String avid = (String) ((TextView) holder.getView(R.id.av_id)).getText();
                        Intent intent = new Intent(context, StoreSupAVDetailActivity.class);
                        intent.putExtra("adjustmentvoucherId",avid);
                        startActivity(intent);
                    }
                });
                if (av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.APPROVED) ||
                        av.getStatus().equals(CommonConstant.AdjsutmentVoucherStatus.REJECTED)) {
                    holder.setVisibility(R.id.apprve_or_reject, View.INVISIBLE);
                }

            }

        };
        allvoucher_list.setAdapter(myAdapter1);
    }

}