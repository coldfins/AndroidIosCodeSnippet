package com.housing.housecheap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchPropertyPost {

	@SerializedName("code")
	@Expose
	private String code;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("msg")
	@Expose
	private String msg;
	@SerializedName("properties_lists")
	@Expose
	private List<PropertiesList> propertiesLists = new ArrayList<PropertiesList>();

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
	 * @return The propertiesLists
	 */
	public List<PropertiesList> getPropertiesLists() {
		return propertiesLists;
	}

	/**
	 * 
	 * @param propertiesLists
	 *            The properties_lists
	 */
	public void setPropertiesLists(List<PropertiesList> propertiesLists) {
		this.propertiesLists = propertiesLists;
	}

}
