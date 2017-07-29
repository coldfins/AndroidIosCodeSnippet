package com.medical.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchLocationDeatilModel implements Serializable{

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
	@SerializedName("Range")
	@Expose
	private String Range;
	@SerializedName("ClinicPhoneNumber")
	@Expose
	private String ClinicPhoneNumber;
	@SerializedName("Name")
	@Expose
	private String Name;
	@SerializedName("AppointmentDate")
	@Expose
	private String AppointmentDate;
	@SerializedName("Message")
	@Expose
	private String Message;
	@SerializedName("IsValid")
	@Expose
	private Boolean IsValid;
	@SerializedName("DegreeName")
	@Expose
	private String DegreeName;
	@SerializedName("UserImage")
	@Expose
	private String UserImage;
	@SerializedName("appointmentStartTimes")
	@Expose
	private ArrayList<AppoinmentDateTimeModel> appointmentStartTimes;
	
	@SerializedName("ConsultationFees")
	@Expose
	private Integer ConsultationFees;

	/**
	 * 
	 * @return The DocInfoId
	 */
	public Integer getDocInfoId() {
		return DocInfoId;
	}

	/**
	 * 
	 * @param DocInfoId
	 *            The DocInfoId
	 */
	public void setDocInfoId(Integer DocInfoId) {
		this.DocInfoId = DocInfoId;
	}

	/**
	 * 
	 * @return The Latitude
	 */
	public Double getLatitude() {
		return Latitude;
	}

	/**
	 * 
	 * @param Latitude
	 *            The Latitude
	 */
	public void setLatitude(Double Latitude) {
		this.Latitude = Latitude;
	}

	/**
	 * 
	 * @return The Longitude
	 */
	public Double getLongitude() {
		return Longitude;
	}

	/**
	 * 
	 * @param Longitude
	 *            The Longitude
	 */
	public void setLongitude(Double Longitude) {
		this.Longitude = Longitude;
	}

	/**
	 * 
	 * @return The ClinicName
	 */
	public String getClinicName() {
		return ClinicName;
	}

	/**
	 * 
	 * @param ClinicName
	 *            The ClinicName
	 */
	public void setClinicName(String ClinicName) {
		this.ClinicName = ClinicName;
	}

	/**
	 * 
	 * @return The ClinicAddress
	 */
	public String getClinicAddress() {
		return ClinicAddress;
	}

	/**
	 * 
	 * @param ClinicAddress
	 *            The ClinicAddress
	 */
	public void setClinicAddress(String ClinicAddress) {
		this.ClinicAddress = ClinicAddress;
	}

	/**
	 * 
	 * @return The Range
	 */
	public String getRange() {
		return Range;
	}

	/**
	 * 
	 * @param Range
	 *            The Range
	 */
	public void setRange(String Range) {
		this.Range = Range;
	}

	/**
	 * 
	 * @return The ClinicPhoneNumber
	 */
	public String getClinicPhoneNumber() {
		return ClinicPhoneNumber;
	}

	/**
	 * 
	 * @param ClinicPhoneNumber
	 *            The ClinicPhoneNumber
	 */
	public void setClinicPhoneNumber(String ClinicPhoneNumber) {
		this.ClinicPhoneNumber = ClinicPhoneNumber;
	}

	/**
	 * 
	 * @return The Name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * @param Name
	 *            The Name
	 */
	public void setName(String Name) {
		this.Name = Name;
	}

	/**
	 * 
	 * @return The AppointmentDate
	 */
	public String getAppointmentDate() {
		return AppointmentDate;
	}

	/**
	 * 
	 * @param AppointmentDate
	 *            The AppointmentDate
	 */
	public void setAppointmentDate(String AppointmentDate) {
		this.AppointmentDate = AppointmentDate;
	}

	/**
	 * 
	 * @return The Message
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * 
	 * @param Message
	 *            The Message
	 */
	public void setMessage(String Message) {
		this.Message = Message;
	}

	/**
	 * 
	 * @return The IsValid
	 */
	public Boolean getIsValid() {
		return IsValid;
	}

	/**
	 * 
	 * @param IsValid
	 *            The IsValid
	 */
	public void setIsValid(Boolean IsValid) {
		this.IsValid = IsValid;
	}

	/**
	 * 
	 * @return The DegreeName
	 */
	public String getDegreeName() {
		return DegreeName;
	}

	/**
	 * 
	 * @param DegreeName
	 *            The DegreeName
	 */
	public void setDegreeName(String DegreeName) {
		this.DegreeName = DegreeName;
	}

	/**
	 * 
	 * @return The UserImage
	 */
	public String getUserImage() {
		return UserImage;
	}

	/**
	 * 
	 * @param UserImage
	 *            The UserImage
	 */
	public void setUserImage(String UserImage) {
		this.UserImage = UserImage;
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
}
