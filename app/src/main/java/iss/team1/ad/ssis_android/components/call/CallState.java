package iss.team1.ad.ssis_android.components.call;

import android.telecom.Call;

import java.util.ArrayList;
import java.util.List;

public class CallState implements ICallObserverable{

    private CallStateListener mCallListener=null;


    @Override
    public void registerObserver(CallStateListener o) {
        mCallListener=o;
    }

    @Override
    public void removeObserver(CallStateListener o) {
        mCallListener=null;
    }

    @Override
    public void notifyObserver(boolean result) {
        if(mCallListener!=null){
            mCallListener.update(result);
        }
    }
}
