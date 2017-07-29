package com.medical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialistSubCategoryModel {
	@SerializedName("SubCategoryId")
	@Expose
	private Integer SubCategoryId;
	@SerializedName("SubCategoryName")
	@Expose
	private String SubCategoryName;
	@SerializedName("PostedDate")
	@Expose
	private String PostedDate;
	@SerializedName("CategoryId")
	@Expose
	private Integer CategoryId;

	/**
	 * 
	 * @return The SubCategoryId
	 */
	public Integer getSubCategoryId() {
		return SubCategoryId;
	}

	/**
	 * 
	 * @param SubCategoryId
	 *            The SubCategoryId
	 */
	public void setSubCategoryId(Integer SubCategoryId) {
		this.SubCategoryId = SubCategoryId;
	}

	/**
	 * 
	 * @return The SubCategoryName
	 */
	public String getSubCategoryName() {
		return SubCategoryName;
	}

	/**
	 * 
	 * @param SubCategoryName
	 *            The SubCategoryName
	 */
	public void setSubCategoryName(String SubCategoryName) {
		this.SubCategoryName = SubCategoryName;
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

}
