
package com.post.wall.wallpostapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharePostModel {

    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("post")
    @Expose
    private Post_ post;
    @SerializedName("sharedPost")
    @Expose
    private SharedPost sharedPost;
    @SerializedName("postImageVideos")
    @Expose
    private List<Object> postImageVideos = null;
    @SerializedName("user")
    @Expose
    private Object user;
    @SerializedName("ShareUser")
    @Expose
    private ShareUser shareUser;

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

    public Post_ getPost() {
        return post;
    }

    public void setPost(Post_ post) {
        this.post = post;
    }

    public SharedPost getSharedPost() {
        return sharedPost;
    }

    public void setSharedPost(SharedPost sharedPost) {
        this.sharedPost = sharedPost;
    }

    public List<Object> getPostImageVideos() {
        return postImageVideos;
    }

    public void setPostImageVideos(List<Object> postImageVideos) {
        this.postImageVideos = postImageVideos;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public ShareUser getShareUser() {
        return shareUser;
    }

    public void setShareUser(ShareUser shareUser) {
        this.shareUser = shareUser;
    }

}
