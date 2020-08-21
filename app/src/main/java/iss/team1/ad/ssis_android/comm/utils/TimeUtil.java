package iss.team1.ad.ssis_android.comm.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
    public static long getCurrentTimestamp(){
        return new Date().getTime();
    }

    /**
     * @author WUYUDING
     * @param timestamp
     * @return
     */
    public static String convertTimestampToyyyyMMddHHmm(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String tsStr = sdf.format(ts);
        return tsStr;
    }

    public static long convertyyyyMMddToTimestamp(String time){
        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
        sp.setTimeZone(TimeZone.getTimeZone("UTC"));
        long timestamp = sp.parse(time, new ParsePosition(0)).getTime() ;
        return timestamp;

    }
}
