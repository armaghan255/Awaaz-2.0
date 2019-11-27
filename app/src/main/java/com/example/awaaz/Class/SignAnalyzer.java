package com.example.awaaz.Class;

public class SignAnalyzer {
    private Preprocessor preprocessor;

    public Preprocessor getPreprocessor() {
        return preprocessor;
    }

    public SignAnalyzer() {
    }

    public SignAnalyzer(Preprocessor preprocessor) {
        this.preprocessor = preprocessor;
    }

    public void setPreprocessor(Frame frame) {
        preprocessor=new Preprocessor(frame);
    }
}
