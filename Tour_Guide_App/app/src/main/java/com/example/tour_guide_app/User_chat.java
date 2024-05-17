package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class User_chat extends AppCompatActivity
{

    Button b1;
    EditText t1;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String sender=prefs.getString("username", "");

        String receiver=getIntent().getExtras().getString("unm");

        Log.e("SENDER : >",sender);
        Log.e("RECIEVER  : >",receiver);

        final LinearLayoutCompat l2 = (LinearLayoutCompat)findViewById(R.id.usch_l);
        l2.setPadding(25,25,25,25);
        b1=(Button)findViewById(R.id.snd_ub);
        t1=(EditText)findViewById(R.id.snd_ut);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/filter_/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response.trim());

                    JSONArray jsonArray = object.getJSONArray("data");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject result = jsonArray.getJSONObject(i);
                        Log.e("result", String.valueOf(result));

                        LinearLayout r1 = new LinearLayout(User_chat.this);
                        tv2 = new TextView(User_chat.this);
                        r1.setOrientation(LinearLayout.VERTICAL);
                        String msgsndr=result.getString("sender");
                        String message=result.getString("message");

                        tv2.setText("    "+message+"    ");
                        tv2.setTextSize(20);
                        tv2.setBackgroundResource(R.drawable.chatreciever);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                150);
                        tv2.setLayoutParams(params);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        r1.setLayoutParams(params1);
                        r1.addView(tv2);
                        if (msgsndr.equals(sender)){
                            r1.setGravity(Gravity.RIGHT);
                        }
                        else {
                            r1.setGravity(Gravity.LEFT);
                        }

                        l2.addView(r1);

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
                m.put("sender",sender);
                m.put("receiver",receiver);

                return m;
            }
        };
        requestQueue.add(requ);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String tx = t1.getText().toString();

                LinearLayout r1 = new LinearLayout(User_chat.this);
                tv2 = new TextView(User_chat.this);

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/send_message/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Response is: ", response.toString());
                        try
                        {
                            JSONObject o = new JSONObject(response);
                            String dat = o.getString("msg");



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

                        m.put("sender",sender);
                        m.put("receiver",receiver);
                        m.put("message",tx);
                        m.put("date_time",currentDateandTime);
                        return m;
                    }
                };
                requestQueue.add(requ);

                tv2.setText(tx);
                t1.setText("");
                r1.setOrientation(LinearLayout.VERTICAL);
                tv2.setText("    "+tx+"    ");
                tv2.setTextSize(20);
                tv2.setBackgroundResource(R.drawable.chatreciever);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        150);
                tv2.setLayoutParams(params);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                r1.setLayoutParams(params1);
                r1.addView(tv2);
                r1.setGravity(Gravity.RIGHT);
                l2.addView(r1);



            }



        });

    }
}