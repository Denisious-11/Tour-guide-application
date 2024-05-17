package com.example.tour_guide_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddExcel extends AppCompatActivity
{
    Button b1,b2;
    TextView tv;
    String pathstr="";
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excel);

        b1=(Button)findViewById(R.id.choose);
        b2=(Button)findViewById(R.id.upload);
        tv=(TextView)findViewById(R.id.tv1);
        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname=prefs.getString("usernm", "");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathstr=tv.getText().toString();
                if (pathstr.equals("")){
                    Toast.makeText(AddExcel.this,"Please choose an Excel file",Toast.LENGTH_LONG).show();
                }
                else {
                    String encodedstr=getBase64FromPath(pathstr);

                    Log.e("Encoded string==>",encodedstr);


                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/Upload_Excel/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is: ", response.toString());
                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("yes")){
                                    Toast.makeText(AddExcel.this, "File uploaded successfully!!!", Toast.LENGTH_SHORT).show();
                                    Intent i1=new Intent(AddExcel.this,AdminHome.class);
                                    startActivity(i1);
//                                    tv.setText("");
                                }
                                else {
                                    Toast.makeText(AddExcel.this, "Error : PlaceName Already Exist", Toast.LENGTH_SHORT).show();
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
                            m.put("uname",Uname);
                            m.put("fpath",pathstr);
                            m.put("encodedstr",encodedstr);



                            return m;
                        }
                    };
                    requestQueue.add(requ);



                }

            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                if(id==R.id.home)
                {
                    Intent i4=new Intent(AddExcel.this,AdminHome.class);
                    startActivity(i4);

                }
//                else if(id==R.id.registered_users)
//                {
//                    Intent i3=new Intent(AddExcel.this,GetUsers.class);
//                    startActivity(i3);
//                }
                else if(id==R.id.add_t_place)
                {
                    Intent i3=new Intent(AddExcel.this,AddPlaces.class);
                    startActivity(i3);
                }
                else if(id==R.id.upload_excel)
                {
                    Intent i5=new Intent(AddExcel.this,AddExcel.class);
                    startActivity(i5);
                }
//                else if(id==R.id.view_t_place)
//                {
//                    Intent i2=new Intent(AddExcel.this,ViewPlaces.class);
//                    startActivity(i2);
//                }

                else if(id==R.id.logout)
                {
                    Intent i1=new Intent(AddExcel.this,LoginPage.class);
                    startActivity(i1);
                }
                return true;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 7:

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    String PathHolder = data.getData().getPath();
                    String path = uri.getPath() ;// "/mnt/sdcard/FileName.mp3"
                    Log.e("realpath",path);
                    String[] arrray = PathHolder.split(":");

//                    Toast.makeText(AddExcel.this, PathHolder, Toast.LENGTH_LONG).show();

                    File sdcard = Environment.getExternalStorageDirectory();

                    String[] arrray2 = arrray[1].split("/");
                    Log.e("arrray[1]",arrray[1]);
                    File file = new File(sdcard,arrray2[0]);
                    Log.e("file",file.getPath());
                    String apkfilepath=file.getPath()+"/";
                    String filepth="/storage/emulated/0/"+arrray[1];
                    Log.e("apkfilepath",apkfilepath);
                    Log.e("filepth==>",filepth);

                    tv.setText(filepth);





//
                }
                break;

        }

    }

    public static String getBase64FromPath(String path)
    {
        Log.e("path",path);
        String base64 = "";
        try {/*from   w w w .  ja  va  2s  .  c om*/
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            Log.e("path",e+"");
            e.printStackTrace();
        }
        return base64;
    }
}