package com.example.tour_guide_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour_guide_app.RecyclerAdapter.Get_request_recy;
import com.example.tour_guide_app.RecyclerAdapter.Get_users_recy;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowRequest extends AppCompatActivity
{
    RecyclerView recv;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);


        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");

        recv=(RecyclerView)findViewById(R.id.recyclerView10);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/get_my_requests/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response.trim());

                    JSONArray jsonArray = object.getJSONArray("data");
                    Get_request_recy ordrec = new Get_request_recy(ShowRequest.this, jsonArray, ShowRequest.this);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowRequest.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recv.getContext(),
                            linearLayoutManager.getOrientation());
                    recv.addItemDecoration(dividerItemDecoration);
                    recv.setLayoutManager(linearLayoutManager);
                    recv.setAdapter(ordrec);


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

                return m;
            }
        };
        requestQueue.add(requ);


        navigationView = (NavigationView) findViewById(R.id.nav5);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                if(id==R.id.home)
                {
                    Intent i4=new Intent(ShowRequest.this,UserHome.class);
                    startActivity(i4);

                }

                else if(id==R.id.search_places)
                {
                    Intent i2=new Intent(ShowRequest.this,SearchPlace.class);
                    startActivity(i2);
                }
                else if(id==R.id.show_request)
                {
                    Intent i5=new Intent(ShowRequest.this,ShowRequest.class);
                    startActivity(i5);
                }
                else if(id==R.id.chat)
                {
                    Intent i3=new Intent(ShowRequest.this,UsersList.class);
                    startActivity(i3);
                }
                else if(id==R.id.logout)
                {
                    Intent i1=new Intent(ShowRequest.this,LoginPage.class);
                    startActivity(i1);
                }
                return true;
            }
        });



    }
}