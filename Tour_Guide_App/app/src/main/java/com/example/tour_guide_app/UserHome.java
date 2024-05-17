package com.example.tour_guide_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class UserHome extends AppCompatActivity
{
    int b[] = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b5, R.drawable.b6, R.drawable.b7};
    NavigationView navigationView;
    public ImageView i; int z = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        i = (ImageView) findViewById(R.id.img1);
        i.setImageResource(b[0]);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                    for (z = 0; z < b.length + 2; z++) {
                        if (z < b.length) {
                            sleep(3000);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    i.setImageResource(b[z]);
                                }
                            });
                        } else {
                            z = 0;

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally");
                }
            }
        };
        timer.start();

        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                if(id==R.id.home)
                {
                    Intent i4=new Intent(UserHome.this,UserHome.class);
                    startActivity(i4);

                }

                else if(id==R.id.search_places)
                {
                    Intent i2=new Intent(UserHome.this,SearchPlace.class);
                    startActivity(i2);
                }
                else if(id==R.id.show_request)
                {
                    Intent i5=new Intent(UserHome.this,ShowRequest.class);
                    startActivity(i5);
                }
                else if(id==R.id.chat)
                {
                    Intent i3=new Intent(UserHome.this,UsersList.class);
                    startActivity(i3);
                }
                else if(id==R.id.logout)
                {
                    Intent i1=new Intent(UserHome.this,LoginPage.class);
                    startActivity(i1);
                }
                return true;
            }
        });

    }
}