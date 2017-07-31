
package Model.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("MessageContent")
    @Expose
    private String messageContent;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("Counter")
    @Expose
    private String counter;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("LikeOrNot")
    @Expose
    private Boolean likeOrNot;

    public Boolean getLikeOrNot() {
        return likeOrNot;
    }

    public void setLikeOrNot(Boolean likeOrNot) {
        this.likeOrNot = likeOrNot;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @SerializedName("SendRcvFlag")

    @Expose
    private String sendRcvFlag;

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

    /**
     * 
     * @return
     *     The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The UserName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The Image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The messageId
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * 
     * @param messageId
     *     The MessageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * 
     * @return
     *     The messageContent
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 
     * @param messageContent
     *     The MessageContent
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * 
     * @return
     *     The counter
     */
    public String getCounter() {
        return counter;
    }

    /**
     * 
     * @param counter
     *     The Counter
     */
    public void setCounter(String counter) {
        this.counter = counter;
    }

    /**
     * 
     * @return
     *     The postedDate
     */
    public String getPostedDate() {
        return postedDate;
    }

    /**
     * 
     * @param postedDate
     *     The PostedDate
     */
    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    /**
     * 
     * @return
     *     The sendRcvFlag
     */
    public String getSendRcvFlag() {
        return sendRcvFlag;
    }

    /**
     * 
     * @param sendRcvFlag
     *     The SendRcvFlag
     */
    public void setSendRcvFlag(String sendRcvFlag) {
        this.sendRcvFlag = sendRcvFlag;
    }

}
