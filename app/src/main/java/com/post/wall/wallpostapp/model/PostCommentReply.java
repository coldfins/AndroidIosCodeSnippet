package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ved_pc on 3/8/2017.
 */

public class PostCommentReply {
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("postCommentReply")
    @Expose
    private PostCommentReplyModel postCommentReply;

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

    public PostCommentReplyModel getPostCommentReplyModel() {
        return postCommentReply;
    }

    public void setPostCommentReplyModel(PostCommentReplyModel postCommentReplyModel) {
        this.postCommentReply = postCommentReplyModel;
    }



}
