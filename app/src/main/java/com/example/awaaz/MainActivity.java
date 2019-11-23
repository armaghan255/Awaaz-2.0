package com.example.awaaz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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
    int sliderValue=2;
    ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraKitView = findViewById(R.id.camera);
        imageView=findViewById(R.id.imgView);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Tag","In Run");
                cameraKitView.captureFrame(new CameraKitView.FrameCallback() {
                    @Override
                    public void onFrame(CameraKitView cameraKitView, byte[] bytes) {
                        Toast.makeText(MainActivity.this, "Frame", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        Mat mat=new Mat();
                        Utils.bitmapToMat(bitmap,mat);
                        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2BGR);

                    }
                });
                }

        },2000);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    @SuppressWarnings("Convert2Lambda")
    void fluidSlider()
    {
        final float max = 5;
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

        slider.setPositionListener(pos -> {
            String value = String.valueOf(round(min + total * pos, 2));
            slider.setBubbleText(value);
            double temp=Double.parseDouble(value);
            temp=temp*1000;
            sliderValue= (int)temp;
            return Unit.INSTANCE;
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
            public void onTap(CameraKitView cameraKitView, float v, float v1) {
                Log.e("Tag","ON Tap");
                cameraKitView.captureImage(new  CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        // capturedImage contains the image from the CameraKitView.
                        Toast.makeText(MainActivity.this, "Captured", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap= BitmapFactory.decodeByteArray(capturedImage,0,capturedImage.length);
                        Mat src = new Mat();
                        Utils.bitmapToMat(bitmap, src);
                        Mat mat=new Mat();
                        Imgproc.cvtColor(src, mat, Imgproc.COLOR_RGB2GRAY);
                        Imgproc.threshold(mat,mat,0,255,Imgproc.THRESH_BINARY+Imgproc.THRESH_OTSU);
                        List<MatOfPoint> contours = new ArrayList<>();
                        Mat hierarchy = new Mat();
                        Imgproc.findContours(mat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
                        Imgproc.drawContours(src, contours, -1, new Scalar(0, 0, 255), -1);
                        //Imgproc.findContours(mat,contours,mat,Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_NONE);
                        //Imgproc.drawContours(mat, contours, -1, new Scalar(0, 255, 0, 255), 3);
                        Utils.matToBitmap(mat,bitmap);
                        imageView.setImageBitmap(bitmap);

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
