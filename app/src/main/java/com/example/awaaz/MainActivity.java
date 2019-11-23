package com.example.awaaz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
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
    com.google.android.material.floatingactionbutton.FloatingActionButton fab_button_1;
    public FluidSlider slider =null;

    @SuppressWarnings("Convert2Lambda")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraKitView = findViewById(R.id.camera);
        slider = findViewById(R.id.fluidSlider);
        setType();
        if(OpenCVLoader.initDebug())
        {
            FabToast.makeText(MainActivity.this, "OpenCV Attached Successfully", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
        }
        else
            FabToast.makeText(MainActivity.this, "OpenCV Didn't Attached Successfully", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();


        fluidSlider();
        slider.setVisibility(View.INVISIBLE);

    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    void fluidSlider()
    {
        final float max = 7;
        final float min = 1;
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

//
//        slider.setPositionListener(pos -> {
//            String value = String.valueOf( round((float) (min + total * pos),2) );
//            slider.setBubbleText(value);
//            return Unit.INSTANCE;
//        });

        slider.setPosition(0.3f);
        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));
    }

    private void setType() {
        fab_button_1 = findViewById(R.id.fab_button_1);
        com.google.android.material.floatingactionbutton.FloatingActionButton fab_button_2 = findViewById(R.id.fab_button_2);
        com.google.android.material.floatingactionbutton.FloatingActionButton fab_button_3 = findViewById(R.id.fab_button_3);
        FloatingActionButton fab_button_4 = findViewById(R.id.fab_button_4);
        fab_button_1.hide();
        mFabButtons = new MFabButtons(MainActivity.this, fab_button_1, fab_button_2, fab_button_3, fab_button_4,slider);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
        cameraKitView.setGestureListener(new CameraKitView.GestureListener() {
            @Override
            public void onTap(CameraKitView cameraKitView, float v, float v1) {
                Log.e("Tag","On Tap");

                cameraKitView.captureFrame(new CameraKitView.FrameCallback() {
                    @Override
                    public void onFrame(CameraKitView cameraKitView, byte[] bytes) {
                        Toast.makeText(MainActivity.this, "sed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLongTap(CameraKitView cameraKitView, float v, float v1) {
//     if(cameraKitView.getFlash()==CameraKit.FLASH_OFF)
//     {
//         cameraKitView.setFlash(CameraKit.FLASH_TORCH);
//     }
//     else
//     {
//         cameraKitView.setFlash(CameraKit.FLASH_OFF);
//     }
                fab_button_1.show();
            }

            @Override
            public void onDoubleTap(CameraKitView cameraKitView, float v, float v1) {
                if(cameraKitView.getFacing()==CameraKit.FACING_FRONT)
                {
                    cameraKitView.setFacing(CameraKit.FACING_BACK);
                }
                else
                    cameraKitView.setFacing(CameraKit.FACING_FRONT);
            }

            @Override
            public void onPinch(CameraKitView cameraKitView, float v, float v1, float v2) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
