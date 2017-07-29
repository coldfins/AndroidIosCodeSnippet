package com.post.wall.wallpostapp.model;

import android.graphics.Bitmap;

public class AlbumImages {

    protected String albumImages;
    private boolean isSelected, isFailLoading;
    String mediaType;
    Bitmap thumbBitmap;
    boolean isCameraImage = false;

    public boolean isCameraImage() {
        return isCameraImage;
    }

    public void setCameraImage(boolean cameraImage) {
        isCameraImage = cameraImage;
    }

    public Bitmap getThumbBitmap() {
        return thumbBitmap;
    }

    public void setThumbBitmap(Bitmap thumbBitmap) {
        this.thumbBitmap = thumbBitmap;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isFailLoading() {
		return isFailLoading;
	}

	public void setFailLoading(boolean isFailLoading) {
		this.isFailLoading = isFailLoading;
	}

	public boolean isSelected () {
        return isSelected;
    }

    public void setSelected (boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getAlbumImages () {
        return albumImages;
    }

    public void setAlbumImages (String albumImages) {
        this.albumImages = albumImages;
    }
}

