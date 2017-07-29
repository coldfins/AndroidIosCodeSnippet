package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ved_pc on 3/9/2017.
 */

public class PostComment {

    @SerializedName("PostCommentId")
    @Expose
    private Integer PostCommentId;

    @SerializedName("UserId")
    @Expose
    private Integer userId;

    @SerializedName("PostId")
    @Expose
    private Integer PostId;

    @SerializedName("Comment")
    @Expose
    private String Comment;

    @SerializedName("CommentImage")
    @Expose
    private String CommentImage;

    @SerializedName("PostedDate")
    @Expose
    private String PostedDate;


    @SerializedName("LikeCount")
    @Expose
    private Integer LikeCount;

    @SerializedName("ReplyCount")
    @Expose
    private Integer ReplyCount;

    @SerializedName("FirstName")
    @Expose
    private String FirstName;

    @SerializedName("LastName")
    @Expose
    private String LastName;

    @SerializedName("ProfilePic")
    @Expose
    private String ProfilePic;

    @SerializedName("BirthDate")
    @Expose
    private String BirthDate;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("Contact")
    @Expose
    private String Contact;

    @SerializedName("IsLike")
    @Expose
    private boolean IsLike;

    @SerializedName("IsPublic")
    @Expose
    private boolean IsPublic;

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    public boolean isLike() {
        return IsLike;
    }

    public void setLike(boolean like) {
        IsLike = like;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Integer getReplyCount() {
        return ReplyCount;
    }

    public void setReplyCount(Integer replyCount) {
        ReplyCount = replyCount;
    }

    public Integer getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(Integer likeCount) {
        LikeCount = likeCount;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public Integer getPostCommentId() {
        return PostCommentId;
    }

    public void setPostCommentId(Integer postCommentId) {
        this.PostCommentId = postCommentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return PostId;
    }

    public void setPostId(Integer postId) {
        this.PostId = postId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }

    public String getCommentImage() {
        return CommentImage;
    }

    public void setCommentImage(String commentImage) {
        this.CommentImage = commentImage;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        this.PostedDate = postedDate;
    }
}