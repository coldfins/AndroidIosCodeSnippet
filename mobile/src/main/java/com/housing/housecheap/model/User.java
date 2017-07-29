
package com.housing.housecheap.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("facebookid")
    @Expose
    private Object facebookid;
    @SerializedName("googleplusid")
    @Expose
    private Object googleplusid;
    @SerializedName("profilephoto")
    @Expose
    private Profilephoto profilephoto;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("verified")
    @Expose
    private Object verified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("provider")
    @Expose
    private Object provider;
    @SerializedName("uid")
    @Expose
    private Object uid;
    @SerializedName("oauth_token")
    @Expose
    private Object oauthToken;
    @SerializedName("oauth_expires_at")
    @Expose
    private Object oauthExpiresAt;
    @SerializedName("usertype_id")
    @Expose
    private Integer usertypeId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The facebookid
     */
    public Object getFacebookid() {
        return facebookid;
    }

    /**
     * 
     * @param facebookid
     *     The facebookid
     */
    public void setFacebookid(Object facebookid) {
        this.facebookid = facebookid;
    }

    /**
     * 
     * @return
     *     The googleplusid
     */
    public Object getGoogleplusid() {
        return googleplusid;
    }

    /**
     * 
     * @param googleplusid
     *     The googleplusid
     */
    public void setGoogleplusid(Object googleplusid) {
        this.googleplusid = googleplusid;
    }

    /**
     * 
     * @return
     *     The profilephoto
     */
    public Profilephoto getProfilephoto() {
        return profilephoto;
    }

    /**
     * 
     * @param profilephoto
     *     The profilephoto
     */
    public void setProfilephoto(Profilephoto profilephoto) {
        this.profilephoto = profilephoto;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The verified
     */
    public Object getVerified() {
        return verified;
    }

    /**
     * 
     * @param verified
     *     The verified
     */
    public void setVerified(Object verified) {
        this.verified = verified;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The provider
     */
    public Object getProvider() {
        return provider;
    }

    /**
     * 
     * @param provider
     *     The provider
     */
    public void setProvider(Object provider) {
        this.provider = provider;
    }

    /**
     * 
     * @return
     *     The uid
     */
    public Object getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(Object uid) {
        this.uid = uid;
    }

    /**
     * 
     * @return
     *     The oauthToken
     */
    public Object getOauthToken() {
        return oauthToken;
    }

    /**
     * 
     * @param oauthToken
     *     The oauth_token
     */
    public void setOauthToken(Object oauthToken) {
        this.oauthToken = oauthToken;
    }

    /**
     * 
     * @return
     *     The oauthExpiresAt
     */
    public Object getOauthExpiresAt() {
        return oauthExpiresAt;
    }

    /**
     * 
     * @param oauthExpiresAt
     *     The oauth_expires_at
     */
    public void setOauthExpiresAt(Object oauthExpiresAt) {
        this.oauthExpiresAt = oauthExpiresAt;
    }

    /**
     * 
     * @return
     *     The usertypeId
     */
    public Integer getUsertypeId() {
        return usertypeId;
    }

    /**
     * 
     * @param usertypeId
     *     The usertype_id
     */
    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
