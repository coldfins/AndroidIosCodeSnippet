package com.medical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialistCategoryListModel {

	@SerializedName("CategoryId")
	@Expose
	private Integer CategoryId;
	@SerializedName("CategoryName")
	@Expose
	private String CategoryName;
	@SerializedName("PostedDate")
	@Expose
	private String PostedDate;

	/**
	 * 
	 * @return The CategoryId
	 */
	public Integer getCategoryId() {
		return CategoryId;
	}

	/**
	 * 
	 * @param CategoryId
	 *            The CategoryId
	 */
	public void setCategoryId(Integer CategoryId) {
		this.CategoryId = CategoryId;
	}

	/**
	 * 
	 * @return The CategoryName
	 */
	public String getCategoryName() {
		return CategoryName;
	}

	/**
	 * 
	 * @param CategoryName
	 *            The CategoryName
	 */
	public void setCategoryName(String CategoryName) {
		this.CategoryName = CategoryName;
	}

	/**
	 * 
	 * @return The PostedDate
	 */
	public String getPostedDate() {
		return PostedDate;
	}

	/**
	 * 
	 * @param PostedDate
	 *            The PostedDate
	 */
	public void setPostedDate(String PostedDate) {
		this.PostedDate = PostedDate;
	}

}
