package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GiveRating extends AppCompatActivity
{
    Button b1;
    RatingBar rb1,rb2;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");

        tv1=(TextView)findViewById(R.id.d1);
        tv2=(TextView)findViewById(R.id.d2);

        ArrayList<String> name_list = (ArrayList<String>) getIntent().getSerializableExtra("pname");
        Log.e("GET Names : ", String.valueOf(name_list));

        String val_1 = name_list.get(0);
        String val_2 = name_list.get(1);
        Log.e("first place ; >>>>>", val_1);
        Log.e("second place ; >>>>>", val_2);

        tv1.setText(val_1);
        tv2.setText(val_2);

        rb1=(RatingBar)findViewById(R.id.ratingBar1);
        rb2=(RatingBar)findViewById(R.id.ratingBar2);

        b1=(Button) findViewById(R.id.sub_button);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String rating1=String.valueOf(rb1.getRating());
                String rating2=String.valueOf(rb2.getRating());
                String place1=tv1.getText().toString();
                String place2=tv2.getText().toString();

                Log.e("rating 1 : ",rating1);
                Log.e("rating 2 : ",rating2);
                if (rating1.equals("0.0")||rating2.equals("0.0"))
                {
                    Toast.makeText(getApplicationContext(),"Please Do Rating",Toast.LENGTH_LONG).show();
                }
                else
                {

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ = new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Give_rating_one/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is: ", response.toString());
                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if (dat.equals("yes")) {
                                    Toast.makeText(GiveRating.this, "Rated Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent i2 = new Intent(GiveRating.this, UserHome.class);
                                    startActivity(i2);
                                } else {
                                    Toast.makeText(GiveRating.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> m = new HashMap<>();

                            m.put("place1", place1);
                            m.put("place2", place2);
                            m.put("username", username);
                            m.put("rating1", rating1);
                            m.put("rating2", rating2);

                            return m;
                        }
                    };
                    requestQueue.add(requ);
                }
            }
        });
    }
}