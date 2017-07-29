package com.food.eathub.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantAndCuisineModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("Restaurant")
	@Expose
	private GetRestaurantDetailModel Restaurant;
	@SerializedName("Cuisines")
	@Expose
	private List<RestCuisineModel> Cuisines = new ArrayList<RestCuisineModel>();

	@SerializedName("WorkingHours")
	@Expose
	private ArrayList<GetAllWorkingHrbyRestModel> WorkingHours = new ArrayList<GetAllWorkingHrbyRestModel>();

	@SerializedName("TotalReviews")
	@Expose
	private Integer totalReviews;

	public Integer getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(Integer totalReviews) {
		this.totalReviews = totalReviews;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<GetAllWorkingHrbyRestModel> getWorkingHours() {
		return WorkingHours;
	}

	public void setWorkingHours(
			ArrayList<GetAllWorkingHrbyRestModel> workingHours) {
		WorkingHours = workingHours;
	}

	/**
	 * 
	 * @return The Restaurant
	 */
	public GetRestaurantDetailModel getRestaurant() {
		return Restaurant;
	}

	/**
	 * 
	 * @param Restaurant
	 *            The Restaurant
	 */
	public void setRestaurant(GetRestaurantDetailModel Restaurant) {
		this.Restaurant = Restaurant;
	}

	/**
	 * 
	 * @return The Cuisines
	 */
	public List<RestCuisineModel> getCuisines() {
		return Cuisines;
	}

	/**
	 * 
	 * @param Cuisines
	 *            The Cuisines
	 */
	public void setCuisines(List<RestCuisineModel> Cuisines) {
		this.Cuisines = Cuisines;
	}

}
