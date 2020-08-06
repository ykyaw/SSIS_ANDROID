package iss.team1.ad.ssis_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.bean.User;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EncrypUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button testToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        testToken=(Button)findViewById(R.id.testToken);

        testToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST,CommonConstant.HttpUrl.TEST,new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getValue()!=null){
                                    Toast.makeText(MainActivity.this,result.getValue().toString(),Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(MainActivity.this,"return value is null",Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String hashPwd= EncrypUtil.getSHA256String(passwordText);

                User user=new User();
                user.setEmail(emailText);
                user.setPassword(hashPwd);
                Map<String,String> params=new HashMap<>();
                params.put("Value",JSONUtil.ObjectToJson(user));

                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST,CommonConstant.HttpUrl.LOGIN,new JSONObject(params),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getValue()==null){
                                    Toast.makeText(MainActivity.this,"email or password incorrect.",Toast.LENGTH_LONG).show();
                                }else{
                                    SharedPreferences pref=getSharedPreferences("user_credentials",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putString("token",result.getValue().toString());
                                    editor.apply();

                                    startHomeActivity();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
            }
        });
    }

    private void startHomeActivity(){
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

}