package com.medical.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDoctResponse {

	@SerializedName("error_code")
	@Expose
	private Integer errorCode;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("msg")
	@Expose
	private String msg;
	@SerializedName("searchLocationDetails")
	@Expose
	private List<SearchLocationDeatilModel> searchLocationDetails = new ArrayList<SearchLocationDeatilModel>();

	/**
	* 
	* @return
	* The errorCode
	*/
	public Integer getErrorCode() {
	return errorCode;
	}

	/**
	* 
	* @param errorCode
	* The error_code
	*/
	public void setErrorCode(Integer errorCode) {
	this.errorCode = errorCode;
	}

	/**
	* 
	* @return
	* The status
	*/
	public String getStatus() {
	return status;
	}

	/**
	* 
	* @param status
	* The status
	*/
	public void setStatus(String status) {
	this.status = status;
	}

	/**
	* 
	* @return
	* The msg
	*/
	public String getMsg() {
	return msg;
	}

	/**
	* 
	* @param msg
	* The msg
	*/
	public void setMsg(String msg) {
	this.msg = msg;
	}

	/**
	* 
	* @return
	* The searchLocationDetails
	*/
	public List<SearchLocationDeatilModel> getSearchLocationDetails() {
	return searchLocationDetails;
	}

	/**
	* 
	* @param searchLocationDetails
	* The searchLocationDetails
	*/
	public void setSearchLocationDetails(List<SearchLocationDeatilModel> searchLocationDetails) {
	this.searchLocationDetails = searchLocationDetails;
	}
}
