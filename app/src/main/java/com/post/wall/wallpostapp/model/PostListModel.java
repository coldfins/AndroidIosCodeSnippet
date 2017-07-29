
package com.post.wall.wallpostapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostListModel {

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
    private Post post = null;

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
