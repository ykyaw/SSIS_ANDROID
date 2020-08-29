package iss.team1.ad.ssis_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;
import iss.team1.ad.ssis_android.comm.utils.EntityUtil;
import iss.team1.ad.ssis_android.components.ActivityCollector;
import iss.team1.ad.ssis_android.components.Result;
import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.comm.CommonConstant;
import iss.team1.ad.ssis_android.comm.utils.EncrypUtil;
import iss.team1.ad.ssis_android.comm.utils.HttpUtil;
import iss.team1.ad.ssis_android.comm.utils.JSONUtil;
import iss.team1.ad.ssis_android.modal.Employee;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button testToken;
    private Button clearToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCollector.addActivity(this);
        SharedPreferences pref = ApplicationUtil.getContext().getSharedPreferences("user_credentials", MODE_PRIVATE);
        if(pref.contains("token")){
        }
        init();
    }

    private void init(){
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        testToken=(Button)findViewById(R.id.testToken);
        clearToken=(Button)findViewById(R.id.clearToken);

        clearToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref=getSharedPreferences("user_credentials",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.clear();
                editor.apply();
                Toast.makeText(MainActivity.this,"clear token successfully",Toast.LENGTH_LONG).show();
            }
        });

        testToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST,CommonConstant.HttpUrl.TEST,new JSONObject(),new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){
                                    Toast.makeText(MainActivity.this,result.getData().toString(),Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(MainActivity.this,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,"invalid token",Toast.LENGTH_LONG).show();
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

                Employee user=new Employee();
                user.setEmail(emailText);
                user.setPassword(hashPwd);

                HttpUtil.getInstance()
                        .sendJSONRequest(Request.Method.POST,CommonConstant.HttpUrl.LOGIN, EntityUtil.object2JSONObject(user),
                                new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Result result = (Result) JSONUtil.JsonToObject(response.toString(), Result.class);
                                if(result.getCode()==200){

                                    String token = ((Map<String, Object>) result.getData()).get("token").toString();
                                    SharedPreferences pref=getSharedPreferences("user_credentials",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putString("token",token);
                                    editor.apply();
                                    Map<String,Object> t= (Map<String, Object>) ((Map<String,Object>)result.getData()).get("employee");
                                    Employee currentUser= (Employee) EntityUtil.map2Object(t,Employee.class);
                                    startHomeActivity(currentUser);
                                }else{
                                    Toast.makeText(MainActivity.this,result.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,"email or password incorrect",Toast.LENGTH_LONG).show();
                                System.out.println("error");
                                error.printStackTrace();
                                System.out.println(error.getMessage());

                            }
                        });
            }
        });
    }

    private void startHomeActivity(Employee employee){
        Intent intent=new Intent(this,HomeActivity.class);
        intent.putExtra("currentUser",employee);
        startActivity(intent);
    }

}