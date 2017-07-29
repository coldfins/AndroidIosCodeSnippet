package com.food.eathub.Model.feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.food.eathub.Model.FeedBackDetailModel;
import com.food.eathub.Model.GetRestaurantDetailModel;

public class FeedBackswithrest {

	@SerializedName("feedback")
	@Expose
	private FeedBackDetailModel feedback;
	@SerializedName("restaurant")
	@Expose
	private GetRestaurantDetailModel restaurant;

	/**
	 * 
	 * @return The feedback
	 */
	public FeedBackDetailModel getFeedback() {
		return feedback;
	}

	/**
	 * 
	 * @param feedback
	 *            The feedback
	 */
	public void setFeedback(FeedBackDetailModel feedback) {
		this.feedback = feedback;
	}

	/**
	 * 
	 * @return The restaurant
	 */
	public GetRestaurantDetailModel getRestaurant() {
		return restaurant;
	}

	/**
	 * 
	 * @param restaurant
	 *            The restaurant
	 */
	public void setRestaurant(GetRestaurantDetailModel restaurant) {
		this.restaurant = restaurant;
	}

}
