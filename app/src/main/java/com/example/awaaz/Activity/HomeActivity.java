package com.example.awaaz.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.awaaz.Class.Interpretor;
import com.example.awaaz.Class.SLR;
import com.example.awaaz.R;
import com.google.android.material.card.MaterialCardView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import spencerstudios.com.fab_toast.FabToast;


public class HomeActivity extends AppCompatActivity {

    MaterialCardView upload_cardview,live_cardview;
    SLR slr;
    Interpretor interpretor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        live_cardview = findViewById(R.id.livecardview);
        upload_cardview = findViewById(R.id.uploadcardview);
        slr=new SLR(1,this);
        SharedPreferences pref=getSharedPreferences("users",MODE_PRIVATE);
        String name=pref.getString("name","");
        interpretor=new Interpretor(1);
        interpretor.setName(name);
        FabToast.makeText(HomeActivity.this, "Welcome Back "+interpretor.getName() + " ☺ ", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_TOP).show();

        live_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                live_cardview.setBackgroundColor(getResources().getColor(R.color.red));
                slr.startLiveVideo();
            }
        });
        upload_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_cardview.setBackgroundColor(getResources().getColor(R.color.green));
                slr.uploadImage();
            }
        });

    }
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public void Exit_click(View view) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                       finishActivity(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        live_cardview.setBackgroundColor(Color.parseColor("#F7F9F9"));
        upload_cardview.setBackgroundColor(Color.parseColor("#F7F9F9"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        finishActivity(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
