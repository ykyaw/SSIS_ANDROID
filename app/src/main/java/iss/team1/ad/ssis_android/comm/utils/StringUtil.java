package iss.team1.ad.ssis_android.comm.utils;

import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isNumeric(String text){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(text).matches();
    }
}
