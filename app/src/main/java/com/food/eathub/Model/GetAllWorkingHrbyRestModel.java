package com.food.eathub.Model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllWorkingHrbyRestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("WorkingHourId")
	@Expose
	private Integer WorkingHourId;
	@SerializedName("WorkingDay")
	@Expose
	private String WorkingDay;
	@SerializedName("OpenTime")
	@Expose
	private String OpenTime;
	@SerializedName("CloseTime")
	@Expose
	private String CloseTime;
	@SerializedName("RestaurantId")
	@Expose
	private Integer RestaurantId;

	/**
	 * 
	 * @return The WorkingHourId
	 */
	public Integer getWorkingHourId() {
		return WorkingHourId;
	}

	/**
	 * 
	 * @param WorkingHourId
	 *            The WorkingHourId
	 */
	public void setWorkingHourId(Integer WorkingHourId) {
		this.WorkingHourId = WorkingHourId;
	}

	/**
	 * 
	 * @return The WorkingDay
	 */
	public String getWorkingDay() {
		return WorkingDay;
	}

	/**
	 * 
	 * @param WorkingDay
	 *            The WorkingDay
	 */
	public void setWorkingDay(String WorkingDay) {
		this.WorkingDay = WorkingDay;
	}

	/**
	 * 
	 * @return The OpenTime
	 */
	public String getOpenTime() {
		return OpenTime;
	}

	/**
	 * 
	 * @param OpenTime
	 *            The OpenTime
	 */
	public void setOpenTime(String OpenTime) {
		this.OpenTime = OpenTime;
	}

	/**
	 * 
	 * @return The CloseTime
	 */
	public String getCloseTime() {
		return CloseTime;
	}

	/**
	 * 
	 * @param CloseTime
	 *            The CloseTime
	 */
	public void setCloseTime(String CloseTime) {
		this.CloseTime = CloseTime;
	}

	/**
	 * 
	 * @return The RestaurantId
	 */
	public Integer getRestaurantId() {
		return RestaurantId;
	}

	/**
	 * 
	 * @param RestaurantId
	 *            The RestaurantId
	 */
	public void setRestaurantId(Integer RestaurantId) {
		this.RestaurantId = RestaurantId;
	}

}
