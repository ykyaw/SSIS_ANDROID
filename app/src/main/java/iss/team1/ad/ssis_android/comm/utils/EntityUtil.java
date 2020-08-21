package iss.team1.ad.ssis_android.comm.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author WUYUIDNG
 */
public class EntityUtil {

    public static JSONObject object2JSONObject(Object obj){
        JSONObject result=new JSONObject(object2Map(obj));
        return result;
    }


    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj =null;
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(map);
        obj = gson.fromJson(jsonElement, clazz);

        return obj;
    }
}
