package com.example.awaaz.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKitView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;


public class LiveVideo {
    private int id;
    private boolean endLiveVideo;
    private Frame frame;
    private Context context;
    private CameraKitView cameraKitView;
    private int interval;
    private ImageView imageView;
    private Mode facing;

    public enum Mode{
        back,
        front
    }

    public Mode getFacing() {
        return facing;
    }

    public void setFacing(Mode facing) {
        this.facing = facing;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEndLiveVideo() {
        return endLiveVideo;
    }

    public void setEndLiveVideo(boolean endLiveVideo) {
        this.endLiveVideo = endLiveVideo;
    }

    public CameraKitView getCameraKitView() {
        return cameraKitView;
    }

    public void setCameraKitView(CameraKitView cameraKitView) {
        this.cameraKitView = cameraKitView;
    }

    public LiveVideo(int id, boolean endLiveVideo, Context context,CameraKitView cameraKitView,int interval,ImageView imageView) {
        this.id = id;
        this.endLiveVideo = endLiveVideo;
        this.context=context;
        this.cameraKitView=cameraKitView;
        this.interval=interval;
        this.imageView=imageView;
        facing=Mode.back;
        openCamera();
        captureFrames();
    }
    public void openCamera(){
        try{
            cameraKitView.onStart();
        }
        catch (Exception e){
            Toast.makeText(context, "Unable to Open Camera...", Toast.LENGTH_SHORT).show();
        }
    }
    public void captureFrames(){
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        Mat src=new Mat();
                        Utils.bitmapToMat(bitmap,src);
                        frame=new Frame(1,src);
                        frame.getSignAnalyzer().setPreprocessor(frame);
                        if (facing==Mode.front) {
                            src = frame.getSignAnalyzer().getPreprocessor().Mirror(src);
                        }
                        src=frame.getSignAnalyzer().getPreprocessor().cropMat(src);
                        Mat gray=frame.getSignAnalyzer().getPreprocessor().convert2gray(src);
                        Bitmap bitmap1=Bitmap.createBitmap(gray.width(), gray.height(),Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(src,bitmap1);
                        imageView.setImageBitmap(bitmap1);
                    }
                });
                handler.postDelayed(this, getInterval());
            }
        };
        handler.postDelayed(r, getInterval());
    }
}
