package com.medical.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppoinmentDateTimeModel  implements Serializable{

	@SerializedName("StartTime")
	@Expose
	private String StartTime;
	@SerializedName("EndTime")
	@Expose
	private String EndTime;
	@SerializedName("appointmentDate")
	@Expose
	private String appointmentDate;

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
	* The appointmentDate
	*/
	public String getAppointmentDate() {
	return appointmentDate;
	}

	/**
	* 
	* @param appointmentDate
	* The appointmentDate
	*/
	public void setAppointmentDate(String appointmentDate) {
	this.appointmentDate = appointmentDate;
	}

}
