
package com.post.wall.wallpostapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("post")
    @Expose
    private Post_ post;

    @SerializedName("sharedPost")
    @Expose
    private Post_ sharedPost;

    @SerializedName("postImageVideos")
    @Expose
    private List<PostImageVideo> postImageVideos = null;

    @SerializedName("ShareUser")
    @Expose
    private User ShareUser;


    public User getShareUser() {
        return ShareUser;
    }

    public void setShareUser(User shareUser) {
        ShareUser = shareUser;
    }

    public Post_ getSharedPost() {
        return sharedPost;
    }

    public void setSharedPost(Post_ sharedPost) {
        this.sharedPost = sharedPost;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post_ getPost() {
        return post;
    }

    public void setPost(Post_ post) {
        this.post = post;
    }

    public List<PostImageVideo> getPostImageVideos() {
        return postImageVideos;
    }

    public void setPostImageVideos(List<PostImageVideo> postImageVideos) {
        this.postImageVideos = postImageVideos;
    }

}
