package com.post.wall.wallpostapp.model;

/**
 * Created by ved_pc on 2/28/2017.
 */

public class PostLikeModel {
    int error_code;
    String status;
    String msg;
    postLike postLike;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
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

    public PostLikeModel.postLike getPostLike() {
        return postLike;
    }

    public void setPostLike(PostLikeModel.postLike postLike) {
        this.postLike = postLike;
    }

    public class postLike {
        int PostLikeId;
        int LikeUserId;
        int PostId;
        String PostedDate;

        public int getPostLikeId() {
            return PostLikeId;
        }

        public void setPostLikeId(int postLikeId) {
            PostLikeId = postLikeId;
        }

        public int getLikeUserId() {
            return LikeUserId;
        }

        public void setLikeUserId(int likeUserId) {
            LikeUserId = likeUserId;
        }

        public int getPostId() {
            return PostId;
        }

        public void setPostId(int postId) {
            PostId = postId;
        }

        public String getPostedDate() {
            return PostedDate;
        }

        public void setPostedDate(String postedDate) {
            PostedDate = postedDate;
        }
    }
}
