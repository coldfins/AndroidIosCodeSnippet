
package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_ {

    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    @SerializedName("PostText")
    @Expose
    private String postText;

    @SerializedName("PostedDate")
    @Expose
    private String PostedDate;

    @SerializedName("IsShare")
    @Expose
    private boolean IsShare;

    @SerializedName("IsLike")
    @Expose
    private boolean IsLike;

    @SerializedName("LikeCount")
    @Expose
    private int LikeCount;

    @SerializedName("ShareCount")
    @Expose
    private int ShareCount;

    @SerializedName("CommentCount")
    @Expose
    private int CommentCount;

    @SerializedName("SharedUserId")
    @Expose
    private int SharedUserId;

    @SerializedName("SharedPostId")
    @Expose
    private int SharedPostId;

    @SerializedName("IsReport")
    @Expose
    private boolean IsReport;

    public boolean isReport() {
        return IsReport;
    }

    public void setReport(boolean report) {
        IsReport = report;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        this.CommentCount = commentCount;
    }

    public boolean isShare() {
        return IsShare;
    }

    public void setShare(boolean share) {
        IsShare = share;
    }

    public boolean isLike() {
        return IsLike;
    }

    public void setLike(boolean like) {
        IsLike = like;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public int getShareCount() {
        return ShareCount;
    }

    public void setShareCount(int shareCount) {
        ShareCount = shareCount;
    }

    public int getSharedUserId() {
        return SharedUserId;
    }

    public void setSharedUserId(int sharedUserId) {
        SharedUserId = sharedUserId;
    }

    public int getSharedPostId() {
        return SharedPostId;
    }

    public void setSharedPostId(int sharedPostId) {
        SharedPostId = sharedPostId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        this.PostedDate = postedDate;
    }

}
