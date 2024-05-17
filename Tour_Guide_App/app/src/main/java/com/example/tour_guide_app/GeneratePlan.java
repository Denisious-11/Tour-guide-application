package com.example.tour_guide_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GeneratePlan extends AppCompatActivity
{
    TextView tv1,tv2,tv3,tv4;
    Button b1,b2;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_plan);

        tv1=(TextView)findViewById(R.id.s_date);
        tv2=(TextView)findViewById(R.id.e_date);
        tv3=(TextView)findViewById(R.id.tv1);
        tv4=(TextView)findViewById(R.id.tv2);

//        calendar = Calendar.getInstance();
//        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        date = simpleDateFormat.format(calendar.getTime());
//        dateTimeDisplay.setText(date);

//        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        Log.e("Current DATE ; >>>>>",date);
//        LocalDateTime todayDate = LocalDateTime.now();
//        todayDate.plusDays(1);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");// HH:mm:ss");
        String start_date = df.format(c.getTime());
//
//        c.add(Calendar.DATE, 1);  // number of days to add
//        String end_date = df.format(c.getTime());

        Log.e("Current DATE ; >>>>>", start_date);
//        Log.e("End DATE ; >>>>>", end_date);s

        b1=(Button)findViewById(R.id.back);
        b2=(Button)findViewById(R.id.rating);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i2=new Intent(GeneratePlan.this,SearchPlace.class);
                startActivity((i2));
            }
        });



        ArrayList<String> name_list = (ArrayList<String>) getIntent().getSerializableExtra("pname");
        ArrayList<String> o_times = (ArrayList<String>) getIntent().getSerializableExtra("o_time");
        ArrayList<String> c_times = (ArrayList<String>) getIntent().getSerializableExtra("c_time");
        Log.e("GET Names : ", String.valueOf(name_list));

        String val_1 = name_list.get(0);
        String val_2 = name_list.get(1);
        Log.e("first place ; >>>>>", val_1);
        Log.e("second place ; >>>>>", val_2);

        tv1.setText(start_date);
        tv2.setText(start_date);
        tv3.setText("Visiting "+val_1);
        tv4.setText("Visiting "+val_2);

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i2=new Intent(GeneratePlan.this,GiveRating.class);
                i2.putExtra("pname", name_list);
                startActivity((i2));
            }
        });

    }
}