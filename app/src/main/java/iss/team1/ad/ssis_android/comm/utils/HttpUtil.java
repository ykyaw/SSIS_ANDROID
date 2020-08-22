package iss.team1.ad.ssis_android.comm.utils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author WUYUDING
 */
public class HttpUtil {

    private static final RequestQueue mRequestQueue = Volley.newRequestQueue(ApplicationUtil.getContext());
    private LruCache<String, Bitmap> mCache = null;

    private static final class Holder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return Holder.INSTANCE;
    }

    public final void sendStringRequest(String url, Response.Listener<String> success, Response.ErrorListener error) {
        StringRequest stringRequest = new StringRequest(url, success, error){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                String token = getToken();
                if(!token.isEmpty()){
                    params.put("Authorization","Bearer "+token);
                }
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public final void sendStringRequest(int method, String url, Response.Listener<String> success,
                                        Response.ErrorListener error, final Map<String, String> params) {
        StringRequest stringRequest = new StringRequest(method, url, success, error) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                String token = getToken();
                if(!token.isEmpty()){
                    params.put("Authorization","Bearer "+token);
                }
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000*2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public final void sendJSONRequest(int method, String url, JSONObject params,
                                      Response.Listener<JSONObject> success, Response.ErrorListener error) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, params, success, error){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                String token = getToken();
                if(!token.isEmpty()){
                    params.put("Authorization","Bearer "+token);
                }
                return params;
            }
        };
        mRequestQueue.add(jsonObjectRequest);
    }

    public final void sendJSONRequest(int method, String url, JSONArray params,
                                      Response.Listener<JSONObject> success, Response.ErrorListener error) {
        MyJsonObjectRequest request = new MyJsonObjectRequest(method, url, params, success, error){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                String token = getToken();
                if(!token.isEmpty()){
                    params.put("Authorization","Bearer "+token);
                }
                return params;
            }
        };
        mRequestQueue.add(request);
    }


    public final void sendImageRequest(String url, int maxWidth, int maxHeight, Bitmap.Config decodeConfig,
                                       Response.Listener<Bitmap> success, Response.ErrorListener error) {
        ImageRequest imageRequest = new ImageRequest(url, success, maxWidth, maxHeight, decodeConfig, error);
        mRequestQueue.add(imageRequest);
    }


    public final void sendImageRequest(String url, Response.Listener<Bitmap> success, Response.ErrorListener error) {
        ImageRequest imageRequest = new ImageRequest(url, success, 0, 0, Bitmap.Config.RGB_565, error);
        mRequestQueue.add(imageRequest);
    }


    public final void sendImageLoader(String url, ImageView target, int defalutRes, int errorRes) {
        ImageLoader imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                final int maxSize = 10 * 1024 * 1024;
                mCache = new LruCache<String, Bitmap>(maxSize) {
                    @Override
                    protected int sizeOf(String key, Bitmap bitmap) {
                        return bitmap.getRowBytes() * bitmap.getHeight();
                    }
                };
                return mCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                mCache.put(s, bitmap);
            }
        });
        ImageLoader.ImageListener imageListener = ImageLoader.
                getImageListener(target, defalutRes, errorRes);
        imageLoader.get(url, imageListener);
    }

    public static String getToken(){
        SharedPreferences pref = ApplicationUtil.getContext().getSharedPreferences("user_credentials", MODE_PRIVATE);
        if(pref.contains("token")){
            return pref.getString("token","");
        }
        return "";
    }

    public class MyJsonObjectRequest extends JsonRequest<JSONObject>{
        public MyJsonObjectRequest(int method, String url, JSONArray jsonRequest,
                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                    errorListener);

        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }

}
