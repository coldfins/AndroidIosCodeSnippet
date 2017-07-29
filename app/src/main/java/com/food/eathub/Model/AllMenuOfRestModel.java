package com.food.eathub.Model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMenuOfRestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	@SerializedName("Category")
	@Expose
	private AllCategorybymenuModel Category;
	@SerializedName("Dishes")
	@Expose
	private ArrayList<AllDishbycategoryModel> Dishes = new ArrayList<AllDishbycategoryModel>();

	/**
	 * 
	 * @return The Category
	 */
	public AllCategorybymenuModel getCategory() {
		return Category;
	}

	/**
	 * 
	 * @param Category
	 *            The Category
	 */
	public void setCategory(AllCategorybymenuModel Category) {
		this.Category = Category;
	}

	/**
	 * 
	 * @return The Dishes
	 */
	public ArrayList<AllDishbycategoryModel> getDishes() {
		return Dishes;
	}

	/**
	 * 
	 * @param Dishes
	 *            The Dishes
	 */
	public void setDishes(ArrayList<AllDishbycategoryModel> Dishes) {
		this.Dishes = Dishes;
	}

}
