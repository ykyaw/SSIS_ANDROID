package iss.team1.ad.ssis_android.comm.utils;

import android.app.Application;
import android.content.Context;

/**
 * get application context
 * @author WUYUDING
 */
public class ApplicationUtil extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }


}
