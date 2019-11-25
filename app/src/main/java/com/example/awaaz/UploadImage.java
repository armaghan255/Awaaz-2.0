package com.example.awaaz;

import java.util.List;

public class UploadImage {
    private List<Frame> frameList;

    public List<Frame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }

    public void addFrame(Frame frame) {
        frameList.add(frame);
    }

    public UploadImage() {
    }
}
