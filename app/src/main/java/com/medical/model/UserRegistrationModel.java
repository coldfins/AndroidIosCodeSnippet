package com.medical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegistrationModel {

	@SerializedName("UserId")
	@Expose
	private Integer userId;
	@SerializedName("UserType")
	@Expose
	private String userType;
	@SerializedName("FirstName")
	@Expose
	private String firstName;
	@SerializedName("LastName")
	@Expose
	private String lastName;
	@SerializedName("Email")
	@Expose
	private String email;
	@SerializedName("UserName")
	@Expose
	private String userName;
	@SerializedName("Password")
	@Expose
	private String password;
	@SerializedName("Gender")
	@Expose
	private String gender;
	@SerializedName("Mobile")
	@Expose
	private String mobile;
	@SerializedName("PostedDate")
	@Expose
	private String postedDate;
	@SerializedName("UserImage")
	@Expose
	private String userImage;
	@SerializedName("Address")
	@Expose
	private String address;
	@SerializedName("CountryCode")
	@Expose
	private String countryCode;
	@SerializedName("DeviceType")
	@Expose
	private Object deviceType;
	@SerializedName("TokenId")
	@Expose
	private Object tokenId;
	@SerializedName("RegisteredToken")
	@Expose
	private Object registeredToken;
	@SerializedName("DocInfoId")
	@Expose
	private Integer docInfoId;

	/**
	* 
	* @return
	* The userId
	*/
	public Integer getUserId() {
	return userId;
	}

	/**
	* 
	* @param userId
	* The UserId
	*/
	public void setUserId(Integer userId) {
	this.userId = userId;
	}

	/**
	* 
	* @return
	* The userType
	*/
	public String getUserType() {
	return userType;
	}

	/**
	* 
	* @param userType
	* The UserType
	*/
	public void setUserType(String userType) {
	this.userType = userType;
	}

	/**
	* 
	* @return
	* The firstName
	*/
	public String getFirstName() {
	return firstName;
	}

	/**
	* 
	* @param firstName
	* The FirstName
	*/
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	/**
	* 
	* @return
	* The lastName
	*/
	public String getLastName() {
	return lastName;
	}

	/**
	* 
	* @param lastName
	* The LastName
	*/
	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	/**
	* 
	* @return
	* The email
	*/
	public String getEmail() {
	return email;
	}

	/**
	* 
	* @param email
	* The Email
	*/
	public void setEmail(String email) {
	this.email = email;
	}

	/**
	* 
	* @return
	* The userName
	*/
	public String getUserName() {
	return userName;
	}

	/**
	* 
	* @param userName
	* The UserName
	*/
	public void setUserName(String userName) {
	this.userName = userName;
	}

	/**
	* 
	* @return
	* The password
	*/
	public String getPassword() {
	return password;
	}

	/**
	* 
	* @param password
	* The Password
	*/
	public void setPassword(String password) {
	this.password = password;
	}

	/**
	* 
	* @return
	* The gender
	*/
	public String getGender() {
	return gender;
	}

	/**
	* 
	* @param gender
	* The Gender
	*/
	public void setGender(String gender) {
	this.gender = gender;
	}

	/**
	* 
	* @return
	* The mobile
	*/
	public String getMobile() {
	return mobile;
	}

	/**
	* 
	* @param mobile
	* The Mobile
	*/
	public void setMobile(String mobile) {
	this.mobile = mobile;
	}

	/**
	* 
	* @return
	* The postedDate
	*/
	public String getPostedDate() {
	return postedDate;
	}

	/**
	* 
	* @param postedDate
	* The PostedDate
	*/
	public void setPostedDate(String postedDate) {
	this.postedDate = postedDate;
	}

	/**
	* 
	* @return
	* The userImage
	*/
	public String getUserImage() {
	return userImage;
	}

	/**
	* 
	* @param userImage
	* The UserImage
	*/
	public void setUserImage(String userImage) {
	this.userImage = userImage;
	}

	/**
	* 
	* @return
	* The address
	*/
	public String getAddress() {
	return address;
	}

	/**
	* 
	* @param address
	* The Address
	*/
	public void setAddress(String address) {
	this.address = address;
	}

	/**
	* 
	* @return
	* The countryCode
	*/
	public String getCountryCode() {
	return countryCode;
	}

	/**
	* 
	* @param countryCode
	* The CountryCode
	*/
	public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
	}

	/**
	* 
	* @return
	* The deviceType
	*/
	public Object getDeviceType() {
	return deviceType;
	}

	/**
	* 
	* @param deviceType
	* The DeviceType
	*/
	public void setDeviceType(Object deviceType) {
	this.deviceType = deviceType;
	}

	/**
	* 
	* @return
	* The tokenId
	*/
	public Object getTokenId() {
	return tokenId;
	}

	/**
	* 
	* @param tokenId
	* The TokenId
	*/
	public void setTokenId(Object tokenId) {
	this.tokenId = tokenId;
	}

	/**
	* 
	* @return
	* The registeredToken
	*/
	public Object getRegisteredToken() {
	return registeredToken;
	}

	/**
	* 
	* @param registeredToken
	* The RegisteredToken
	*/
	public void setRegisteredToken(Object registeredToken) {
	this.registeredToken = registeredToken;
	}

	/**
	* 
	* @return
	* The docInfoId
	*/
	public Integer getDocInfoId() {
	return docInfoId;
	}

	/**
	* 
	* @param docInfoId
	* The DocInfoId
	*/
	public void setDocInfoId(Integer docInfoId) {
	this.docInfoId = docInfoId;
	}


}
