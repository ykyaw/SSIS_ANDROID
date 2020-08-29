package iss.team1.ad.ssis_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.PurchaseRequestDeatilActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.PurchaseRequestDetail;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class PurchaseRquestFragment extends Fragment {

    private SwipeRefreshLayout purchase_request_swl;
    private ListView purchase_request_list;

    private Map<Long,List<PurchaseRequestDetail>> renderData;
    private MyAdapter<Map.Entry<Long,List<PurchaseRequestDetail>>> detailMyAdapter;

    private Context context;

    public PurchaseRquestFragment() {
    }


    public static PurchaseRquestFragment newInstance(String param1, String param2) {
        PurchaseRquestFragment fragment = new PurchaseRquestFragment();
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
        View view=inflater.inflate(R.layout.fragment_purchase_rquest, container, false);
        context= ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        purchase_request_swl=view.findViewById(R.id.purchase_request_swl);
        purchase_request_list=view.findViewById(R.id.purchase_request_list);
        purchase_request_swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPurchaseRequests();
            }
        });
        fetchPurchaseRequests();

    }

    private void fetchPurchaseRequests(){
        purchase_request_swl.setRefreshing(true);
        renderData=new HashMap<>();
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.PURCHASE_REQUEST,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                purchase_request_swl.setRefreshing(false);
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    List<PurchaseRequestDetail> purchaseRequestDetails=new ArrayList<>();
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        purchaseRequestDetails.add((PurchaseRequestDetail) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),PurchaseRequestDetail.class));
                                    }

                                    for(PurchaseRequestDetail detail:purchaseRequestDetails){
                                        if(renderData.containsKey(detail.getPurchaseRequestId())){
                                            renderData.get(detail.getPurchaseRequestId()).add(detail);
                                        }else{
                                            renderData.put(detail.getPurchaseRequestId(),new ArrayList<>());
                                            renderData.get(detail.getPurchaseRequestId()).add(detail);
                                        }
                                    }

                                    detailMyAdapter=new MyAdapter<Map.Entry<Long, List<PurchaseRequestDetail>>>(new ArrayList<>(renderData.entrySet()),R.layout.item_purchase_request) {
                                        @Override
                                        public void bindView(ViewHolder holder, Map.Entry<Long, List<PurchaseRequestDetail>> obj) {
                                            holder.setText(R.id.item_purchase_request_id,obj.getKey()+"");
                                            holder.setText(R.id.item_purchase_request_status,obj.getValue().get(0).getStatus());
                                            holder.setOnClickListener(R.id.item_purchase_request_row, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent=new Intent(context, PurchaseRequestDeatilActivity.class);
                                                    intent.putExtra("id",obj.getKey()+"");
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    };
                                    purchase_request_list.setAdapter(detailMyAdapter);
                                }else{
                                    Toast.makeText(context,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                purchase_request_swl.setRefreshing(false);
                                Toast.makeText(context,"invalid token",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
    }
}