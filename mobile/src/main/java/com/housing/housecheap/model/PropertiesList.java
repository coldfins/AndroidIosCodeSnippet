
package com.housing.housecheap.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PropertiesList implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("propertytype_id")
    @Expose
    private Integer propertytypeId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("adddate")
    @Expose
    private String adddate;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("buildarea")
    @Expose
    private Integer buildarea;
    @SerializedName("facing")
    @Expose
    private String facing;
    @SerializedName("floorno")
    @Expose
    private String floorno;
    @SerializedName("bathroom")
    @Expose
    private Integer bathroom;
    @SerializedName("maintenance")
    @Expose
    private String maintenance;
    @SerializedName("restriction")
    @Expose
    private String restriction;
    @SerializedName("availibility")
    @Expose
    private String availibility;
    @SerializedName("powerbackup")
    @Expose
    private String powerbackup;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("property_for")
    @Expose
    private String propertyFor;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("bhktype_id")
    @Expose
    private Integer bhktypeId;
    @SerializedName("shortlist")
    @Expose
    private Boolean shortlist;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("balcony")
    @Expose
    private Integer balcony;
    @SerializedName("user_contact_no")
    @Expose
    private String userContactNo;
    @SerializedName("user_first_name")
    @Expose
    private String userFirstName;
    @SerializedName("user_last_name")
    @Expose
    private String userLastName;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("bhktype")
    @Expose
    private String bhktype;
    @SerializedName("propertyphoto_url")
    @Expose
    private String propertyphotoUrl;
    @SerializedName("propertytypes")
    @Expose
    private String propertytypes;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The propertytypeId
     */
    public Integer getPropertytypeId() {
        return propertytypeId;
    }

    /**
     * 
     * @param propertytypeId
     *     The propertytype_id
     */
    public void setPropertytypeId(Integer propertytypeId) {
        this.propertytypeId = propertytypeId;
    }

    /**
     * 
     * @return
     *     The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * @return
     *     The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The adddate
     */
    public String getAdddate() {
        return adddate;
    }

    /**
     * 
     * @param adddate
     *     The adddate
     */
    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The area
     */
    public String getArea() {
        return area;
    }

    /**
     * 
     * @param area
     *     The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 
     * @return
     *     The rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * 
     * @param rate
     *     The rate
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
     * 
     * @return
     *     The buildarea
     */
    public Integer getBuildarea() {
        return buildarea;
    }

    /**
     * 
     * @param buildarea
     *     The buildarea
     */
    public void setBuildarea(Integer buildarea) {
        this.buildarea = buildarea;
    }

    /**
     * 
     * @return
     *     The facing
     */
    public String getFacing() {
        return facing;
    }

    /**
     * 
     * @param facing
     *     The facing
     */
    public void setFacing(String facing) {
        this.facing = facing;
    }

    /**
     * 
     * @return
     *     The floorno
     */
    public String getFloorno() {
        return floorno;
    }

    /**
     * 
     * @param floorno
     *     The floorno
     */
    public void setFloorno(String floorno) {
        this.floorno = floorno;
    }

    /**
     * 
     * @return
     *     The bathroom
     */
    public Integer getBathroom() {
        return bathroom;
    }

    /**
     * 
     * @param bathroom
     *     The bathroom
     */
    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    /**
     * 
     * @return
     *     The maintenance
     */
    public String getMaintenance() {
        return maintenance;
    }

    /**
     * 
     * @param maintenance
     *     The maintenance
     */
    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    /**
     * 
     * @return
     *     The restriction
     */
    public String getRestriction() {
        return restriction;
    }

    /**
     * 
     * @param restriction
     *     The restriction
     */
    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    /**
     * 
     * @return
     *     The availibility
     */
    public String getAvailibility() {
        return availibility;
    }

    /**
     * 
     * @param availibility
     *     The availibility
     */
    public void setAvailibility(String availibility) {
        this.availibility = availibility;
    }

    /**
     * 
     * @return
     *     The powerbackup
     */
    public String getPowerbackup() {
        return powerbackup;
    }

    /**
     * 
     * @param powerbackup
     *     The powerbackup
     */
    public void setPowerbackup(String powerbackup) {
        this.powerbackup = powerbackup;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The propertyFor
     */
    public String getPropertyFor() {
        return propertyFor;
    }

    /**
     * 
     * @param propertyFor
     *     The property_for
     */
    public void setPropertyFor(String propertyFor) {
        this.propertyFor = propertyFor;
    }

    /**
     * 
     * @return
     *     The addedby
     */
    public String getAddedby() {
        return addedby;
    }

    /**
     * 
     * @param addedby
     *     The addedby
     */
    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    /**
     * 
     * @return
     *     The bhktypeId
     */
    public Integer getBhktypeId() {
        return bhktypeId;
    }

    /**
     * 
     * @param bhktypeId
     *     The bhktype_id
     */
    public void setBhktypeId(Integer bhktypeId) {
        this.bhktypeId = bhktypeId;
    }

    /**
     * 
     * @return
     *     The shortlist
     */
    public Boolean getShortlist() {
        return shortlist;
    }

    /**
     * 
     * @param shortlist
     *     The shortlist
     */
    public void setShortlist(Boolean shortlist) {
        this.shortlist = shortlist;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The balcony
     */
    public Integer getBalcony() {
        return balcony;
    }

    /**
     * 
     * @param balcony
     *     The balcony
     */
    public void setBalcony(Integer balcony) {
        this.balcony = balcony;
    }

    /**
     * 
     * @return
     *     The userContactNo
     */
    public String getUserContactNo() {
        return userContactNo;
    }

    /**
     * 
     * @param userContactNo
     *     The user_contact_no
     */
    public void setUserContactNo(String userContactNo) {
        this.userContactNo = userContactNo;
    }

    /**
     * 
     * @return
     *     The userFirstName
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * 
     * @param userFirstName
     *     The user_first_name
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * 
     * @return
     *     The userLastName
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * 
     * @param userLastName
     *     The user_last_name
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * 
     * @return
     *     The usertype
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * 
     * @param usertype
     *     The usertype
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The bhktype
     */
    public String getBhktype() {
        return bhktype;
    }

    /**
     * 
     * @param bhktype
     *     The bhktype
     */
    public void setBhktype(String bhktype) {
        this.bhktype = bhktype;
    }

    /**
     * 
     * @return
     *     The propertyphotoUrl
     */
    public String getPropertyphotoUrl() {
        return propertyphotoUrl;
    }

    /**
     * 
     * @param propertyphotoUrl
     *     The propertyphoto_url
     */
    public void setPropertyphotoUrl(String propertyphotoUrl) {
        this.propertyphotoUrl = propertyphotoUrl;
    }

    /**
     * 
     * @return
     *     The propertytypes
     */
    public String getPropertytypes() {
        return propertytypes;
    }

    /**
     * 
     * @param propertytypes
     *     The propertytypes
     */
    public void setPropertytypes(String propertytypes) {
        this.propertytypes = propertytypes;
    }

	/*public PropertiesList(String address, String area,
			Integer rate, String userContactNo, String city, String bhktype,
			String propertytypes) {
		super();
		//this.id = id;
		//Integer id, 
		this.address = address;
		this.area = area;
		this.rate = rate;
		this.userContactNo = userContactNo;
		this.city = city;
		this.bhktype = bhktype;
		this.propertytypes = propertytypes;
	}*/

	public PropertiesList() {
		super();
	}

	public PropertiesList(String area, Integer rate, String userContactNo,
			String city, String bhktype, String propertytypes) {
		super();
		this.area = area;
		this.rate = rate;
		this.userContactNo = userContactNo;
		this.city = city;
		this.bhktype = bhktype;
		this.propertytypes = propertytypes;
	}
    
    
    

}
