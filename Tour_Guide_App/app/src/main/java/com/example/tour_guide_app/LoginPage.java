package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity
{
    EditText ed1,ed2;
    Button b1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ed1=(EditText)findViewById(R.id.username);
        ed2=(EditText)findViewById(R.id.password);

        tv1=(TextView)findViewById(R.id.signup_tv);
        tv1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1=new Intent(LoginPage.this,RegisterPage.class);
                startActivity(i1);
            }
        });

        b1=(Button)findViewById(R.id.login) ;
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String username=ed1.getText().toString();
                String password=ed2.getText().toString();
                if (username.equals("")||password.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter details!!!",Toast.LENGTH_SHORT).show();
                }
                else if(username.equals("admin") && password.equals("admin"))
                {
                    Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent i1= new Intent(LoginPage.this,AdminHome.class);
                    startActivity(i1);
                }
                else
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/find_login/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is: ", response.toString());
                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("User"))
                                {
                                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sp = getSharedPreferences("userdetails", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit
                                            = sp.edit();
                                    myEdit.putString("username", username);
                                    myEdit.commit();

                                    Intent i1= new Intent(LoginPage.this,UserHome.class);
                                    startActivity(i1);
                                }
                                else
                                {
                                    Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> m=new HashMap<>();
                            m.put("username",username);
                            m.put("password",password);

                            return m;
                        }
                    };
                    requestQueue.add(requ);
                }
            }
        });


    }
}