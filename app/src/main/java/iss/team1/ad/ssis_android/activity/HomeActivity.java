package iss.team1.ad.ssis_android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.components.ActivityCollector;
import iss.team1.ad.ssis_android.fragment.AckReceiveFragment;
import iss.team1.ad.ssis_android.fragment.DelegateFragment;
import iss.team1.ad.ssis_android.fragment.DisbursementFragment;
import iss.team1.ad.ssis_android.fragment.MenuFragment;
import iss.team1.ad.ssis_android.fragment.RetrievalFragment;
import iss.team1.ad.ssis_android.fragment.RetrieveAllVouchersFragment;
import iss.team1.ad.ssis_android.fragment.TabFragment;
import iss.team1.ad.ssis_android.modal.Employee;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private FrameLayout contentFrameLayout;
    private Fragment currentFragment;
    private List<Fragment> tabFragments = new ArrayList<>();
    private Context context;
    private Employee currentUser=null;
    private FrameLayout menuContent;
    private int isGetPortrait=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent=getIntent();
        currentUser=(Employee)intent.getSerializableExtra("currentUser");
//        currentUser=new Employee();
//        currentUser.setName("Wee Kian Fatt(de)");
//        currentUser.setRole("de");
        FragmentManager fragmentManager = getSupportFragmentManager();
        final MenuFragment menuFragment=(MenuFragment)fragmentManager.findFragmentById(R.id.nav_view);
        menuFragment.setCurrentUser(currentUser);

        init();
    }


    private void init(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT); // ensure the content wll not be covered by shadow when sliding

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); // do not display the application name
        toolbar.bringToFront();
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final CardView cardView = (CardView) findViewById(R.id.card_view);
        switch (currentUser.getRole()){
            case CommonConstant.ROLE.DEPARTMENT_EMPLOYEE:
                tabFragments.add(new AckReceiveFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                break;
            case CommonConstant.ROLE.DEPARTMENT_HEAD:
                tabFragments.add(new DelegateFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                break;
            case CommonConstant.ROLE.STORE_CLERK:
                tabFragments.add(new RetrievalFragment());
                tabFragments.add(new DisbursementFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                break;
            case CommonConstant.ROLE.STORE_MANAGER:
            case CommonConstant.ROLE.STORE_SUPERVISOR:
                tabFragments.add(new RetrieveAllVouchersFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                tabFragments.add(new TabFragment());
                break;
        }


        contentFrameLayout = (FrameLayout) findViewById(R.id.content_view);
        currentFragment = tabFragments.get(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_view, currentFragment).commit();


        /**
         * listen to the drawer slide event
         */
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                float leftScale = 0.5f + slideOffset * 0.5f;
                mMenu.setAlpha(leftScale);
                mMenu.setScaleX(leftScale);
                mMenu.setScaleY(leftScale);
                mContent.setPivotX(0);
                mContent.setPivotY(mContent.getHeight() * 1 / 2);
                mContent.setScaleX(rightScale);
                mContent.setScaleY(rightScale);
                mContent.setTranslationX(mMenu.getWidth() * slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                cardView.setRadius(20);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                cardView.setRadius(0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    /**
     * change main view's fragment, avoid duplicate instantiate
     * @param position
     */
    public void switchFragment(int position) {
        Fragment fragment = tabFragments.get(position);
        if (currentFragment != fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (fragment.isAdded()) {
                transaction.hide(currentFragment)
                        .show(fragment)
                        .commit();
            } else {
                transaction.hide(currentFragment)
                        .add(R.id.content_view, fragment)
                        .commit();
            }
            currentFragment = fragment;
        }
    }

    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    /**
     * double back to exit the app
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(context,"press again to exit app",Toast.LENGTH_LONG).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                ActivityCollector.AppExit(context);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}