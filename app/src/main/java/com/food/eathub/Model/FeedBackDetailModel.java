package com.food.eathub.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBackDetailModel {

	@SerializedName("FeedbackId")
	@Expose
	private Integer FeedbackId;
	@SerializedName("FeedbackData")
	@Expose
	private String FeedbackData;
	@SerializedName("RestaurantId")
	@Expose
	private Integer RestaurantId;
	@SerializedName("UserId")
	@Expose
	private Integer UserId;
	@SerializedName("Rate")
	@Expose
	private double Rate;
	@SerializedName("DateTime")
	@Expose
	private String DateTime;

	/**
	 * 
	 * @return The FeedbackId
	 */
	public Integer getFeedbackId() {
		return FeedbackId;
	}

	/**
	 * 
	 * @param FeedbackId
	 *            The FeedbackId
	 */
	public void setFeedbackId(Integer FeedbackId) {
		this.FeedbackId = FeedbackId;
	}

	/**
	 * 
	 * @return The FeedbackData
	 */
	public String getFeedbackData() {
		return FeedbackData;
	}

	/**
	 * 
	 * @param FeedbackData
	 *            The FeedbackData
	 */
	public void setFeedbackData(String FeedbackData) {
		this.FeedbackData = FeedbackData;
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

	/**
	 * 
	 * @return The UserId
	 */
	public Integer getUserId() {
		return UserId;
	}

	/**
	 * 
	 * @param UserId
	 *            The UserId
	 */
	public void setUserId(Integer UserId) {
		this.UserId = UserId;
	}

	/**
	 * 
	 * @return The Rate
	 */
	public double getRate() {
		return Rate;
	}

	/**
	 * 
	 * @param Rate
	 *            The Rate
	 */
	public void setRate(double Rate) {
		this.Rate = Rate;
	}

	/**
	 * 
	 * @return The DateTime
	 */
	public String getDateTime() {
		return DateTime;
	}

	/**
	 * 
	 * @param DateTime
	 *            The DateTime
	 */
	public void setDateTime(String DateTime) {
		this.DateTime = DateTime;
	}

}
