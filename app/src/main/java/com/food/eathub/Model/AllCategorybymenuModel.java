package com.food.eathub.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllCategorybymenuModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("MenuCategoryId")
	@Expose
	private Integer MenuCategoryId;
	@SerializedName("MenuCategoryName")
	@Expose
	private String MenuCategoryName;
	@SerializedName("MenuCategoryImage")
	@Expose
	private String MenuCategoryImage;
	@SerializedName("Description")
	@Expose
	private String Description;
	@SerializedName("HeaderName")
	@Expose
	private String HeaderName;
	@SerializedName("RestaurantId")
	@Expose
	private Integer RestaurantId;

	/**
	 * 
	 * @return The MenuCategoryId
	 */
	public Integer getMenuCategoryId() {
		return MenuCategoryId;
	}

	/**
	 * 
	 * @param MenuCategoryId
	 *            The MenuCategoryId
	 */
	public void setMenuCategoryId(Integer MenuCategoryId) {
		this.MenuCategoryId = MenuCategoryId;
	}

	/**
	 * 
	 * @return The MenuCategoryName
	 */
	public String getMenuCategoryName() {
		return MenuCategoryName;
	}

	/**
	 * 
	 * @param MenuCategoryName
	 *            The MenuCategoryName
	 */
	public void setMenuCategoryName(String MenuCategoryName) {
		this.MenuCategoryName = MenuCategoryName;
	}

	/**
	 * 
	 * @return The MenuCategoryImage
	 */
	public String getMenuCategoryImage() {
		return MenuCategoryImage;
	}

	/**
	 * 
	 * @param MenuCategoryImage
	 *            The MenuCategoryImage
	 */
	public void setMenuCategoryImage(String MenuCategoryImage) {
		this.MenuCategoryImage = MenuCategoryImage;
	}

	/**
	 * 
	 * @return The Description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * 
	 * @param Description
	 *            The Description
	 */
	public void setDescription(String Description) {
		this.Description = Description;
	}

	/**
	 * 
	 * @return The HeaderName
	 */
	public String getHeaderName() {
		return HeaderName;
	}

	/**
	 * 
	 * @param HeaderName
	 *            The HeaderName
	 */
	public void setHeaderName(String HeaderName) {
		this.HeaderName = HeaderName;
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
