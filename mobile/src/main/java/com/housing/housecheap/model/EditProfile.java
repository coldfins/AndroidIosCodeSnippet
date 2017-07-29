package com.housing.housecheap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfile {

	@SerializedName("code")
	@Expose
	private String code;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("msg")
	@Expose
	private String msg;
	@SerializedName("user")
	@Expose
	private User user;

	/**
	 * 
	 * @return The code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 *            The code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return The user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            The user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
