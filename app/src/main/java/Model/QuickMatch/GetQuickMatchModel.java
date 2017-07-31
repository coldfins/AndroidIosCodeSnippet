
package Model.QuickMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetQuickMatchModel {

    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("matchedUsersList")
    @Expose
    private List<QuickMatchedUsersListModel> matchedUsersList = new ArrayList<QuickMatchedUsersListModel>();

    /**
     * 
     * @return
     *     The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 
     * @param errorCode
     *     The error_code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * @return
     *     The matchedUsersList
     */
    public List<QuickMatchedUsersListModel> getMatchedUsersList() {
        return matchedUsersList;
    }

    /**
     * 
     * @param matchedUsersList
     *     The matchedUsersList
     */
    public void setMatchedUsersList(List<QuickMatchedUsersListModel> matchedUsersList) {
        this.matchedUsersList = matchedUsersList;
    }

}
