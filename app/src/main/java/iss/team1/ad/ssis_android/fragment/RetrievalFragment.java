package iss.team1.ad.ssis_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.modal.Product;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;
import iss.team1.ad.ssis_android.modal.Retrieval;

public class RetrievalFragment extends Fragment {

    private ListView retrieval_list;


    private MyAdapter<RequisitionDetail> myAdapter1 = null;

    public RetrievalFragment() {
        // Required empty public constructor
    }

    public static RetrievalFragment newInstance(String param1, String param2) {
        RetrievalFragment fragment = new RetrievalFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_retrieval, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        retrieval_list=(ListView)view.findViewById(R.id.retrieval_list);

        List<RequisitionDetail> requisitionDetails=new ArrayList<>();
        Product product=new Product("E001","This is a product");
        requisitionDetails.add(new RequisitionDetail(5,product));
        requisitionDetails.add(new RequisitionDetail(6,product));
        requisitionDetails.add(new RequisitionDetail(7,product));
        requisitionDetails.add(new RequisitionDetail(8,product));

        myAdapter1 = new MyAdapter<RequisitionDetail>((ArrayList)requisitionDetails,R.layout.item_retrieval) {
            @Override
            public void bindView(ViewHolder holder, RequisitionDetail detail) {
                holder.setText(R.id.item_code,detail.getProduct().getId());
                holder.setText(R.id.item_description,detail.getProduct().getDescription());
                holder.setText(R.id.qty_needed,detail.getQtyNeeded()+"");
            }
        };

        retrieval_list.setAdapter(myAdapter1);
    }
}