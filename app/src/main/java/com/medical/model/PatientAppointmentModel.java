package com.medical.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientAppointmentModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("FirstName")
	@Expose
	private String FirstName;
	@SerializedName("LastName")
	@Expose
	private String LastName;
	@SerializedName("Email")
	@Expose
	private String Email;
	@SerializedName("UserName")
	@Expose
	private String UserName;
	@SerializedName("Mobile")
	@Expose
	private String Mobile;
	@SerializedName("UserType")
	@Expose
	private String UserType;
	@SerializedName("UserImage")
	@Expose
	private String UserImage;
	@SerializedName("CategoryName")
	@Expose
	private String CategoryName;
	@SerializedName("SubCategoryName")
	@Expose
	private String SubCategoryName;
	@SerializedName("ClinicStartTime")
	@Expose
	private String ClinicStartTime;
	@SerializedName("ClinicEndTime")
	@Expose
	private String ClinicEndTime;
	@SerializedName("ClinicName")
	@Expose
	private String ClinicName;
	@SerializedName("ConsultationFees")
	@Expose
	private Integer ConsultationFees;
	@SerializedName("AvailableDays")
	@Expose
	private String AvailableDays;
	@SerializedName("ClinicAddress")
	@Expose
	private String ClinicAddress;
	@SerializedName("Latitude")
	@Expose
	private Double Latitude;
	@SerializedName("Longitude")
	@Expose
	private Double Longitude;
	@SerializedName("AppointmentId")
	@Expose
	private Integer AppointmentId;
	@SerializedName("AppointmentDate")
	@Expose
	private String AppointmentDate;
	@SerializedName("StartTime")
	@Expose
	private String StartTime;
	@SerializedName("EndTime")
	@Expose
	private String EndTime;
	@SerializedName("PostedDate")
	@Expose
	private String PostedDate;
	@SerializedName("Attend")
	@Expose
	private Integer Attend;
	@SerializedName("DiseasesDescription")
	@Expose
	private Object DiseasesDescription;
	@SerializedName("DiseasesImage")
	@Expose
	private String DiseasesImage;
	@SerializedName("path")
	@Expose
	private String path;

	/**
	* 
	* @return
	* The FirstName
	*/
	public String getFirstName() {
	return FirstName;
	}

	/**
	* 
	* @param FirstName
	* The FirstName
	*/
	public void setFirstName(String FirstName) {
	this.FirstName = FirstName;
	}

	/**
	* 
	* @return
	* The LastName
	*/
	public String getLastName() {
	return LastName;
	}

	/**
	* 
	* @param LastName
	* The LastName
	*/
	public void setLastName(String LastName) {
	this.LastName = LastName;
	}

	/**
	* 
	* @return
	* The Email
	*/
	public String getEmail() {
	return Email;
	}

	/**
	* 
	* @param Email
	* The Email
	*/
	public void setEmail(String Email) {
	this.Email = Email;
	}

	/**
	* 
	* @return
	* The UserName
	*/
	public String getUserName() {
	return UserName;
	}

	/**
	* 
	* @param UserName
	* The UserName
	*/
	public void setUserName(String UserName) {
	this.UserName = UserName;
	}

	/**
	* 
	* @return
	* The Mobile
	*/
	public String getMobile() {
	return Mobile;
	}

	/**
	* 
	* @param Mobile
	* The Mobile
	*/
	public void setMobile(String Mobile) {
	this.Mobile = Mobile;
	}

	/**
	* 
	* @return
	* The UserType
	*/
	public String getUserType() {
	return UserType;
	}

	/**
	* 
	* @param UserType
	* The UserType
	*/
	public void setUserType(String UserType) {
	this.UserType = UserType;
	}

	/**
	* 
	* @return
	* The UserImage
	*/
	public String getUserImage() {
	return UserImage;
	}

	/**
	* 
	* @param UserImage
	* The UserImage
	*/
	public void setUserImage(String UserImage) {
	this.UserImage = UserImage;
	}

	/**
	* 
	* @return
	* The CategoryName
	*/
	public String getCategoryName() {
	return CategoryName;
	}

	/**
	* 
	* @param CategoryName
	* The CategoryName
	*/
	public void setCategoryName(String CategoryName) {
	this.CategoryName = CategoryName;
	}

	/**
	* 
	* @return
	* The SubCategoryName
	*/
	public String getSubCategoryName() {
	return SubCategoryName;
	}

	/**
	* 
	* @param SubCategoryName
	* The SubCategoryName
	*/
	public void setSubCategoryName(String SubCategoryName) {
	this.SubCategoryName = SubCategoryName;
	}

	/**
	* 
	* @return
	* The ClinicStartTime
	*/
	public String getClinicStartTime() {
	return ClinicStartTime;
	}

	/**
	* 
	* @param ClinicStartTime
	* The ClinicStartTime
	*/
	public void setClinicStartTime(String ClinicStartTime) {
	this.ClinicStartTime = ClinicStartTime;
	}

	/**
	* 
	* @return
	* The ClinicEndTime
	*/
	public String getClinicEndTime() {
	return ClinicEndTime;
	}

	/**
	* 
	* @param ClinicEndTime
	* The ClinicEndTime
	*/
	public void setClinicEndTime(String ClinicEndTime) {
	this.ClinicEndTime = ClinicEndTime;
	}

	/**
	* 
	* @return
	* The ClinicName
	*/
	public String getClinicName() {
	return ClinicName;
	}

	/**
	* 
	* @param ClinicName
	* The ClinicName
	*/
	public void setClinicName(String ClinicName) {
	this.ClinicName = ClinicName;
	}

	/**
	* 
	* @return
	* The ConsultationFees
	*/
	public Integer getConsultationFees() {
	return ConsultationFees;
	}

	/**
	* 
	* @param ConsultationFees
	* The ConsultationFees
	*/
	public void setConsultationFees(Integer ConsultationFees) {
	this.ConsultationFees = ConsultationFees;
	}

	/**
	* 
	* @return
	* The AvailableDays
	*/
	public String getAvailableDays() {
	return AvailableDays;
	}

	/**
	* 
	* @param AvailableDays
	* The AvailableDays
	*/
	public void setAvailableDays(String AvailableDays) {
	this.AvailableDays = AvailableDays;
	}

	/**
	* 
	* @return
	* The ClinicAddress
	*/
	public String getClinicAddress() {
	return ClinicAddress;
	}

	/**
	* 
	* @param ClinicAddress
	* The ClinicAddress
	*/
	public void setClinicAddress(String ClinicAddress) {
	this.ClinicAddress = ClinicAddress;
	}

	/**
	* 
	* @return
	* The Latitude
	*/
	public Double getLatitude() {
	return Latitude;
	}

	/**
	* 
	* @param Latitude
	* The Latitude
	*/
	public void setLatitude(Double Latitude) {
	this.Latitude = Latitude;
	}

	/**
	* 
	* @return
	* The Longitude
	*/
	public Double getLongitude() {
	return Longitude;
	}

	/**
	* 
	* @param Longitude
	* The Longitude
	*/
	public void setLongitude(Double Longitude) {
	this.Longitude = Longitude;
	}

	/**
	* 
	* @return
	* The AppointmentId
	*/
	public Integer getAppointmentId() {
	return AppointmentId;
	}

	/**
	* 
	* @param AppointmentId
	* The AppointmentId
	*/
	public void setAppointmentId(Integer AppointmentId) {
	this.AppointmentId = AppointmentId;
	}

	/**
	* 
	* @return
	* The AppointmentDate
	*/
	public String getAppointmentDate() {
	return AppointmentDate;
	}

	/**
	* 
	* @param AppointmentDate
	* The AppointmentDate
	*/
	public void setAppointmentDate(String AppointmentDate) {
	this.AppointmentDate = AppointmentDate;
	}

	/**
	* 
	* @return
	* The StartTime
	*/
	public String getStartTime() {
	return StartTime;
	}

	/**
	* 
	* @param StartTime
	* The StartTime
	*/
	public void setStartTime(String StartTime) {
	this.StartTime = StartTime;
	}

	/**
	* 
	* @return
	* The EndTime
	*/
	public String getEndTime() {
	return EndTime;
	}

	/**
	* 
	* @param EndTime
	* The EndTime
	*/
	public void setEndTime(String EndTime) {
	this.EndTime = EndTime;
	}

	/**
	* 
	* @return
	* The PostedDate
	*/
	public String getPostedDate() {
	return PostedDate;
	}

	/**
	* 
	* @param PostedDate
	* The PostedDate
	*/
	public void setPostedDate(String PostedDate) {
	this.PostedDate = PostedDate;
	}

	/**
	* 
	* @return
	* The Attend
	*/
	public Integer getAttend() {
	return Attend;
	}

	/**
	* 
	* @param Attend
	* The Attend
	*/
	public void setAttend(Integer Attend) {
	this.Attend = Attend;
	}

	/**
	* 
	* @return
	* The DiseasesDescription
	*/
	public Object getDiseasesDescription() {
	return DiseasesDescription;
	}

	/**
	* 
	* @param DiseasesDescription
	* The DiseasesDescription
	*/
	public void setDiseasesDescription(Object DiseasesDescription) {
	this.DiseasesDescription = DiseasesDescription;
	}

	/**
	* 
	* @return
	* The DiseasesImage
	*/
	public Object getDiseasesImage() {
	return DiseasesImage;
	}

	/**
	* 
	* @param DiseasesImage
	* The DiseasesImage
	*/
	public void setDiseasesImage(String DiseasesImage) {
	this.DiseasesImage = DiseasesImage;
	}

	/**
	* 
	* @return
	* The path
	*/
	public String getPath() {
	return path;
	}

	/**
	* 
	* @param path
	* The path
	*/
	public void setPath(String path) {
	this.path = path;
	}
}
