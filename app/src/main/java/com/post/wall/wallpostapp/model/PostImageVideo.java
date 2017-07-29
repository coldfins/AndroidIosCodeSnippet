
package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostImageVideo {

    @SerializedName("PostContent")
    @Expose
    private String postContent;

    @SerializedName("VideoThumbnail")
    @Expose
    private String VideoThumbnail;

    @SerializedName("IsImageVideo")
    @Expose
    private Integer isImageVideo;

    @SerializedName("PostId")
    @Expose
    private Integer postId;

    public String getVideoThumbnail() {
        return VideoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        VideoThumbnail = videoThumbnail;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Integer getIsImageVideo() {
        return isImageVideo;
    }

    public void setIsImageVideo(Integer isImageVideo) {
        this.isImageVideo = isImageVideo;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

}
