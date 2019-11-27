package com.example.awaaz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.example.awaaz.R;

public class SplashScreen extends AppCompatActivity {
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        }, 3000);
    }
    void next() {
        SharedPreferences pref=getSharedPreferences("users",MODE_PRIVATE);
        String ee=pref.getString("login","notdone");
        if(ee.equals("done"))
        {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        }
        else {
            setContentView(R.layout.enter_name_layout);
        }
    }

    public void name_click_next(View view) {
        SharedPreferences.Editor preferences = getSharedPreferences("users",MODE_PRIVATE).edit();
        preferences.putString("login","done");
        preferences.commit();
        Intent intent = new Intent(this, OnBoardingActivity.class);
        startActivity(intent);
    }
}
