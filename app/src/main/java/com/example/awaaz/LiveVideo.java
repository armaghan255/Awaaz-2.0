package com.example.awaaz;

public class LiveVideo {
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

    public LiveVideo(int id, boolean endLiveVideo) {
        this.id = id;
        this.endLiveVideo = endLiveVideo;
    }
}
