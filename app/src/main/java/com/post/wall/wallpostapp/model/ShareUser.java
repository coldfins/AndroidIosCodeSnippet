
package com.post.wall.wallpostapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareUser {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;
    @SerializedName("Password")
    @Expose
    private Object password;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

}
