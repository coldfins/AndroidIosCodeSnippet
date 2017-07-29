
package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharedPost {

    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("UserId")
    @Expose
    private Object userId;
    @SerializedName("PostText")
    @Expose
    private Object postText;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("IsShare")
    @Expose
    private Boolean isShare;
    @SerializedName("SharedUserId")
    @Expose
    private Integer sharedUserId;
    @SerializedName("SharedPostId")
    @Expose
    private Integer sharedPostId;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getPostText() {
        return postText;
    }

    public void setPostText(Object postText) {
        this.postText = postText;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public Boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    public Integer getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(Integer sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    public Integer getSharedPostId() {
        return sharedPostId;
    }

    public void setSharedPostId(Integer sharedPostId) {
        this.sharedPostId = sharedPostId;
    }

}
