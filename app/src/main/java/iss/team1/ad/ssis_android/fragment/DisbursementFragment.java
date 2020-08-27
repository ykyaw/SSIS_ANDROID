package iss.team1.ad.ssis_android.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.DisbursementDetailActivity;
import iss.team1.ad.ssis_android.adapter.MyAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.comm.utils.StringUtil;
import iss.team1.ad.ssis_android.comm.utils.TimeUtil;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.modal.Department;
import iss.team1.ad.ssis_android.modal.Requisition;
import iss.team1.ad.ssis_android.modal.RequisitionDetail;

public class DisbursementFragment extends Fragment {

    private TextView disbursement_date;
    private TextView disbursement_dept;
    private Button disbursement_search;
    private ListView fragment_disbursement_list;


    private MyAdapter<Department> spinnerItemMyAdapter;
    private MyAdapter<Requisition> disbursementAdapter;




    private Context context;


    String dept_select;
    int mYear;
    int mMonth;
    int mDay;
    String selectDay = null;

    private List<Department> departments = new ArrayList<>();
    private List<Requisition> requisitions;

    public DisbursementFragment() {
        // Required empty public constructor
    }


    public static DisbursementFragment newInstance(String param1, String param2) {
        DisbursementFragment fragment = new DisbursementFragment();
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
        View view = inflater.inflate(R.layout.fragment_disbursement, container, false);
        context = ApplicationUtil.getContext();
        init(view);
        return view;
    }

    private void fetchDisbursemnets(){
        requisitions=new ArrayList<>();
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_ALL_DISBURSEMENTS,
                        new JSONObject(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if (result.getCode() == 200) {
                                    for (int i = 0; i < ((ArrayList) result.getData()).size(); i++) {
                                        requisitions.add((Requisition) EntityUtil.map2Object((Map<String, Object>) ((ArrayList) result.getData()).get(i), Requisition.class));
                                    }
                                    setDisbursementAdapter();
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

    private void setDisbursementAdapter() {
        disbursementAdapter=new MyAdapter<Requisition>((ArrayList<Requisition>) requisitions,R.layout.item_disbursement_row) {
            @Override
            public void bindView(ViewHolder holder, Requisition obj) {
                holder.setText(R.id.item_disbursement_requisition_id,obj.getId()+"");
                holder.setText(R.id.item_disbursement_collection_date,TimeUtil.convertTimestampToyyyyMMdd(obj.getCollectionDate()));
                holder.setText(R.id.item_disbursement_department,obj.getDepartment().getName());
                holder.setText(R.id.item_disbursement_requested_by,obj.getReqByEmp().getName());
                holder.setText(R.id.item_disbursement_status,obj.getStatus());

                holder.setOnClickListener(R.id.item_disbursement_row, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDisbursementDetailActivity(obj.getCollectionDate(),obj.getDepartmentId());
                    }
                });
            }
        };

        fragment_disbursement_list.setAdapter(disbursementAdapter);
    }

    private void getAllDept() {
        HttpUtil.getInstance()
                .sendJSONRequest(Request.Method.GET, CommonConstant.HttpUrl.GET_ALL_DEPARTMENT,
                        new JSONObject(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if (result.getCode() == 200) {
                                    departments = new ArrayList<>();
                                    for (int i = 0; i < ((ArrayList) result.getData()).size(); i++) {
                                        departments.add((Department) EntityUtil.map2Object((Map<String, Object>) ((ArrayList) result.getData()).get(i), Department.class));
                                    }
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

    private void init(View view) {
        disbursement_dept = (TextView) view.findViewById(R.id.disbursement_dept);
        disbursement_date = (TextView) view.findViewById(R.id.disbursement_date);
        disbursement_search = (Button) view.findViewById(R.id.disbursement_search);
        fragment_disbursement_list = (ListView) view.findViewById(R.id.fragment_disbursement_list);

        getAllDept();

        fetchDisbursemnets();


        disbursement_dept.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getContext())
                        .hasShadowBg(false)
                        .isDestroyOnDismiss(false)
                        .atView(disbursement_dept)
                        .asAttachList(departments.stream().map(item -> item.getName()).toArray(String[]::new),
                                new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        disbursement_dept.setText(text);
                                        dept_select = departments.get(position).getId();
//                                        Toast.makeText(context,"click " + departments.get(position).getName(),Toast.LENGTH_LONG).show();
                                    }
                                })
                        .show();
            }
        });

        disbursement_date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View arg0) {
                Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MARCH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
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
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1).append("-")
                                        .append(mDay).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(mDay).toString();
                            } else {
                                selectDay = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1).append("-")
                                        .append(mDay)
                                        .toString();
                            }
                        }
                        disbursement_date.setText(selectDay);

                    }
                };
                new DatePickerDialog(getActivity(), onDateSetListener, year,
                        month, day).show();
            }
        });


        disbursement_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fetchRequisitionDetailsByDate();
                startDisbursementDetailActivity(TimeUtil.convertyyyyMMddToTimestamp(selectDay),dept_select);
            }
        });

    }

    private void startDisbursementDetailActivity(long date,String deptId) {
        Intent intent = new Intent(context, DisbursementDetailActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("deptId",deptId);
        startActivity(intent);
    }


}