
package com.housing.housecheap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyDetailUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("propertycategory_id")
    @Expose
    private Object propertycategoryId;
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
    @SerializedName("securitydeposit")
    @Expose
    private Object securitydeposit;
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
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("property_for")
    @Expose
    private String propertyFor;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("selltype")
    @Expose
    private String selltype;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("bhktype_id")
    @Expose
    private Integer bhktypeId;
    @SerializedName("shortlist")
    @Expose
    private Boolean shortlist;
    @SerializedName("furnishing_state")
    @Expose
    private String furnishingState;
    @SerializedName("visitor_parking")
    @Expose
    private Object visitorParking;
    @SerializedName("dining_space")
    @Expose
    private Boolean diningSpace;
    @SerializedName("year_of_construction")
    @Expose
    private String yearOfConstruction;
    @SerializedName("price_negotiable")
    @Expose
    private Boolean priceNegotiable;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("balcony")
    @Expose
    private Integer balcony;
    @SerializedName("is_possession_started")
    @Expose
    private Boolean isPossessionStarted;
    @SerializedName("possession_date")
    @Expose
    private String possessionDate;
    @SerializedName("is_panorama")
    @Expose
    private Boolean isPanorama;
    @SerializedName("p_shortlist")
    @Expose
    private Integer pShortlist;
    @SerializedName("bedroom")
    @Expose
    private Integer bedroom;
    @SerializedName("user_contact")
    @Expose
    private String userContact;
    @SerializedName("user_first_name")
    @Expose
    private String userFirstName;
    @SerializedName("user_last_name")
    @Expose
    private String userLastName;
    @SerializedName("bhk_type")
    @Expose
    private String bhkType;
    @SerializedName("property_type")
    @Expose
    private String propertyType;

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
     *     The propertycategoryId
     */
    public Object getPropertycategoryId() {
        return propertycategoryId;
    }

    /**
     * 
     * @param propertycategoryId
     *     The propertycategory_id
     */
    public void setPropertycategoryId(Object propertycategoryId) {
        this.propertycategoryId = propertycategoryId;
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
     *     The securitydeposit
     */
    public Object getSecuritydeposit() {
        return securitydeposit;
    }

    /**
     * 
     * @param securitydeposit
     *     The securitydeposit
     */
    public void setSecuritydeposit(Object securitydeposit) {
        this.securitydeposit = securitydeposit;
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
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
     *     The contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * 
     * @param contactNo
     *     The contact_no
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * 
     * @return
     *     The selltype
     */
    public String getSelltype() {
        return selltype;
    }

    /**
     * 
     * @param selltype
     *     The selltype
     */
    public void setSelltype(String selltype) {
        this.selltype = selltype;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public Object getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(Object gender) {
        this.gender = gender;
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
     *     The furnishingState
     */
    public String getFurnishingState() {
        return furnishingState;
    }

    /**
     * 
     * @param furnishingState
     *     The furnishing_state
     */
    public void setFurnishingState(String furnishingState) {
        this.furnishingState = furnishingState;
    }

    /**
     * 
     * @return
     *     The visitorParking
     */
    public Object getVisitorParking() {
        return visitorParking;
    }

    /**
     * 
     * @param visitorParking
     *     The visitor_parking
     */
    public void setVisitorParking(Object visitorParking) {
        this.visitorParking = visitorParking;
    }

    /**
     * 
     * @return
     *     The diningSpace
     */
    public Boolean getDiningSpace() {
        return diningSpace;
    }

    /**
     * 
     * @param diningSpace
     *     The dining_space
     */
    public void setDiningSpace(Boolean diningSpace) {
        this.diningSpace = diningSpace;
    }

    /**
     * 
     * @return
     *     The yearOfConstruction
     */
    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    /**
     * 
     * @param yearOfConstruction
     *     The year_of_construction
     */
    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    /**
     * 
     * @return
     *     The priceNegotiable
     */
    public Boolean getPriceNegotiable() {
        return priceNegotiable;
    }

    /**
     * 
     * @param priceNegotiable
     *     The price_negotiable
     */
    public void setPriceNegotiable(Boolean priceNegotiable) {
        this.priceNegotiable = priceNegotiable;
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
     *     The isPossessionStarted
     */
    public Boolean getIsPossessionStarted() {
        return isPossessionStarted;
    }

    /**
     * 
     * @param isPossessionStarted
     *     The is_possession_started
     */
    public void setIsPossessionStarted(Boolean isPossessionStarted) {
        this.isPossessionStarted = isPossessionStarted;
    }

    /**
     * 
     * @return
     *     The possessionDate
     */
    public String getPossessionDate() {
        return possessionDate;
    }

    /**
     * 
     * @param possessionDate
     *     The possession_date
     */
    public void setPossessionDate(String possessionDate) {
        this.possessionDate = possessionDate;
    }

    /**
     * 
     * @return
     *     The isPanorama
     */
    public Boolean getIsPanorama() {
        return isPanorama;
    }

    /**
     * 
     * @param isPanorama
     *     The is_panorama
     */
    public void setIsPanorama(Boolean isPanorama) {
        this.isPanorama = isPanorama;
    }

    /**
     * 
     * @return
     *     The pShortlist
     */
    public Integer getPShortlist() {
        return pShortlist;
    }

    /**
     * 
     * @param pShortlist
     *     The p_shortlist
     */
    public void setPShortlist(Integer pShortlist) {
        this.pShortlist = pShortlist;
    }

    /**
     * 
     * @return
     *     The bedroom
     */
    public Integer getBedroom() {
        return bedroom;
    }

    /**
     * 
     * @param bedroom
     *     The bedroom
     */
    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }

    /**
     * 
     * @return
     *     The userContact
     */
    public String getUserContact() {
        return userContact;
    }

    /**
     * 
     * @param userContact
     *     The user_contact
     */
    public void setUserContact(String userContact) {
        this.userContact = userContact;
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
     *     The bhkType
     */
    public String getBhkType() {
        return bhkType;
    }

    /**
     * 
     * @param bhkType
     *     The bhk_type
     */
    public void setBhkType(String bhkType) {
        this.bhkType = bhkType;
    }

    /**
     * 
     * @return
     *     The propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * 
     * @param propertyType
     *     The property_type
     */
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

}
