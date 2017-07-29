package com.food.eathub.Model;

public class BasketModel {

	int deliveryfee, price;
	float servicetax;
	String catname;
	AllDishbycategoryModel dishes;

	public int getDeliveryfee() {
		return deliveryfee;
	}

	public void setDeliveryfee(int deliveryfee) {
		this.deliveryfee = deliveryfee;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public float getServicetax() {
		return servicetax;
	}

	public void setServicetax(float servicetax) {
		this.servicetax = servicetax;
	}

	public AllDishbycategoryModel getDishes() {
		return dishes;
	}

	public void setDishes(AllDishbycategoryModel dishes) {
		this.dishes = dishes;
	}

}
