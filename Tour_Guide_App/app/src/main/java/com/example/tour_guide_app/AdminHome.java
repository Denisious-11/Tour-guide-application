package com.example.tour_guide_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class AdminHome extends AppCompatActivity
{
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        navigationView = (NavigationView) findViewById(R.id.nav1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                if(id==R.id.home)
                {
                    Intent i4=new Intent(AdminHome.this,AdminHome.class);
                    startActivity(i4);

                }
//                else if(id==R.id.registered_users)
//                {
//                    Intent i3=new Intent(AdminHome.this,GetUsers.class);
//                    startActivity(i3);
//                }
                else if(id==R.id.add_t_place)
                {
                    Intent i3=new Intent(AdminHome.this,AddPlaces.class);
                    startActivity(i3);
                }
                else if(id==R.id.upload_excel)
                {
                    Intent i5=new Intent(AdminHome.this,AddExcel.class);
                    startActivity(i5);
                }
//                else if(id==R.id.view_t_place)
//                {
//                    Intent i2=new Intent(AdminHome.this,ViewPlaces.class);
//                    startActivity(i2);
//                }

                else if(id==R.id.logout)
                {
                    Intent i1=new Intent(AdminHome.this,LoginPage.class);
                    startActivity(i1);
                }
                return true;
            }
        });
    }
}