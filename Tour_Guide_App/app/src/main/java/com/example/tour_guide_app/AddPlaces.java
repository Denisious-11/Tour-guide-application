package com.example.tour_guide_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPlaces extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String[] users = { "Architectural Buildings","Beaches","Religious Sites","Wildlife Sanctuaries",};
    String utype="Architectural Buildings";
    EditText ed1,ed3,ed4,ed5,ed6,ed7,ed8;
    Button b1;
    TimePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        ed1=(EditText)findViewById(R.id.pname);
        ed3=(EditText)findViewById(R.id.o_time);
        ed4=(EditText)findViewById(R.id.c_time);
        ed5=(EditText)findViewById(R.id.amount);
        ed6=(EditText)findViewById(R.id.latitude);
        ed7=(EditText)findViewById(R.id.longitude);
        ed8=(EditText)findViewById(R.id.link);

        ed3.setInputType(InputType.TYPE_NULL);
        ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(AddPlaces.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                ed3.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        ed4.setInputType(InputType.TYPE_NULL);
        ed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(AddPlaces.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                ed4.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        b1=(Button)findViewById(R.id.addbtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pname=ed1.getText().toString();
                String o_time=ed3.getText().toString();
                String c_time=ed4.getText().toString();
                String amount=ed5.getText().toString();
                String latitude=ed6.getText().toString();
                String longitude=ed7.getText().toString();
                String link=ed8.getText().toString();

                if (pname.equals("")||o_time.equals("")||c_time.equals("")||amount.equals("")||latitude.equals("")||longitude.equals("")||link.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter all details!!",Toast.LENGTH_LONG).show();
                }
                else
                {

                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Add_place/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is: ", response.toString());
                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("yes")){
                                    Toast.makeText(AddPlaces.this, "Tourist Place added Successfully!", Toast.LENGTH_SHORT).show();
//                                    ed1.setText("");
//                                    ed2.setText("");
//                                    ed3.setText("");
//                                    ed4.setText("");
//                                    ed5.setText("");
//                                    ed6.setText("");
//                                    ed7.setText("");
//                                    ed8.setText("");
                                    Intent i2=new Intent(AddPlaces.this,ViewPlaces.class);
                                    startActivity(i2);
                                }
                                else if(dat.equals("Already Exists"))
                                {
                                    Toast.makeText(AddPlaces.this, "Tourist Place already added!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(AddPlaces.this, "Error Happened", Toast.LENGTH_SHORT).show();
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

                            m.put("pname",pname);
                            m.put("ptype",utype);
                            m.put("o_time",o_time);
                            m.put("c_time",c_time);
                            m.put("amount",amount);
                            m.put("latitude",latitude);
                            m.put("longitude",longitude);
                            m.put("link",link);
                            return m;
                        }
                    };
                    requestQueue.add(requ);

                }
            }
        });

        Spinner spin = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        utype=users[position];

        Toast.makeText(getApplicationContext(), "Selected type: "+users[position] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}