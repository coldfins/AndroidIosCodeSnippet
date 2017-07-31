package interfaces;

import Model.Chat.GetAllConveration;
import Model.Chat.GetSendRevMsg;
import Model.Chat.SendMsgModel;
import Model.LikeUnlike.GetLikeUnlikeModel;
import Model.QuickMatch.GetQuickMatchModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface RestInterface {

    @POST("/AddLikes")
    @FormUrlEncoded
    void likeUnlike(
            @Field("UserIdWhoLiked") int selectUserid,
            @Field("UserId") int uid,
            Callback<GetLikeUnlikeModel> callback);

    @GET("/getAllConversations")
    void getAllConversation(@Query("UserId") int user_id,
                            @Query("limit") int limit,
                            @Query("offset") int offset,
                            Callback<GetAllConveration> callback);


    @POST("/receiveMessages")
    @FormUrlEncoded
    void receiveMessages(@Field("SenderId") int send_id,
                         @Field("ReceiverId") int rev_id,
                         @Field("limit") int limit,
                         @Field("offset") int offset,
                         @Field("Flag") String flag,
                         @Field("LastId") int lastId,
                         Callback<GetSendRevMsg> callback);

    @POST("/sendMessage")
    @FormUrlEncoded
    void sendMessage(@Field("SenderId") int send_id,
                     @Field("ReceiverId") int rev_id,
                     @Field("Message") String message,
                     Callback<SendMsgModel> callback);

    @GET("/getQuickMatches/{UserId}")
    void getQuickMatches(@Query("UserId") int id,
                         Callback<GetQuickMatchModel> callback);


    @POST("/AddLikeForQuickMatch")
    @FormUrlEncoded
    void addLikeForQuickMatch(
            @Field("UserIdWhoLiked") int selectUserid,
            @Field("UserId") int uid,
            Callback<GetLikeUnlikeModel> callback);

}
