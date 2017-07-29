package com.medical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayDoctProfileModel {

	@SerializedName("DocInfoId")
	@Expose
	private Integer DocInfoId;
	@SerializedName("UserId")
	@Expose
	private Integer UserId;
	@SerializedName("CategoryName")
	@Expose
	private String CategoryName;
	@SerializedName("SubCategoryName")
	@Expose
	private String SubCategoryName;
	@SerializedName("DegreeName")
	@Expose
	private String DegreeName;
	@SerializedName("ExperienceYear")
	@Expose
	private String ExperienceYear;
	@SerializedName("ConsultationFees")
	@Expose
	private Integer ConsultationFees;
	@SerializedName("AvailableDays")
	@Expose
	private String AvailableDays;
	@SerializedName("AppointmentLength")
	@Expose
	private String AppointmentLength;
	@SerializedName("ClinicStartTime")
	@Expose
	private String ClinicStartTime;
	@SerializedName("ClinicEndTime")
	@Expose
	private String ClinicEndTime;
	@SerializedName("ClinicName")
	@Expose
	private String ClinicName;
	@SerializedName("ClinicAddress")
	@Expose
	private String ClinicAddress;
	@SerializedName("Latitude")
	@Expose
	private Double Latitude;
	@SerializedName("Longitude")
	@Expose
	private Double Longitude;
	@SerializedName("ClinicPhoneNumber")
	@Expose
	private String ClinicPhoneNumber;
	@SerializedName("UserImage")
	@Expose
	private String UserImage;
	@SerializedName("UserName")
	@Expose
	private String UserName;
	@SerializedName("Description")
	@Expose
	private String Description;
	@SerializedName("DegreeYear")
	@Expose
	private Integer DegreeYear;
	@SerializedName("CollegeName")
	@Expose
	private String CollegeName;

	/**
	* 
	* @return
	* The DocInfoId
	*/
	public Integer getDocInfoId() {
	return DocInfoId;
	}

	/**
	* 
	* @param DocInfoId
	* The DocInfoId
	*/
	public void setDocInfoId(Integer DocInfoId) {
	this.DocInfoId = DocInfoId;
	}

	/**
	* 
	* @return
	* The UserId
	*/
	public Integer getUserId() {
	return UserId;
	}

	/**
	* 
	* @param UserId
	* The UserId
	*/
	public void setUserId(Integer UserId) {
	this.UserId = UserId;
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
	* The DegreeName
	*/
	public String getDegreeName() {
	return DegreeName;
	}

	/**
	* 
	* @param DegreeName
	* The DegreeName
	*/
	public void setDegreeName(String DegreeName) {
	this.DegreeName = DegreeName;
	}

	/**
	* 
	* @return
	* The ExperienceYear
	*/
	public String getExperienceYear() {
	return ExperienceYear;
	}

	/**
	* 
	* @param ExperienceYear
	* The ExperienceYear
	*/
	public void setExperienceYear(String ExperienceYear) {
	this.ExperienceYear = ExperienceYear;
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
	* The AppointmentLength
	*/
	public String getAppointmentLength() {
	return AppointmentLength;
	}

	/**
	* 
	* @param AppointmentLength
	* The AppointmentLength
	*/
	public void setAppointmentLength(String AppointmentLength) {
	this.AppointmentLength = AppointmentLength;
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
	* The ClinicPhoneNumber
	*/
	public String getClinicPhoneNumber() {
	return ClinicPhoneNumber;
	}

	/**
	* 
	* @param ClinicPhoneNumber
	* The ClinicPhoneNumber
	*/
	public void setClinicPhoneNumber(String ClinicPhoneNumber) {
	this.ClinicPhoneNumber = ClinicPhoneNumber;
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
	* The Description
	*/
	public String getDescription() {
	return Description;
	}

	/**
	* 
	* @param Description
	* The Description
	*/
	public void setDescription(String Description) {
	this.Description = Description;
	}

	/**
	* 
	* @return
	* The DegreeYear
	*/
	public Integer getDegreeYear() {
	return DegreeYear;
	}

	/**
	* 
	* @param DegreeYear
	* The DegreeYear
	*/
	public void setDegreeYear(Integer DegreeYear) {
	this.DegreeYear = DegreeYear;
	}

	/**
	* 
	* @return
	* The CollegeName
	*/
	public String getCollegeName() {
	return CollegeName;
	}

	/**
	* 
	* @param CollegeName
	* The CollegeName
	*/
	public void setCollegeName(String CollegeName) {
	this.CollegeName = CollegeName;
	}

}
