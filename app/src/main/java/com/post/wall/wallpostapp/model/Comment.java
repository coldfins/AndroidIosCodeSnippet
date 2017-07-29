package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ved_pc on 3/9/2017.
 */

public class Comment {

    @SerializedName("postcomment")
    @Expose
    private PostComment postcomment;

    @SerializedName("postcommentreply")
    @Expose
    private List<PostCommentReplyModel> postcommentreply = null;

    public PostComment getPostcomment() {
        return postcomment;
    }

    public void setPostcomment(PostComment postcomment) {
        this.postcomment = postcomment;
    }

    public List<PostCommentReplyModel> getPostcommentreply() {
        return postcommentreply;
    }

    public void setPostcommentreply(List<PostCommentReplyModel> postcommentreply) {
        this.postcommentreply = postcommentreply;
    }

}
