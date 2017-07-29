package com.food.eathub.Model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantDetailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("RestaurantId")
	@Expose
	private Integer RestaurantId;
	@SerializedName("RestaurantName")
	@Expose
	private String RestaurantName;
	@SerializedName("RestaurantImage")
	@Expose
	private String RestaurantImage;
	@SerializedName("MinimumOrderAmount")
	@Expose
	private Integer MinimumOrderAmount;
	@SerializedName("RestaurantType")
	@Expose
	private String RestaurantType;
	@SerializedName("MinimumDeliveryTime")
	@Expose
	private String MinimumDeliveryTime;
	@SerializedName("OnlinePaymentAvailable")
	@Expose
	private Boolean OnlinePaymentAvailable;
	@SerializedName("PreOrderAvailable")
	@Expose
	private Boolean PreOrderAvailable;
	@SerializedName("JainFoodAvailable")
	@Expose
	private Boolean JainFoodAvailable;
	@SerializedName("OpenTime")
	@Expose
	private String OpenTime;
	@SerializedName("CloseTime")
	@Expose
	private String CloseTime;
	@SerializedName("Address")
	@Expose
	private String Address;
	@SerializedName("FastestDeliveryAvailable")
	@Expose
	private Boolean FastestDeliveryAvailable;
	@SerializedName("FavouriteRate")
	@Expose
	private Float FavouriteRate;
	@SerializedName("DeliveryFee")
	@Expose
	private Integer DeliveryFee;
	@SerializedName("ServiceTax")
	@Expose
	private Double ServiceTax;
	@SerializedName("VatTax")
	@Expose
	private Integer VatTax;
	@SerializedName("ServiceCharge")
	@Expose
	private Integer ServiceCharge;
	@SerializedName("DeliveryStartTime")
	@Expose
	private String DeliveryStartTime;
	@SerializedName("DeliveryEndTime")
	@Expose
	private String DeliveryEndTime;
	@SerializedName("AcceptsVouchers")
	@Expose
	private Boolean AcceptsVouchers;
	@SerializedName("City")
	@Expose
	private String City;
	@SerializedName("Area")
	@Expose
	private String Area;
	@SerializedName("Latitude")
	@Expose
	private Double Latitude;
	@SerializedName("Longitude")
	@Expose
	private Double Longitude;
	@SerializedName("AdminId")
	@Expose
	private Integer AdminId;

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

	/**
	 * 
	 * @return The RestaurantName
	 */
	public String getRestaurantName() {
		return RestaurantName;
	}

	/**
	 * 
	 * @param RestaurantName
	 *            The RestaurantName
	 */
	public void setRestaurantName(String RestaurantName) {
		this.RestaurantName = RestaurantName;
	}

	/**
	 * 
	 * @return The RestaurantImage
	 */
	public String getRestaurantImage() {
		return RestaurantImage;
	}

	/**
	 * 
	 * @param RestaurantImage
	 *            The RestaurantImage
	 */
	public void setRestaurantImage(String RestaurantImage) {
		this.RestaurantImage = RestaurantImage;
	}

	/**
	 * 
	 * @return The MinimumOrderAmount
	 */
	public Integer getMinimumOrderAmount() {
		return MinimumOrderAmount;
	}

	/**
	 * 
	 * @param MinimumOrderAmount
	 *            The MinimumOrderAmount
	 */
	public void setMinimumOrderAmount(Integer MinimumOrderAmount) {
		this.MinimumOrderAmount = MinimumOrderAmount;
	}

	/**
	 * 
	 * @return The RestaurantType
	 */
	public String getRestaurantType() {
		return RestaurantType;
	}

	/**
	 * 
	 * @param RestaurantType
	 *            The RestaurantType
	 */
	public void setRestaurantType(String RestaurantType) {
		this.RestaurantType = RestaurantType;
	}

	/**
	 * 
	 * @return The MinimumDeliveryTime
	 */
	public String getMinimumDeliveryTime() {
		return MinimumDeliveryTime;
	}

	/**
	 * 
	 * @param MinimumDeliveryTime
	 *            The MinimumDeliveryTime
	 */
	public void setMinimumDeliveryTime(String MinimumDeliveryTime) {
		this.MinimumDeliveryTime = MinimumDeliveryTime;
	}

	/**
	 * 
	 * @return The OnlinePaymentAvailable
	 */
	public Boolean getOnlinePaymentAvailable() {
		return OnlinePaymentAvailable;
	}

	/**
	 * 
	 * @param OnlinePaymentAvailable
	 *            The OnlinePaymentAvailable
	 */
	public void setOnlinePaymentAvailable(Boolean OnlinePaymentAvailable) {
		this.OnlinePaymentAvailable = OnlinePaymentAvailable;
	}

	/**
	 * 
	 * @return The PreOrderAvailable
	 */
	public Boolean getPreOrderAvailable() {
		return PreOrderAvailable;
	}

	/**
	 * 
	 * @param PreOrderAvailable
	 *            The PreOrderAvailable
	 */
	public void setPreOrderAvailable(Boolean PreOrderAvailable) {
		this.PreOrderAvailable = PreOrderAvailable;
	}

	/**
	 * 
	 * @return The JainFoodAvailable
	 */
	public Boolean getJainFoodAvailable() {
		return JainFoodAvailable;
	}

	/**
	 * 
	 * @param JainFoodAvailable
	 *            The JainFoodAvailable
	 */
	public void setJainFoodAvailable(Boolean JainFoodAvailable) {
		this.JainFoodAvailable = JainFoodAvailable;
	}

	/**
	 * 
	 * @return The OpenTime
	 */
	public String getOpenTime() {
		return OpenTime;
	}

	/**
	 * 
	 * @param OpenTime
	 *            The OpenTime
	 */
	public void setOpenTime(String OpenTime) {
		this.OpenTime = OpenTime;
	}

	/**
	 * 
	 * @return The CloseTime
	 */
	public String getCloseTime() {
		return CloseTime;
	}

	/**
	 * 
	 * @param CloseTime
	 *            The CloseTime
	 */
	public void setCloseTime(String CloseTime) {
		this.CloseTime = CloseTime;
	}

	/**
	 * 
	 * @return The Address
	 */
	public String getAddress() {
		return Address;
	}

	/**
	 * 
	 * @param Address
	 *            The Address
	 */
	public void setAddress(String Address) {
		this.Address = Address;
	}

	/**
	 * 
	 * @return The FastestDeliveryAvailable
	 */
	public Boolean getFastestDeliveryAvailable() {
		return FastestDeliveryAvailable;
	}

	/**
	 * 
	 * @param FastestDeliveryAvailable
	 *            The FastestDeliveryAvailable
	 */
	public void setFastestDeliveryAvailable(Boolean FastestDeliveryAvailable) {
		this.FastestDeliveryAvailable = FastestDeliveryAvailable;
	}

	/**
	 * 
	 * @return The FavouriteRate
	 */
	public Float getFavouriteRate() {
		return FavouriteRate;
	}

	/**
	 * 
	 * @param FavouriteRate
	 *            The FavouriteRate
	 */
	public void setFavouriteRate(Float FavouriteRate) {
		this.FavouriteRate = FavouriteRate;
	}

	/**
	 * 
	 * @return The DeliveryFee
	 */
	public Integer getDeliveryFee() {
		return DeliveryFee;
	}

	/**
	 * 
	 * @param DeliveryFee
	 *            The DeliveryFee
	 */
	public void setDeliveryFee(Integer DeliveryFee) {
		this.DeliveryFee = DeliveryFee;
	}

	/**
	 * 
	 * @return The ServiceTax
	 */
	public Double getServiceTax() {
		return ServiceTax;
	}

	/**
	 * 
	 * @param ServiceTax
	 *            The ServiceTax
	 */
	public void setServiceTax(Double ServiceTax) {
		this.ServiceTax = ServiceTax;
	}

	/**
	 * 
	 * @return The VatTax
	 */
	public Integer getVatTax() {
		return VatTax;
	}

	/**
	 * 
	 * @param VatTax
	 *            The VatTax
	 */
	public void setVatTax(Integer VatTax) {
		this.VatTax = VatTax;
	}

	/**
	 * 
	 * @return The ServiceCharge
	 */
	public Integer getServiceCharge() {
		return ServiceCharge;
	}

	/**
	 * 
	 * @param ServiceCharge
	 *            The ServiceCharge
	 */
	public void setServiceCharge(Integer ServiceCharge) {
		this.ServiceCharge = ServiceCharge;
	}

	/**
	 * 
	 * @return The DeliveryStartTime
	 */
	public String getDeliveryStartTime() {
		return DeliveryStartTime;
	}

	/**
	 * 
	 * @param DeliveryStartTime
	 *            The DeliveryStartTime
	 */
	public void setDeliveryStartTime(String DeliveryStartTime) {
		this.DeliveryStartTime = DeliveryStartTime;
	}

	/**
	 * 
	 * @return The DeliveryEndTime
	 */
	public String getDeliveryEndTime() {
		return DeliveryEndTime;
	}

	/**
	 * 
	 * @param DeliveryEndTime
	 *            The DeliveryEndTime
	 */
	public void setDeliveryEndTime(String DeliveryEndTime) {
		this.DeliveryEndTime = DeliveryEndTime;
	}

	/**
	 * 
	 * @return The AcceptsVouchers
	 */
	public Boolean getAcceptsVouchers() {
		return AcceptsVouchers;
	}

	/**
	 * 
	 * @param AcceptsVouchers
	 *            The AcceptsVouchers
	 */
	public void setAcceptsVouchers(Boolean AcceptsVouchers) {
		this.AcceptsVouchers = AcceptsVouchers;
	}

	/**
	 * 
	 * @return The City
	 */
	public String getCity() {
		return City;
	}

	/**
	 * 
	 * @param City
	 *            The City
	 */
	public void setCity(String City) {
		this.City = City;
	}

	/**
	 * 
	 * @return The Area
	 */
	public String getArea() {
		return Area;
	}

	/**
	 * 
	 * @param Area
	 *            The Area
	 */
	public void setArea(String Area) {
		this.Area = Area;
	}

	/**
	 * 
	 * @return The Latitude
	 */
	public Double getLatitude() {
		return Latitude;
	}

	/**
	 * 
	 * @param Latitude
	 *            The Latitude
	 */
	public void setLatitude(Double Latitude) {
		this.Latitude = Latitude;
	}

	/**
	 * 
	 * @return The Longitude
	 */
	public Double getLongitude() {
		return Longitude;
	}

	/**
	 * 
	 * @param Longitude
	 *            The Longitude
	 */
	public void setLongitude(Double Longitude) {
		this.Longitude = Longitude;
	}

	/**
	 * 
	 * @return The AdminId
	 */
	public Integer getAdminId() {
		return AdminId;
	}

	/**
	 * 
	 * @param AdminId
	 *            The AdminId
	 */
	public void setAdminId(Integer AdminId) {
		this.AdminId = AdminId;
	}

}
