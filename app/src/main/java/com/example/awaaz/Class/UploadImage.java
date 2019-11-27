package com.example.awaaz.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;

public class UploadImage {
    private List<Frame> frameList;
    private Context context;
    TedImagePicker tedImagePicker;

    public List<Frame> getFrameList() {
        return frameList;
    }
    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }

    public void addFrame(Frame frame) {
        frameList.add(frame);
    }

    public UploadImage(Context context) {
        frameList=new ArrayList<Frame>();
        this.context = context;
    }
    protected void openGallery()
    {
        TedImagePicker.with(context)
                .startMultiImage(new OnMultiSelectedListener() {
                    @Override
                    public void onSelected(@NotNull List<? extends Uri> uriList) {
                        try {
                            for (int i=0;i<uriList.size();i++) {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriList.get(i));
                                Mat src=new Mat();
                                Utils.bitmapToMat(bitmap,src);
                                Frame frame=new Frame(i,src);
                                addFrame(frame);
                                Utils.matToBitmap(getFrameList().get(0).getImage(),bitmap);
                                Toast.makeText(context, ""+frameList.size(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


}
