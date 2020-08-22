package iss.team1.ad.ssis_android.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.activity.HomeActivity;
import iss.team1.ad.ssis_android.activity.MainActivity;
import iss.team1.ad.ssis_android.adapter.MenuItemAdapter;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.components.MenuItem;
import iss.team1.ad.ssis_android.modal.Employee;

import static android.content.Context.MODE_PRIVATE;

public class MenuFragment extends Fragment {

    private ListView mListView;
    private List<MenuItem> menuItemList = new ArrayList<>();
    private MenuItemAdapter adapter;
    private Employee currentUser=null;

    private ImageView userPortrait;
    private TextView userName;
    private Context context;


    public void setCurrentUser(Employee user){
        this.currentUser=user;
        if(currentUser!=null) {
            userName.setText(currentUser.getName());
            initListView();
        }
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View navView = inflater.inflate(R.layout.menu, container, false);
        context= ApplicationUtil.getContext();
        bindView(navView);
        initListView();
        clickEvents();
        return navView;
    }


    private void bindView(View view){
        userName=(TextView)view.findViewById(R.id.current_user_name);
        userPortrait=(ImageView)view.findViewById(R.id.current_user_portrait);
        mListView = (ListView) view.findViewById(R.id.menu_list_view);
        mListView.setDivider(null);
    }


    public void initListView() {
        menuItemList.clear();
        String[] menu_store_clerk=getResources().getStringArray(R.array.menu_store_clerk);
        String[] menu_store_supervisor=getResources().getStringArray(R.array.menu_store_supervisor);
        String[] menu_dept_head=getResources().getStringArray(R.array.menu_dept_head);
        String[] menu_dept_repo=getResources().getStringArray(R.array.menu_dept_repo);
        String[] current_menu=menu_store_clerk;
        if(currentUser!=null){
            switch (currentUser.getRole()){
                case CommonConstant.ROLE.STORE_MANAGER:
                case CommonConstant.ROLE.STORE_SUPERVISOR:
                    current_menu=menu_store_supervisor;
                    break;
                case CommonConstant.ROLE.DEPARTMENT_EMPLOYEE:
                    current_menu=menu_dept_repo;
                    break;
                case CommonConstant.ROLE.DEPARTMENT_HEAD:
                    current_menu=menu_dept_head;
                    break;
                default:
                    current_menu=menu_store_clerk;
                    break;
            }
        }
        for (int i = 0; i < current_menu.length; i++) {
            MenuItem menuItem = new MenuItem(current_menu[i]);
            menuItemList.add(menuItem);
        }
        adapter = new MenuItemAdapter(getActivity(), R.layout.menu_list_item, menuItemList);
        mListView.setAdapter(adapter);
    }


    public void clickEvents() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] menu_store_clerk=getResources().getStringArray(R.array.menu_store_clerk);
                String[] menu_store_supervisor=getResources().getStringArray(R.array.menu_store_supervisor);
                String[] menu_dept_head=getResources().getStringArray(R.array.menu_dept_head);
                String[] menu_dept_repo=getResources().getStringArray(R.array.menu_dept_repo);
                String[] current_menu=menu_store_clerk;
                if(currentUser!=null){
                    switch (currentUser.getRole()){
                        case CommonConstant.ROLE.STORE_MANAGER:
                        case CommonConstant.ROLE.STORE_SUPERVISOR:
                            current_menu=menu_store_supervisor;
                            break;
                        case CommonConstant.ROLE.DEPARTMENT_EMPLOYEE:
                            current_menu=menu_dept_repo;
                            break;
                        case CommonConstant.ROLE.DEPARTMENT_HEAD:
                            current_menu=menu_dept_head;
                            break;
                        default:
                            current_menu=menu_store_clerk;
                            break;
                    }
                }
                if(position==current_menu.length-1){
                    //click logout
                    SharedPreferences pref=ApplicationUtil.getContext().getSharedPreferences("user_credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.clear();
                    editor.apply();

                    Intent intent=new Intent(ApplicationUtil.getContext(), MainActivity.class);
                    startActivity(intent);
                    return;
                }
                adapter.changeSelected(position);
                HomeActivity activity = (HomeActivity) getActivity();
                DrawerLayout mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawer(Gravity.START);
                activity.switchFragment(position);
            }
        });
    }
}