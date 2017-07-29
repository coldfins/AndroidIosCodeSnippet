package com.food.eathub.Model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllRestaurantsModel implements Serializable {

	@SerializedName("error_code")
	@Expose
	private Integer errorCode;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("Restaurants")
	@Expose
	private ArrayList<GetRestaurantAndCuisineModel> Restaurants = new ArrayList<GetRestaurantAndCuisineModel>();
	@SerializedName("Restaurant")
	@Expose
	private GetRestaurantAndCuisineModel Restaurant;

	public GetRestaurantAndCuisineModel getRestaurant() {
		return Restaurant;
	}

	public void setRestaurant(GetRestaurantAndCuisineModel restaurant) {
		Restaurant = restaurant;
	}

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
	 * @return The Restaurants
	 */
	public ArrayList<GetRestaurantAndCuisineModel> getRestaurants() {
		return Restaurants;
	}

	/**
	 * 
	 * @param Restaurants
	 *            The Restaurants
	 */
	public void setRestaurants(
			ArrayList<GetRestaurantAndCuisineModel> Restaurants) {
		this.Restaurants = Restaurants;
	}

}
