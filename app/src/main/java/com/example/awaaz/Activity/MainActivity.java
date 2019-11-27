package com.example.awaaz.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.example.awaaz.Class.LiveVideo;
import com.example.awaaz.Class.MFabButtons;
import com.example.awaaz.Class.MySurface;
import com.example.awaaz.Class.SLR;
import com.example.awaaz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramotion.fluidslider.FluidSlider;

import org.opencv.android.OpenCVLoader;

import java.math.BigDecimal;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import spencerstudios.com.fab_toast.FabToast;

public class MainActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    MFabButtons mFabButtons;
    FloatingActionButton fab_button_1;
    public FluidSlider slider =null;
    FrameLayout frameLayout;
    ImageView imageView;
    MySurface mySurface;
    SLR slr;
    boolean check=true;


    public void back_click(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    enum mode{
        back,
        front
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySurface = new MySurface(this);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.addView(mySurface);
        imageView = findViewById(R.id.imgView);
        cameraKitView = findViewById(R.id.camera);
        slider = findViewById(R.id.fluidSlider);

        slr=getIntent().getParcelableExtra("SLR");
        slr.setLiveVideo(new LiveVideo(1,true,this,cameraKitView,2000,imageView));
        setType();
        if(OpenCVLoader.initDebug())
        {
            Log.e("Opencv", "Loaded");
        }
        else {
            FabToast.makeText(MainActivity.this, "Unable to attach OpenCV", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
            try {
                Thread.sleep(2000);
                finish();
                System.exit(0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setfluidSlider();

    }



    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    void setfluidSlider() {
        final float max = 5;
        final float min = 2;
        final float total = max - min;
        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return Unit.INSTANCE;
            }
        });

        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return Unit.INSTANCE;
            }
        });
        slider.setPositionListener(pos -> {
            String value = String.valueOf( round((float) (min + total * pos),3) );
            double temp=Double.parseDouble(value);
            temp=temp*1000;
            slr.setInterval((int)temp);
            slider.setBubbleText(value);
            return Unit.INSTANCE;
        });


        slider.setPosition(0.3f);
        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));
        slider.setVisibility(View.INVISIBLE);

    }

    private void setType() {
        fab_button_1 = findViewById(R.id.fab_button_1);
        FloatingActionButton fab_button_2 = findViewById(R.id.fab_button_2);
        FloatingActionButton fab_button_3 = findViewById(R.id.fab_button_3);
        FloatingActionButton fab_button_4 = findViewById(R.id.fab_button_4);
        fab_button_1.hide();
        mFabButtons = new MFabButtons(MainActivity.this, fab_button_1, fab_button_2, fab_button_3, fab_button_4,slider);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
        cameraKitView.setGestureListener(new CameraKitView.GestureListener() {
            @Override
            public void onTap(CameraKitView ckV, float v, float v1) {

            }

            @Override
            public void onLongTap(CameraKitView cameraKitView, float v, float v1) {
                if (check){
                    fab_button_1.show();
                    check=false;
                }
                else{
                    fab_button_1.hide();
                    check=true;
                }
            }

            @Override
            public void onDoubleTap(CameraKitView cameraKitView, float v, float v1) {
                if (cameraKitView.getFacing() == CameraKit.FACING_FRONT) {
                    cameraKitView.setFacing(CameraKit.FACING_BACK);
                    slr.getLiveVideo().setFacing(LiveVideo.Mode.back);
                }
                else {
                    cameraKitView.setFacing(CameraKit.FACING_FRONT);
                    slr.getLiveVideo().setFacing(LiveVideo.Mode.front);
                }
            }

            @Override
            public void onPinch(CameraKitView cameraKitView, float v, float v1, float v2) {

            }
        });
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
