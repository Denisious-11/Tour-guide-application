package com.example.tour_guide_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour_guide_app.RecyclerAdapter.Show_places_recy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPlace extends AppCompatActivity
{
    String[] users = { "Architectural Buildings","Beaches","Religious Sites","Wildlife Sanctuaries",};
    String utype="Architectural Buildings";
    String[] paces = { "Low","High",};
    String pacetype="Low";
    String[] district = { "Ernakulam","Idukki",};
    String ini_district="Ernakulam";
    String[] distlist = {  "Ernakulam", "Idukki"};
    float[] latlist = { 9.9816f,9.9189f};
    float[] longlist ={ 76.2999f,77.1025f};
    float latval=0f;
    float longval=0f;
    EditText ed3;//ed1,ed2,
    Button b1,b2,b3;
    Spinner spin1;
    Spinner spin2;
    Spinner spin3;
    RecyclerView recv;
    ArrayList<String> latitudes = new ArrayList<String>();
    ArrayList<String> longitudes = new ArrayList<String>();
    ArrayList<String> places = new ArrayList<String>();
    ArrayList<String> o_times = new ArrayList<String>();
    ArrayList<String> c_times = new ArrayList<String>();
    String e_lat,e_long;
    Float final_lat,final_long;
    String f_days;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=prefs.getString("username", "");

//        ed1=(EditText)findViewById(R.id.latitude);
//        ed2=(EditText)findViewById(R.id.longitude);
        ed3=(EditText)findViewById(R.id.n_days);
        recv=(RecyclerView)findViewById(R.id.recyclerView3);


        b1=(Button)findViewById(R.id.searchbtn);
        b2=(Button)findViewById(R.id.generate);
        b3=(Button)findViewById(R.id.map);

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Log.e("Length of Place List : ", String.valueOf(places.size()));
                String len= String.valueOf(places.size());

                if (len.equals("4"))
                {
                    if (f_days.equals("2"))
                    {
                        Log.e("HAI : ","enter 2 ");
                        Intent i3=new Intent(SearchPlace.this,GeneratePlantwo.class);
                        i3.putExtra("pname", places);
                        i3.putExtra("o_time", o_times);
                        i3.putExtra("c_time", c_times);
                        startActivity((i3));
                    }
                    else if (f_days.equals("3"))
                    {
                        Log.e("HAI : ","enter 3");
                        Intent i4=new Intent(SearchPlace.this,GeneratePlanthree.class);
                        i4.putExtra("pname", places);
                        i4.putExtra("o_time", o_times);
                        i4.putExtra("c_time", c_times);
                        startActivity((i4));
                    }
                    else if (f_days.equals("4"))
                    {
                        Log.e("HAI : ","enter 3");
                        Intent i5=new Intent(SearchPlace.this,GeneratePlanfour.class);
                        i5.putExtra("pname", places);
                        i5.putExtra("o_time", o_times);
                        i5.putExtra("c_time", c_times);
                        startActivity((i5));
                    }

                }
                else if(len.equals("2"))
                {
                    if (f_days.equals("2"))
                    {
                        Log.e("HAI : ","enter 2 ");
                        Intent i7=new Intent(SearchPlace.this,GeneratePlantwolow.class);
                        i7.putExtra("pname", places);
                        i7.putExtra("o_time", o_times);
                        i7.putExtra("c_time", c_times);
                        startActivity((i7));
                    }
                    else
                    {
                        Log.e("Hello : ","PRINT HELLO");
                        Intent i2=new Intent(SearchPlace.this,GeneratePlan.class);
                        i2.putExtra("pname", places);
                        i2.putExtra("o_time", o_times);
                        i2.putExtra("c_time", c_times);
                        startActivity((i2));
                    }

                }

            }
        });

        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1=new Intent(SearchPlace.this,MapsActivity.class);
                i1.putExtra("latitude_list", latitudes);
                i1.putExtra("longitude_list", longitudes);
                i1.putExtra("e_lat", e_lat);
                i1.putExtra("e_long", e_long);
                startActivity((i1));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude=String.valueOf(final_lat);
                String longitude=String.valueOf(final_long);
                String u_days=ed3.getText().toString();
                f_days=u_days;
                Log.e("u_days :::::::::::> ",u_days);
                int myNum = Integer.parseInt(u_days);



                if (latitude.equals("")||longitude.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter all details!!",Toast.LENGTH_LONG).show();
                }
                else if(myNum>5)
                {
                    Toast.makeText(getApplicationContext(),"Please enter days between 0 and 5",Toast.LENGTH_LONG).show();
                }

                else if(pacetype.equals("Low"))
                {
                    Log.e("Entered lOW","eNTERED lOW");
                    if(u_days.equals("3") || u_days.equals("4"))
                    {
                        Log.e("Entered lOW IF","eNTERED lOW IF");
                        Toast.makeText(getApplicationContext(),"Number of Days Not Compatible with Pace!, Reduce Number of Days",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Log.e("hai___4","hai___4");
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Search_place/", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject object = new JSONObject(response.trim());
                                    JSONArray jsonArray = object.getJSONArray("data");

                                    JSONObject o = new JSONObject(response);
                                    e_lat = o.getString("my_lat");
                                    e_long = o.getString("my_long");


                                    for(int i=0;i<jsonArray.length();i++) {

                                        JSONObject json_obj = jsonArray.getJSONObject(i);   //get the 3rd item
                                        String latitude = json_obj.getString("latitude");
                                        Log.e("my latitude : >", latitude);
                                        String longitude = json_obj.getString("longitude");
                                        Log.e("my longitude : >", longitude);
                                        String o_time = json_obj.getString("o_time");
                                        String c_time = json_obj.getString("c_time");
                                        String pname = json_obj.getString("pname");
                                        latitudes.add(latitude);
                                        longitudes.add(longitude);
                                        places.add(pname);
                                        o_times.add(o_time);
                                        c_times.add(c_time);


                                    }
                                    Log.e("ALL Latitudes : >", String.valueOf(latitudes));
                                    Log.e("ALL longitudes : >", String.valueOf(longitudes));

                                    Show_places_recy ordrec = new Show_places_recy(SearchPlace.this, jsonArray, SearchPlace.this);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchPlace.this);
                                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recv.getContext(),
                                            linearLayoutManager.getOrientation());
                                    recv.addItemDecoration(dividerItemDecoration);
                                    recv.setLayoutManager(linearLayoutManager);
                                    recv.setAdapter(ordrec);

                                    b2.setVisibility(View.VISIBLE);
                                    b3.setVisibility(View.VISIBLE);


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
                                m.put("pacetype",pacetype);
                                m.put("ptype",utype);
                                m.put("latitude",latitude);
                                m.put("longitude",longitude);
                                return m;
                            }
                        };
                        requestQueue.add(requ);
                    }
                }
                else if(pacetype.equals("High"))
                {
                    Log.e("hai___1","hai___1");
                    if(u_days.equals("1"))
                    {
                        Log.e("hai___2","hai___2");
                        Toast.makeText(getApplicationContext(),"Number of Days Not Compatible with Pace!, Increase Number of Days",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Log.e("hai___10","hai___10");
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Search_place/", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject object = new JSONObject(response.trim());
                                    JSONArray jsonArray = object.getJSONArray("data");

                                    JSONObject o = new JSONObject(response);
                                    e_lat = o.getString("my_lat");
                                    e_long = o.getString("my_long");


                                    for(int i=0;i<jsonArray.length();i++) {

                                        JSONObject json_obj = jsonArray.getJSONObject(i);   //get the 3rd item
                                        String latitude = json_obj.getString("latitude");
                                        Log.e("my latitude : >", latitude);
                                        String longitude = json_obj.getString("longitude");
                                        Log.e("my longitude : >", longitude);
                                        String o_time = json_obj.getString("o_time");
                                        String c_time = json_obj.getString("c_time");
                                        String pname = json_obj.getString("pname");
                                        latitudes.add(latitude);
                                        longitudes.add(longitude);
                                        places.add(pname);
                                        o_times.add(o_time);
                                        c_times.add(c_time);


                                    }
                                    Log.e("ALL Latitudes : >", String.valueOf(latitudes));
                                    Log.e("ALL longitudes : >", String.valueOf(longitudes));

                                    Show_places_recy ordrec = new Show_places_recy(SearchPlace.this, jsonArray, SearchPlace.this);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchPlace.this);
                                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recv.getContext(),
                                            linearLayoutManager.getOrientation());
                                    recv.addItemDecoration(dividerItemDecoration);
                                    recv.setLayoutManager(linearLayoutManager);
                                    recv.setAdapter(ordrec);

                                    b2.setVisibility(View.VISIBLE);
                                    b3.setVisibility(View.VISIBLE);


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
                                m.put("pacetype",pacetype);
                                m.put("ptype",utype);
                                m.put("latitude",latitude);
                                m.put("longitude",longitude);
                                return m;
                            }
                        };
                        requestQueue.add(requ);
                    }
                }
                else
                {
                    //No need
                    Log.e("hai___3","hai___3");
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Search_place/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject object = new JSONObject(response.trim());
                                JSONArray jsonArray = object.getJSONArray("data");

                                JSONObject o = new JSONObject(response);
                                e_lat = o.getString("my_lat");
                                e_long = o.getString("my_long");


                                for(int i=0;i<jsonArray.length();i++) {

                                    JSONObject json_obj = jsonArray.getJSONObject(i);   //get the 3rd item
                                    String latitude = json_obj.getString("latitude");
                                    Log.e("my latitude : >", latitude);
                                    String longitude = json_obj.getString("longitude");
                                    Log.e("my longitude : >", longitude);
                                    String o_time = json_obj.getString("o_time");
                                    String c_time = json_obj.getString("c_time");
                                    String pname = json_obj.getString("pname");
                                    latitudes.add(latitude);
                                    longitudes.add(longitude);
                                    places.add(pname);
                                    o_times.add(o_time);
                                    c_times.add(c_time);


                                }
                                Log.e("ALL Latitudes : >", String.valueOf(latitudes));
                                Log.e("ALL longitudes : >", String.valueOf(longitudes));

                                Show_places_recy ordrec = new Show_places_recy(SearchPlace.this, jsonArray, SearchPlace.this);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchPlace.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recv.getContext(),
                                        linearLayoutManager.getOrientation());
                                recv.addItemDecoration(dividerItemDecoration);
                                recv.setLayoutManager(linearLayoutManager);
                                recv.setAdapter(ordrec);

                                b2.setVisibility(View.VISIBLE);
                                b3.setVisibility(View.VISIBLE);


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
                            m.put("pacetype",pacetype);
                            m.put("ptype",utype);
                            m.put("latitude",latitude);
                            m.put("longitude",longitude);
                            return m;
                        }
                    };
                    requestQueue.add(requ);

                }
            }
        });

        spin1 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                utype=users[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, paces);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter1);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l)
            {
                pacetype=paces[j];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spin3 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, district);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(adapter2);
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int y, long l)
            {
                ini_district=district[y];

                latval=latlist[y];
                longval=longlist[y];

                final_lat=latval;
                final_long=longval;

                //Toast.makeText(getApplicationContext(), "Selected latitude: "+latlist[y] ,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Selected longitude: "+longlist[y] ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


}