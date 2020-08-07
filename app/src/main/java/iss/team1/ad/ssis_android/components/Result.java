package iss.team1.ad.ssis_android.components;

import java.io.Serializable;

/**
 * model of the http request global result
 * @author WUYUDING
 */
public class Result implements Serializable {

    private int code  = 200;

    private Object data ;

    private String msg ;

    private String sub_msg ;

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(int code, Object data, String msg, String sub_msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.sub_msg = sub_msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }
}
