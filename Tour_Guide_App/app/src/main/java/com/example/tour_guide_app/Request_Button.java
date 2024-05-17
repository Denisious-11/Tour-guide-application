package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request_Button extends AppCompatActivity
{
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_button);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");


        Intent intent = getIntent();
        // retrieve the value using the key that was used to pass it
        String selected_user = intent.getStringExtra("unm");

        b1=(Button)findViewById(R.id.req_button);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/sent_req/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            String dat = o.getString("msg");
                            if(dat.equals("request sent"))
                            {
                                Toast.makeText(Request_Button.this, "Friend Request Sent Successfully", Toast.LENGTH_SHORT).show();
                                Intent i1=new Intent(Request_Button.this,UserHome.class);
                                startActivity(i1);
                            }

                        } catch (JSONException e)
                        {
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
                        m.put("sender_username",username);
                        m.put("receiver_username",selected_user);

                        return m;
                    }
                };
                requestQueue.add(requ);
            }
        });


    }
}