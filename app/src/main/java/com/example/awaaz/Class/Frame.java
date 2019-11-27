package com.example.awaaz.Class;

import org.opencv.core.Mat;

public class Frame {
    private int id;
    private Mat image;
    private SignAnalyzer signAnalyzer;

    public SignAnalyzer getSignAnalyzer() {
        return signAnalyzer;
    }

    public void setSignAnalyzer(SignAnalyzer signAnalyzer) {
        this.signAnalyzer = signAnalyzer;
    }

    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
    }

    public Frame(int id, Mat image) {
        this.id = id;
        this.image = image;
        signAnalyzer=new SignAnalyzer();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
