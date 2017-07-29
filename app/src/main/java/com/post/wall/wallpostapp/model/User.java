package com.post.wall.wallpostapp.model;

/**
 * Created by ved_pc on 2/21/2017.
 */

public class User {

    public int UserId;
    public String Email;
    public String FirstName;
    public String LastName;
    public String BirthDate;
    public String ProfilePic;
    public String Password;
    public String Contact;
    public String PostedDate;
    boolean IsPublic;


    public User(int userId, String email, String firstName, String lastName, String birthDate, String profilePic, String contact, boolean IsPublic) {
        UserId = userId;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
        ProfilePic = profilePic;
        Contact = contact;
        this.IsPublic = IsPublic;
    }

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }


}
