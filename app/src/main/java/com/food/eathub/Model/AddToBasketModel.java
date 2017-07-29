package com.food.eathub.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class AddToBasketModel implements Serializable {

	int restid;
	float servicetax;
	int deliveryfee;
	ArrayList<AddToBasketRestDishesModel> dishes = new ArrayList<>();
	GetAllRestaurantsModel restdetail = new GetAllRestaurantsModel();
	float subtotal;
	String restLaterDate;
	String restLaterTime;

	public String getRestLaterDate() {
		return restLaterDate;
	}

	public void setRestLaterDate(String restLaterDate) {
		this.restLaterDate = restLaterDate;
	}

	public String getRestLaterTime() {
		return restLaterTime;
	}

	public void setRestLaterTime(String restLaterTime) {
		this.restLaterTime = restLaterTime;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public GetAllRestaurantsModel getRestdetail() {
		return restdetail;
	}

	public void setRestdetail(GetAllRestaurantsModel restdetail) {
		this.restdetail = restdetail;
	}

	public float getServicetax() {
		return servicetax;
	}

	public void setServicetax(float servicetax) {
		this.servicetax = servicetax;
	}

	public int getDeliveryfee() {
		return deliveryfee;
	}

	public void setDeliveryfee(int deliveryfee) {
		this.deliveryfee = deliveryfee;
	}

	public int getRestid() {
		return restid;
	}

	public void setRestid(int restid) {
		this.restid = restid;
	}

	public ArrayList<AddToBasketRestDishesModel> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<AddToBasketRestDishesModel> dishes) {
		this.dishes = dishes;
	}

}
