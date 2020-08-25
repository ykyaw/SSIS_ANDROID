package iss.team1.ad.ssis_android.comm.utils;

import android.app.Application;
import android.content.Context;

import iss.team1.ad.ssis_android.modal.Employee;

/**
 * get application context
 * @author WUYUDING
 */
public class ApplicationUtil extends Application {
    private static Context context;
    private static Employee currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

    public static Employee getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(Employee user){
        currentUser=user;
    }


}
