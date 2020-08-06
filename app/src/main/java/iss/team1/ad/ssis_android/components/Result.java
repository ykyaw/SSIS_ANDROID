package iss.team1.ad.ssis_android.components;

import java.io.Serializable;

/**
 * model of the http request global result
 * @author WUYUDING
 */
public class Result implements Serializable {

    private Object Value;

    public Result(Object value) {
        this.Value = value;
    }

    public Result() {
    }

    public Object getValue(){
        return Value;
    }

    public void setValue(Object value){
        this.Value=value;
    }
}
