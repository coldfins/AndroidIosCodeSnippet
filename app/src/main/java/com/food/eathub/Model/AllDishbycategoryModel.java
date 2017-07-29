package com.food.eathub.Model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDishbycategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("DishId")
	@Expose
	private Integer DishId;
	@SerializedName("DishName")
	@Expose
	private String DishName;
	@SerializedName("DishImage")
	@Expose
	private String DishImage;
	@SerializedName("DishPrice")
	@Expose
	private Integer DishPrice;
	@SerializedName("Description")
	@Expose
	private String Description;

	@SerializedName("Calories")
	@Expose
	private String Calories;
	@SerializedName("MenuCategoryId")
	@Expose
	private Integer MenuCategoryId;

	/**
	 * 
	 * @return The DishId
	 */

	public Integer getDishId() {
		return DishId;
	}

	public String getCalories() {
		return Calories;
	}

	public void setCalories(String calories) {
		Calories = calories;
	}

	/**
	 * 
	 * @param DishId
	 *            The DishId
	 */
	public void setDishId(Integer DishId) {
		this.DishId = DishId;
	}

	/**
	 * 
	 * @return The DishName
	 */
	public String getDishName() {
		return DishName;
	}

	/**
	 * 
	 * @param DishName
	 *            The DishName
	 */
	public void setDishName(String DishName) {
		this.DishName = DishName;
	}

	/**
	 * 
	 * @return The DishImage
	 */
	public String getDishImage() {
		return DishImage;
	}

	/**
	 * 
	 * @param DishImage
	 *            The DishImage
	 */
	public void setDishImage(String DishImage) {
		this.DishImage = DishImage;
	}

	/**
	 * 
	 * @return The DishPrice
	 */
	public Integer getDishPrice() {
		return DishPrice;
	}

	/**
	 * 
	 * @param DishPrice
	 *            The DishPrice
	 */
	public void setDishPrice(Integer DishPrice) {
		this.DishPrice = DishPrice;
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

}
