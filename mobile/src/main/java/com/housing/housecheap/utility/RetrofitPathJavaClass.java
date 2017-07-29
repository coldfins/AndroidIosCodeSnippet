package com.housing.housecheap.utility;

import com.housing.housecheap.model.EditProfile;
import com.housing.housecheap.model.FinalUser;
import com.housing.housecheap.model.GetAllCity;
import com.housing.housecheap.model.GetAllPropertiList;
import com.housing.housecheap.model.GetPropertyByIdUser;
import com.housing.housecheap.model.GetPropertyType;
import com.housing.housecheap.model.GetWishList;
import com.housing.housecheap.model.SearchPropertyPost;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

public interface RetrofitPathJavaClass {

	@Multipart
	@POST("/user_sign_up")
	void insertUser(@Part("name") String name,
					@Part("first_name") String fname, @Part("last_name") String lname,
					@Part("email") String email, @Part("password") String password,
					@Part("mobile") String mobile, @Part("profilephoto") TypedFile pic,
					Callback<FinalUser> callback);

	@Multipart
	@POST("/edit_profile")
	void editProfile(@Part("id") int id, @Part("name") String name,
					 @Part("first_name") String fname, @Part("last_name") String lname,
					 @Part("mobile") String mobile, @Part("profilephoto") TypedFile pic,
					 Callback<EditProfile> callback);

	@GET("/get_all_properties_list")
	void getAllPropertyReport(Callback<GetAllPropertiList> cb);

	@GET("/get_user_property_shortlist/{user_id}")
	void getWhishlistById(@Path("user_id") int id, Callback<GetWishList> callback);

	@GET("/get_user_property/{id}/{user_id}")
	void getPropertyByIdUser(@Path("id") int id, @Path("user_id") int uid, Callback<GetPropertyByIdUser> callback);

	@GET("/get_property_types")
	void allPropertyType(Callback<GetPropertyType> callback);

	@GET("/get_cities")
	void getAllCityy(Callback<GetAllCity> callback);

	@POST("/searched_property_list")
	@FormUrlEncoded
	void searchProperty(@Field("city") String city,
						@Field("property_for") String pFor,
						@Field("bhk_type") int bhktype,
						@Field("min_price") String minPrice,
						@Field("max_price") String maxPrice,
						@Field("min_build_area") String minBuild,
						@Field("max_build_area") String maxBuild,
						@Field("property_type[]") ArrayList<Integer> pType,
						@Field("bathroom") int bathroom,
						Callback<SearchPropertyPost> callback);



}
