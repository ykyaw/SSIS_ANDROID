package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.RetrievalFormActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Retrieval;

public class RetrievalFragment extends Fragment {

    private ListView fragment_retrieval_list;
    private TextView fragment_retrieval_date_select;
    private SwipeRefreshLayout fragment_retrieval_swl;

    int mYear;
    int mMonth ;
    int mDay;
    String startday;

    private Context context;

    private List<Retrieval> retrievals;


    private MyAdapter<Retrieval> myAdapter = null;

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
        context= ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void init(View view){
        fragment_retrieval_list=(ListView)view.findViewById(R.id.fragment_retrieval_list);
        fragment_retrieval_date_select=(TextView)view.findViewById(R.id.fragment_retrieval_date_select);
        fragment_retrieval_swl=view.findViewById(R.id.fragment_retrieval_swl);

        fragment_retrieval_swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRetrievalForms();
            }
        });

        fragment_retrieval_date_select.setOnClickListener(new View.OnClickListener() {

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
                                startday = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                startday = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1).append("-")
                                        .append(mDay).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                startday = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                startday = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1).append("-")
                                        .append(mDay)
                                        .toString();
                            }
                        }
                        fragment_retrieval_date_select.setText(startday);
                        startRetrievalFormActivity(startday,null);
                    }
                };
                new DatePickerDialog(getActivity(), onDateSetListener, year,
                        month, day).show();
            }
        });

        getRetrievalForms();
    }

    private void startRetrievalFormActivity(String startday,String RequisitionId) {
        Intent intent=new Intent(context,RetrievalFormActivity.class);
        intent.putExtra("collectionDate",startday);
        intent.putExtra("id",RequisitionId);
        startActivity(intent);
    }

    private void getRetrievalForms() {
        fragment_retrieval_swl.setRefreshing(true);
        retrievals=new ArrayList<>();
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.FETCH_RETRIEVAL_FORMS,
                        new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                fragment_retrieval_swl.setRefreshing(false);
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    for(int i=0;i<((ArrayList)result.getData()).size();i++){
                                        retrievals.add((Retrieval) EntityUtil.map2Object((Map<String, Object>) ((ArrayList)result.getData()).get(i),Retrieval.class));
                                        setAdapter();
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

    private void setAdapter() {
        myAdapter=new MyAdapter<Retrieval>((ArrayList<Retrieval>) retrievals,R.layout.item_retrieval) {
            @Override
            public void bindView(ViewHolder holder, Retrieval obj) {
                holder.setText(R.id.item_retrieval_id,obj.getId()+"");
                holder.setText(R.id.item_retrieval_disbursement_date, TimeUtil.convertTimestampToyyyyMMdd(obj.getDisbursedDate()));
                holder.setText(R.id.item_retrieval_retrieved_by,obj.getClerk().getName());
                holder.setText(R.id.item_retrieval_status,obj.getStatus());
                holder.setOnClickListener(R.id.item_retrieval_row, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startRetrievalFormActivity(null,obj.getId()+"");
                    }
                });
            }
        };
        fragment_retrieval_list.setAdapter(myAdapter);
    }


}