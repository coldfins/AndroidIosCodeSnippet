package com.post.wall.wallpostapp.utility;

import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.model.LikePostUserListModel;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.model.PostCommentModel;
import com.post.wall.wallpostapp.model.PostCommentReply;
import com.post.wall.wallpostapp.model.PostCommentReplyModel;
import com.post.wall.wallpostapp.model.PostCommentsListModel;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.model.PostListModel;
import com.post.wall.wallpostapp.model.SharePostModel;
import com.post.wall.wallpostapp.model.UserModel;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;


public interface RestInterface {


    @Multipart
    @POST("/UserRegistration")
    void RegistrationReport(@Part("Email") String Email, @Part("FirstName") String FirstName, @Part("LastName") String LastName, @Part("BirthDate") String BirthDate,
                            @Part("Contact") String Contact, @Part("Password") String Password, @Part("ProfilePic") TypedFile ProfilePic, Callback<UserModel> userModel);

    @Multipart
    @POST("/EditProfile")
    void editProfile(@Part("UserId") int UserId, @Part("Email") String Email, @Part("FirstName") String FirstName, @Part("LastName") String LastName, @Part("BirthDate") String BirthDate,
                            @Part("Contact") String Contact, @Part("IsPublic") boolean IsPublic, @Part("ProfilePic") TypedFile ProfilePic, Callback<UserModel> userModel);

    @FormUrlEncoded
    @POST("/UserLogin")
    void Login(@Field("Email") String Email,
                    @Field("Password") String Password,
                    Callback<UserModel> userModel);


    @GET("/postList")
    void getPostList(@Query("offset") int offset, @Query("limit") int limit, @Query("UserId") int UserId, Callback<PostListModel> postListModelCallback);




    @POST("/userPost")
    void AddPost(@Body MultipartTypedOutput attachments, Callback<Post> Post);


    @Multipart
        @POST("/userPost")
        void uploadSurvey(@Part("UserId") int UserId, @Part("PostText") String PostText, @Part("ImagesVideos") String ImagesVideos, @Part("ImageContent") MultipartBody.Part[] surveyImage, Callback<Post> Post);

    @FormUrlEncoded
    @POST("/likeUnlikePost")
    void LikeUnLikePost(@Field("LikeUserId") int LikeUserId, @Field("PostId") int PostId, Callback<PostLikeModel> postLikeModelCallback);

    @FormUrlEncoded
    @POST("/sharePost")
    void sharePost(@Field("PostId") int PostId, @Field("ShareText") String ShareText, @Field("SharedUserId") int SharedUserId, Callback<Post> postCallback);

    @GET("/getLikeUsersOfPost")
    void getLikeUsersOfPost(@Query("offset") int offset, @Query("limit") int limit, @Query("PostId") int PostId, Callback<LikePostUserListModel> likePostUserListModelCallback);


    @GET("/postCommentList")
    void getPostCommentList(@Query("offset") int offset, @Query("limit") int limit, @Query("PostId") int PostId, @Query("UserId") int UserId, Callback<PostCommentsListModel> postCommentsListModelCallback);

    @Multipart
    @POST("/postComment")
    void postComment(@Part("PostId") int PostId, @Part("UserId") int UserId, @Part("Comment") String Comment, @Part("CommentImage") TypedFile CommentImage, Callback<PostCommentModel> postCommentCallback);

    @FormUrlEncoded
    @POST("/postCommentLikeUnlike")
    void postCommentLikeUnlike(@Field("UserId") int UserId, @Field("PostCommentId") int PostCommentId, Callback<PostLikeModel> postLikeModelCallback);

    @FormUrlEncoded
    @POST("/postCommentReplyLikeUnlike")
    void postCommentReplyLikeUnlike(@Field("UserId") int UserId, @Field("PostCommentReplyId") int PostCommentReplyId, Callback<PostLikeModel> postLikeModelCallback);

    @Multipart
    @POST("/postCommentReply")
    void postCommentReply(@Part("PostCommentId") int PostCommentId, @Part("UserId") int UserId, @Part("Reply") String Reply, @Part("ReplyImage") TypedFile ReplyImage, Callback<PostCommentReply> postCommentCallback);

    @FormUrlEncoded
    @POST("/addPostToReport")
    void addPostToReport(@Field("UserId") int UserId, @Field("PostId") int PostId, @Field("Description") String Description, Callback<PostLikeModel> postLikeModelCallback);

    @FormUrlEncoded
    @POST("/ChangePassword")
    void ChangePassword(@Field("UserId") int UserId, @Field("OldPassword") String OldPassword, @Field("NewPassword") String NewPassword, Callback<PostLikeModel> postLikeModelCallback);

    @FormUrlEncoded
    @POST("/forgotPassword")
    void forgotPassword(@Field("Email") String Email, Callback<PostLikeModel> postLikeModelCallback);

}
