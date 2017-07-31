
package Model.QuickMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuickMatchedUsersListModel {

    @SerializedName("UserDetailId")
    @Expose
    private Integer userDetailId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("NoOfChildren")
    @Expose
    private Integer noOfChildren;
    @SerializedName("Age")
    @Expose
    private Integer age;
    @SerializedName("Religion")
    @Expose
    private String religion;
    @SerializedName("UserLatitude")
    @Expose
    private Double userLatitude;
    @SerializedName("UserLongitude")
    @Expose
    private Double userLongitude;
    @SerializedName("MatchLatitude")
    @Expose
    private Integer matchLatitude;
    @SerializedName("MatchLongitude")
    @Expose
    private Integer matchLongitude;
    @SerializedName("IsSmoke")
    @Expose
    private String isSmoke;
    @SerializedName("IsDrink")
    @Expose
    private String isDrink;
    @SerializedName("LocationDistance")
    @Expose
    private String locationDistance;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("JobType")
    @Expose
    private String jobType;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;

    /**
     * 
     * @return
     *     The userDetailId
     */
    public Integer getUserDetailId() {
        return userDetailId;
    }

    /**
     * 
     * @param userDetailId
     *     The UserDetailId
     */
    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
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

    /**
     * 
     * @return
     *     The noOfChildren
     */
    public Integer getNoOfChildren() {
        return noOfChildren;
    }

    /**
     * 
     * @param noOfChildren
     *     The NoOfChildren
     */
    public void setNoOfChildren(Integer noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    /**
     * 
     * @return
     *     The age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     * @param age
     *     The Age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 
     * @return
     *     The religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * 
     * @param religion
     *     The Religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * 
     * @return
     *     The userLatitude
     */
    public Double getUserLatitude() {
        return userLatitude;
    }

    /**
     * 
     * @param userLatitude
     *     The UserLatitude
     */
    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    /**
     * 
     * @return
     *     The userLongitude
     */
    public Double getUserLongitude() {
        return userLongitude;
    }

    /**
     * 
     * @param userLongitude
     *     The UserLongitude
     */
    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    /**
     * 
     * @return
     *     The matchLatitude
     */
    public Integer getMatchLatitude() {
        return matchLatitude;
    }

    /**
     * 
     * @param matchLatitude
     *     The MatchLatitude
     */
    public void setMatchLatitude(Integer matchLatitude) {
        this.matchLatitude = matchLatitude;
    }

    /**
     * 
     * @return
     *     The matchLongitude
     */
    public Integer getMatchLongitude() {
        return matchLongitude;
    }

    /**
     * 
     * @param matchLongitude
     *     The MatchLongitude
     */
    public void setMatchLongitude(Integer matchLongitude) {
        this.matchLongitude = matchLongitude;
    }

    /**
     * 
     * @return
     *     The isSmoke
     */
    public String getIsSmoke() {
        return isSmoke;
    }

    /**
     * 
     * @param isSmoke
     *     The IsSmoke
     */
    public void setIsSmoke(String isSmoke) {
        this.isSmoke = isSmoke;
    }

    /**
     * 
     * @return
     *     The isDrink
     */
    public String getIsDrink() {
        return isDrink;
    }

    /**
     * 
     * @param isDrink
     *     The IsDrink
     */
    public void setIsDrink(String isDrink) {
        this.isDrink = isDrink;
    }

    /**
     * 
     * @return
     *     The locationDistance
     */
    public String getLocationDistance() {
        return locationDistance;
    }

    /**
     * 
     * @param locationDistance
     *     The LocationDistance
     */
    public void setLocationDistance(String locationDistance) {
        this.locationDistance = locationDistance;
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
     *     The jobType
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * 
     * @param jobType
     *     The JobType
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * 
     * @return
     *     The profilePic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * 
     * @param profilePic
     *     The ProfilePic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
