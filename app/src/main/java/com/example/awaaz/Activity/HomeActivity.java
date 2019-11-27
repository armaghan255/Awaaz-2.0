package com.example.awaaz.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.awaaz.Class.SLR;
import com.example.awaaz.R;
import com.google.android.material.card.MaterialCardView;


public class HomeActivity extends AppCompatActivity {


    ImageView imageView;
    LinearLayout linearLayout;
    MaterialCardView upload_cardview,live_cardview;
    SLR slr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        live_cardview = findViewById(R.id.livecardview);
        upload_cardview = findViewById(R.id.uploadcardview);
        slr=new SLR(1,this);
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


    public void Exit_click(View view) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
