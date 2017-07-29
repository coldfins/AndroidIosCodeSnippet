package com.food.eathub.Model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestCuisineModel implements Serializable {

	@SerializedName("RestaurantCuisineId")
	@Expose
	private Integer RestaurantCuisineId;
	@SerializedName("RestaurantId")
	@Expose
	private Integer RestaurantId;
	@SerializedName("CuisineName")
	@Expose
	private String CuisineName;

	/**
	 * 
	 * @return The RestaurantCuisineId
	 */
	public Integer getRestaurantCuisineId() {
		return RestaurantCuisineId;
	}

	/**
	 * 
	 * @param RestaurantCuisineId
	 *            The RestaurantCuisineId
	 */
	public void setRestaurantCuisineId(Integer RestaurantCuisineId) {
		this.RestaurantCuisineId = RestaurantCuisineId;
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
	 * @return The CuisineName
	 */
	public String getCuisineName() {
		return CuisineName;
	}

	/**
	 * 
	 * @param CuisineName
	 *            The CuisineName
	 */
	public void setCuisineName(String CuisineName) {
		this.CuisineName = CuisineName;
	}

}
