package com.example.awaaz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramotion.fluidslider.FluidSlider;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import spencerstudios.com.fab_toast.FabToast;

public class MainActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    MFabButtons mFabButtons;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab_button_1;
    public FluidSlider slider =null;
    FrameLayout frameLayout;
    ImageView imageView;
    MySurface mySurface;
    Bitmap background;
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

        setType();
        if(OpenCVLoader.initDebug())
        {
            Log.e("Opencv", "Loaded");
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
            public void onTap(CameraKitView ckV, float v, float v1) {
                Log.e("Tag","On Tap");

                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Mat src = new Mat();
                        Utils.bitmapToMat(bitmap, src);
                        Mat gray = new Mat();
                        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
                        Mat imdt = new Mat();
                        //Imgproc.Canny(gray,imdt,80,100);
                        List contours = new ArrayList<MatOfPoint>();
                        Mat dest = new Mat();
                        //Imgproc.findContours(imdt,contours,dest,Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
                        //Imgproc.drawContours(gray,contours,-1,new Scalar(0, 255, 255));//, 2, 8, hierarchy, 0, new Point()););
                        Utils.matToBitmap(gray, bitmap);
                        imageView.setImageBitmap(bitmap);
                    }

                });
            }

            @Override
            public void onLongTap(CameraKitView cameraKitView, float v, float v1) {
                fab_button_1.show();
            }

            @Override
            public void onDoubleTap(CameraKitView cameraKitView, float v, float v1) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                        background = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Toast.makeText(MainActivity.this, "background captured", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPinch(CameraKitView cameraKitView, float v, float v1, float v2) {
                if (cameraKitView.getFacing() == CameraKit.FACING_FRONT) {
                    cameraKitView.setFacing(CameraKit.FACING_BACK);
                } else
                    cameraKitView.setFacing(CameraKit.FACING_FRONT);
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
