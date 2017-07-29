package com.food.eathub.Interface;

import com.food.eathub.Model.GetAllRestaurantsModel;
import com.food.eathub.Model.getAllmenuDetailByrestModel;

import retrofit.Callback;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;

@SuppressWarnings("deprecation")
public interface RestInterface {

	@GET("/getrestaurantsByLocation")
	void getrestaurantsByLocation(@EncodedQuery("Latitude") Double lati,
			@EncodedQuery("Longitude") Double longi,
			@EncodedQuery("offset") int offset,
			@EncodedQuery("limit") int limit,
			Callback<GetAllRestaurantsModel> allrest);


	@GET("/getRestaurantById/{id}")
	void getRestaurantById(@EncodedQuery("id") int id,
			Callback<GetAllRestaurantsModel> rest);

	@GET("/getRestaurantDishMenu")
	void getRestaurantDishMenu(@EncodedQuery("RestaurantId") int id,
			Callback<getAllmenuDetailByrestModel> allmenu);
}