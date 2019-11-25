package com.example.awaaz;

import android.content.Context;

public class SLR {
    private LiveVideo liveVideo ;
    private UploadImage uploadImage;
    private Context context;

    public LiveVideo getLiveVideo() {
        return liveVideo;
    }

    public void setLiveVideo(LiveVideo liveVideo) {
        this.liveVideo = liveVideo;
    }

    public UploadImage getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }


    public SLR(Context context) {
        this.context = context;
    }

    protected void startlivevideo() {
    liveVideo = new LiveVideo(context);
    liveVideo.openCamera();
}
//}
//protected void uploadImage()
//{
//    uploadImage = new UploadImage();
//    uploadImage.openGallery();
//}

}
