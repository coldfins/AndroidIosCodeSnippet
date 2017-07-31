
package Model.LikeUnlike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikesModel {

    @SerializedName("LikeId")
    @Expose
    private Integer likeId;
    @SerializedName("UserIdWhoLiked")
    @Expose
    private Integer userIdWhoLiked;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    /**
     * 
     * @return
     *     The likeId
     */
    public Integer getLikeId() {
        return likeId;
    }

    /**
     * 
     * @param likeId
     *     The LikeId
     */
    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    /**
     * 
     * @return
     *     The userIdWhoLiked
     */
    public Integer getUserIdWhoLiked() {
        return userIdWhoLiked;
    }

    /**
     * 
     * @param userIdWhoLiked
     *     The UserIdWhoLiked
     */
    public void setUserIdWhoLiked(Integer userIdWhoLiked) {
        this.userIdWhoLiked = userIdWhoLiked;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The UserId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
