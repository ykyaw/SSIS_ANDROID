package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.MainActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Product;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;
import iss.team1.ad.ssis_android.modal.Retrieval;

public class RetrievalFragment extends Fragment {

    private ListView retrieval_list;
    private TextView retrieval_date_select;

    int mYear;
    int mMonth ;
    int mDay;
    String startday;

    private Context context;


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
        context= ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void init(View view){
        retrieval_list=(ListView)view.findViewById(R.id.retrieval_list);
        retrieval_date_select=(TextView)view.findViewById(R.id.retrieval_date_select);


        retrieval_date_select.setOnClickListener(new View.OnClickListener() {

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
                        retrieval_date_select.setText(startday);
                        try {
                            generateRetrievalForm(TimeUtil.convertyyyyMMddToTimestamp(startday));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new DatePickerDialog(getActivity(), onDateSetListener, year,
                        month, day).show();
            }
        });
    }

    private void generateRetrievalForm(long date) throws JSONException {
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GENERATE_RETRIEVAL_FORM(date),
                        new JSONObject(),new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                        if(result.getCode()==200){
                            Retrieval retrieval = (Retrieval) EntityUtil.map2Object((Map<String, Object>) result.getData(), Retrieval.class);
                            displayRetrievalTable(retrieval);
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

    private void displayRetrievalTable(Retrieval retrieval){

        myAdapter1 = new MyAdapter<RequisitionDetail>((ArrayList)retrieval.getRequisitionDetails(),R.layout.item_retrieval) {
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