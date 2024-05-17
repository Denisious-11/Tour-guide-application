package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterPage extends AppCompatActivity
{
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button b1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        ed1=(EditText)findViewById(R.id.name);
        ed2=(EditText)findViewById(R.id.address);
        ed3=(EditText)findViewById(R.id.mobile);
        ed4=(EditText)findViewById(R.id.email);
        ed5=(EditText)findViewById(R.id.username);
        ed6=(EditText)findViewById(R.id.pass1);
        ed7=(EditText)findViewById(R.id.pass2);

        tv1=(TextView) findViewById(R.id.login_tv);
        tv1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1=new Intent(RegisterPage.this,LoginPage.class);
                startActivity(i1);
            }
        });

        b1=(Button)findViewById(R.id.signup);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name=ed1.getText().toString();
                String address=ed2.getText().toString();
                String mobile=ed3.getText().toString();
                String email=ed4.getText().toString();
                String username=ed5.getText().toString();
                String pass=ed6.getText().toString();
                String cpass=ed7.getText().toString();



                if (name.equals("")||address.equals("")||mobile.equals("")||email.equals("")||username.equals("")||pass.equals("")||cpass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Fill all the details!!!",Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(cpass))
                {
                    Toast.makeText(getApplicationContext(), "Passwords should be same!!", Toast.LENGTH_SHORT).show();
                }
                else if((username.length()<5)||(pass.length()<5))
                {
                    Toast.makeText(getApplicationContext(),"Username/Password should contain atleast 5 characters",Toast.LENGTH_SHORT).show();
                }
                else if (mobile.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Phone number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/register/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is: ", response.toString());
                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("yes"))
                                {
                                    Toast.makeText(RegisterPage.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                    Intent i1=new Intent(RegisterPage.this,LoginPage.class);
                                    startActivity(i1);
                                }
                                else if(dat.equals("Username Already Taken"))
                                {
                                    Toast.makeText(RegisterPage.this, "This username is already taken", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(RegisterPage.this, "Error Happened!!!", Toast.LENGTH_LONG).show();
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
                            m.put("name",name);
                            m.put("address",address);
                            m.put("mobile",mobile);
                            m.put("email",email);
                            m.put("username",username);
                            m.put("password",pass);

                            return m;
                        }
                    };
                    requestQueue.add(requ);
//
                }
            }
        });
    }
}