package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour_guide_app.RecyclerAdapter.Chat_users_recy;
import com.example.tour_guide_app.RecyclerAdapter.View_places_recy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsersList extends AppCompatActivity
{
    RecyclerView recv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");
        recv = (RecyclerView) findViewById(R.id.recyclerView2);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/users_list/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response.trim());

                    JSONArray jsonArray = object.getJSONArray("data");
                    Chat_users_recy ordrec = new Chat_users_recy(UsersList.this, jsonArray, UsersList.this);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UsersList.this);
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


    }
}