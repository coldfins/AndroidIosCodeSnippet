package com.medical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegistrationRespose {

	@SerializedName("error_code")
	@Expose
	private Integer errorCode;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("msg")
	@Expose
	private String msg;
	@SerializedName("newRegistration")
	@Expose
	private UserRegistrationModel newRegistration;

	/**
	 * 
	 * @return The errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * @param errorCode
	 *            The error_code
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 
	 * @param msg
	 *            The msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * @return The newRegistration
	 */
	public UserRegistrationModel getNewRegistration() {
		return newRegistration;
	}

	/**
	 * 
	 * @param newRegistration
	 *            The newRegistration
	 */
	public void setNewRegistration(UserRegistrationModel newRegistration) {
		this.newRegistration = newRegistration;
	}

}
