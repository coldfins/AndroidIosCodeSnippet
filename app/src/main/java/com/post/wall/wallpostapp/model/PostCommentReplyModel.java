package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ved_pc on 3/9/2017.
 */

public class PostCommentReplyModel {
    @SerializedName("PostCommentReplyId")
    @Expose
    private Integer postCommentReplyId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("PostCommentId")
    @Expose
    private Integer postCommentId;
    @SerializedName("Reply")
    @Expose
    private String reply;
    @SerializedName("ReplyImage")
    @Expose
    private String replyImage;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("LikeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;

    @SerializedName("IsLike")
    @Expose
    private boolean IsLike;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("BirthDate")
    @Expose
    private String BirthDate;

    @SerializedName("Contact")
    @Expose
    private String Contact;

    @SerializedName("IsPublic")
    @Expose
    private boolean IsPublic;

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
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

    public boolean isLike() {
        return IsLike;
    }

    public void setLike(boolean like) {
        IsLike = like;
    }

    public Integer getPostCommentReplyId() {
        return postCommentReplyId;
    }

    public void setPostCommentReplyId(Integer postCommentReplyId) {
        this.postCommentReplyId = postCommentReplyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostCommentId() {
        return postCommentId;
    }

    public void setPostCommentId(Integer postCommentId) {
        this.postCommentId = postCommentId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyImage() {
        return replyImage;
    }

    public void setReplyImage(String replyImage) {
        this.replyImage = replyImage;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
