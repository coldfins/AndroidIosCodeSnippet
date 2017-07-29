package com.medical.model;


import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctAppointmentTimeModel {

	@SerializedName("DocInfoId")
	@Expose
	private Integer DocInfoId;
	@SerializedName("Latitude")
	@Expose
	private Double Latitude;
	@SerializedName("Longitude")
	@Expose
	private Double Longitude;
	@SerializedName("ClinicName")
	@Expose
	private String ClinicName;
	@SerializedName("ClinicAddress")
	@Expose
	private String ClinicAddress;
	@SerializedName("ClinicPhoneNumber")
	@Expose
	private String ClinicPhoneNumber;
	@SerializedName("Name")
	@Expose
	private String Name;
	@SerializedName("ConsultationFees")
	@Expose
	private Integer ConsultationFees;
	@SerializedName("AppointmentDate")
	@Expose
	private String AppointmentDate;
	@SerializedName("Description")
	@Expose
	private Object Description;
	@SerializedName("DegreeName")
	@Expose
	private String DegreeName;
	@SerializedName("UserImage")
	@Expose
	private String UserImage;
	@SerializedName("ExperienceYear")
	@Expose
	private Object ExperienceYear;
	@SerializedName("Message")
	@Expose
	private String Message;
	@SerializedName("IsValid")
	@Expose
	private Boolean IsValid;
	
	@SerializedName("appointmentStartTimes")
	@Expose
	private ArrayList<AppoinmentDateTimeModel> appointmentStartTimes;
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
	* The Name
	*/
	public String getName() {
	return Name;
	}

	/**
	* 
	* @param Name
	* The Name
	*/
	public void setName(String Name) {
	this.Name = Name;
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
	* The Description
	*/
	public Object getDescription() {
	return Description;
	}

	/**
	* 
	* @param Description
	* The Description
	*/
	public void setDescription(Object Description) {
	this.Description = Description;
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
	* The ExperienceYear
	*/
	public Object getExperienceYear() {
	return ExperienceYear;
	}

	/**
	* 
	* @param ExperienceYear
	* The ExperienceYear
	*/
	public void setExperienceYear(Object ExperienceYear) {
	this.ExperienceYear = ExperienceYear;
	}

	/**
	* 
	* @return
	* The Message
	*/
	public String getMessage() {
	return Message;
	}

	/**
	* 
	* @param Message
	* The Message
	*/
	public void setMessage(String Message) {
	this.Message = Message;
	}

	/**
	* 
	* @return
	* The IsValid
	*/
	public Boolean getIsValid() {
	return IsValid;
	}

	/**
	* 
	* @param IsValid
	* The IsValid
	*/
	public void setIsValid(Boolean IsValid) {
	this.IsValid = IsValid;
	}

	/**
	 * 
	 * @return The appointmentStartTimes
	 */
	public ArrayList<AppoinmentDateTimeModel> getAppointmentStartTimes() {
		return appointmentStartTimes;
	}

	/**
	 * 
	 * @param appointmentStartTimes
	 *            The appointmentStartTimes
	 */
	public void setAppointmentStartTimes(
			ArrayList<AppoinmentDateTimeModel> appointmentStartTimes) {
		this.appointmentStartTimes = appointmentStartTimes;
	}
}
