package com.food.eathub.Model;

import java.io.Serializable;

public class AddToBasketRestDishesModel implements Serializable {
	AllDishbycategoryModel dish;
	int qty;
	String catname;

	public AllDishbycategoryModel getDish() {
		return dish;
	}

	public void setDish(AllDishbycategoryModel dish) {
		this.dish = dish;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

}
