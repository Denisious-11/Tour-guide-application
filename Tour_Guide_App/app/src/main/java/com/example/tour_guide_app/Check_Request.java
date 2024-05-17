package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour_guide_app.RecyclerAdapter.Chat_users_recy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Check_Request extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_request);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");

        // get the intent that was used to start the activity
        Intent intent = getIntent();

        // retrieve the value using the key that was used to pass it
        String selected_user = intent.getStringExtra("unm");

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/check_requests/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject o = new JSONObject(response);
                    String dat = o.getString("msg");
                    String s_unm = o.getString("unm");
                    if(dat.equals("Exist"))
                    {
                        Intent i1=new Intent(Check_Request.this,User_chat.class);
                        i1.putExtra("unm",s_unm);
                        startActivity(i1);
                    }
                    else if(dat.equals("Wait"))
                    {
                        Toast.makeText(Check_Request.this, "Friend Request is in Pending List", Toast.LENGTH_LONG).show();
                        Intent i3=new Intent(Check_Request.this,UsersList.class);
                        startActivity(i3);
                    }
                    else
                    {
                        Intent i2=new Intent(Check_Request.this,Request_Button.class);
                        i2.putExtra("unm",s_unm);
                        startActivity(i2);

                    }

                } catch (JSONException e) {
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
                m.put("s_username",selected_user);

                return m;
            }
        };
        requestQueue.add(requ);


    }
}