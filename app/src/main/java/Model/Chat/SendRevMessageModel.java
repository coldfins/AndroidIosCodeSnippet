
package Model.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendRevMessageModel {

    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("SenderId")
    @Expose
    private Integer senderId;
    @SerializedName("ReceiverId")
    @Expose
    private Integer receiverId;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ViewFlag")
    @Expose
    private Boolean viewFlag;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("SendRcvFlag")
    @Expose
    private String sendRcvFlag;

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
     *     The senderId
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * 
     * @param senderId
     *     The SenderId
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     * 
     * @return
     *     The receiverId
     */
    public Integer getReceiverId() {
        return receiverId;
    }

    /**
     * 
     * @param receiverId
     *     The ReceiverId
     */
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The viewFlag
     */
    public Boolean getViewFlag() {
        return viewFlag;
    }

    /**
     * 
     * @param viewFlag
     *     The ViewFlag
     */
    public void setViewFlag(Boolean viewFlag) {
        this.viewFlag = viewFlag;
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
