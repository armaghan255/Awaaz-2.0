package com.example.awaaz;

import android.content.Context;
import android.content.Intent;

public class LiveVideo {
    Context context;
    private int id;
    private boolean endLiveVideo;
    private Frame frame;


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

    private void openCamera() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public LiveVideo(int id, boolean endLiveVideo, Context context) {
        this.id = id;
        this.endLiveVideo = endLiveVideo;
        this.context = context;
    }
}
