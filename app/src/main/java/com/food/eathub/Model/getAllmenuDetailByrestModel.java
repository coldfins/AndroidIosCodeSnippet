package com.food.eathub.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class getAllmenuDetailByrestModel {

	@SerializedName("error_code")
	@Expose
	private Integer errorCode;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("Menu")
	@Expose
	private ArrayList<AllMenuOfRestModel> Menu = new ArrayList<AllMenuOfRestModel>();

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
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return The Menu
	 */
	public ArrayList<AllMenuOfRestModel> getMenu() {
		return Menu;
	}

	/**
	 * 
	 * @param Menu
	 *            The Menu
	 */
	public void setMenu(ArrayList<AllMenuOfRestModel> Menu) {
		this.Menu = Menu;
	}

}
