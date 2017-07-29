package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ved_pc on 3/8/2017.
 */

public class PostCommentModel {
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("postComment")
    @Expose
    private PostComment postComment;

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

    public PostComment getPostComment() {
        return postComment;
    }

    public void setPostComment(PostComment postComment) {
        this.postComment = postComment;
    }



}
