package com.post.wall.wallpostapp.model;

import org.apache.http.entity.mime.content.FileBody;

/**
 * Created by ved_pc on 2/20/2017.
 */

public class PostVideoImagesModel {
    FileBody fileBody;
    int isVideo;
    boolean isCameraImage;

    public boolean isCameraImage() {
        return isCameraImage;
    }

    public void setCameraImage(boolean cameraImage) {
        isCameraImage = cameraImage;
    }

    public PostVideoImagesModel(FileBody fileBody, int isVideo, boolean isCameraImage) {
        this.fileBody = fileBody;
        this.isVideo = isVideo;
        this.isCameraImage = isCameraImage;
    }

    public FileBody getFileBody() {
        return fileBody;
    }

    public void setFileBody(FileBody fileBody) {
        this.fileBody = fileBody;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }
}
