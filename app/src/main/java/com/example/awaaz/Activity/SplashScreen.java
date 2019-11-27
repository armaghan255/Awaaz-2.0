package com.example.awaaz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);





        final Handler h = new Handler();
        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                next();
            }
        }, 3000);
    }
    void next() {
        setContentView(R.layout.enter_name_layout);
    }

    public void name_click_next(View view) {
        Intent intent = new Intent(this, OnBoardingActivity.class);
        startActivity(intent);
    }
}
