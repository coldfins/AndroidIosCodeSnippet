
package com.housing.housecheap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyamenityUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("propertydetail_id")
    @Expose
    private Integer propertydetailId;
    @SerializedName("ac")
    @Expose
    private Integer ac;
    @SerializedName("tv")
    @Expose
    private Integer tv;
    @SerializedName("cupboard")
    @Expose
    private Integer cupboard;
    @SerializedName("bed")
    @Expose
    private Integer bed;
    @SerializedName("sofa")
    @Expose
    private Integer sofa;
    @SerializedName("diningtable")
    @Expose
    private Integer diningtable;
    @SerializedName("microwave")
    @Expose
    private Integer microwave;
    @SerializedName("fridge")
    @Expose
    private Integer fridge;
    @SerializedName("stove")
    @Expose
    private Integer stove;
    @SerializedName("washingmachine")
    @Expose
    private Integer washingmachine;
    @SerializedName("serventroom")
    @Expose
    private Boolean serventroom;
    @SerializedName("kitchen")
    @Expose
    private Boolean kitchen;
    @SerializedName("mess")
    @Expose
    private Boolean mess;
    @SerializedName("cookingallow")
    @Expose
    private Boolean cookingallow;
    @SerializedName("hotwater")
    @Expose
    private Boolean hotwater;
    @SerializedName("waterpurifier")
    @Expose
    private Boolean waterpurifier;
    @SerializedName("internet")
    @Expose
    private Boolean internet;
    @SerializedName("security")
    @Expose
    private String security;
    @SerializedName("breakfast")
    @Expose
    private Boolean breakfast;
    @SerializedName("lunch")
    @Expose
    private Boolean lunch;
    @SerializedName("dinner")
    @Expose
    private Boolean dinner;
    @SerializedName("housekeeping")
    @Expose
    private Boolean housekeeping;
    @SerializedName("parking")
    @Expose
    private Boolean parking;
    @SerializedName("laundry")
    @Expose
    private Boolean laundry;
    @SerializedName("entrytill")
    @Expose
    private Boolean entrytill;
    @SerializedName("girlentry")
    @Expose
    private Boolean girlentry;
    @SerializedName("drinking")
    @Expose
    private Boolean drinking;
    @SerializedName("smoking")
    @Expose
    private Boolean smoking;
    @SerializedName("nonveg")
    @Expose
    private Boolean nonveg;
    @SerializedName("gardianentry")
    @Expose
    private Object gardianentry;
    @SerializedName("lift")
    @Expose
    private Boolean lift;
    @SerializedName("gaspipeline")
    @Expose
    private Boolean gaspipeline;
    @SerializedName("gym")
    @Expose
    private Boolean gym;
    @SerializedName("swimmingpool")
    @Expose
    private Boolean swimmingpool;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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
     *     The propertydetailId
     */
    public Integer getPropertydetailId() {
        return propertydetailId;
    }

    /**
     * 
     * @param propertydetailId
     *     The propertydetail_id
     */
    public void setPropertydetailId(Integer propertydetailId) {
        this.propertydetailId = propertydetailId;
    }

    /**
     * 
     * @return
     *     The ac
     */
    public Integer getAc() {
        return ac;
    }

    /**
     * 
     * @param ac
     *     The ac
     */
    public void setAc(Integer ac) {
        this.ac = ac;
    }

    /**
     * 
     * @return
     *     The tv
     */
    public Integer getTv() {
        return tv;
    }

    /**
     * 
     * @param tv
     *     The tv
     */
    public void setTv(Integer tv) {
        this.tv = tv;
    }

    /**
     * 
     * @return
     *     The cupboard
     */
    public Integer getCupboard() {
        return cupboard;
    }

    /**
     * 
     * @param cupboard
     *     The cupboard
     */
    public void setCupboard(Integer cupboard) {
        this.cupboard = cupboard;
    }

    /**
     * 
     * @return
     *     The bed
     */
    public Integer getBed() {
        return bed;
    }

    /**
     * 
     * @param bed
     *     The bed
     */
    public void setBed(Integer bed) {
        this.bed = bed;
    }

    /**
     * 
     * @return
     *     The sofa
     */
    public Integer getSofa() {
        return sofa;
    }

    /**
     * 
     * @param sofa
     *     The sofa
     */
    public void setSofa(Integer sofa) {
        this.sofa = sofa;
    }

    /**
     * 
     * @return
     *     The diningtable
     */
    public Integer getDiningtable() {
        return diningtable;
    }

    /**
     * 
     * @param diningtable
     *     The diningtable
     */
    public void setDiningtable(Integer diningtable) {
        this.diningtable = diningtable;
    }

    /**
     * 
     * @return
     *     The microwave
     */
    public Integer getMicrowave() {
        return microwave;
    }

    /**
     * 
     * @param microwave
     *     The microwave
     */
    public void setMicrowave(Integer microwave) {
        this.microwave = microwave;
    }

    /**
     * 
     * @return
     *     The fridge
     */
    public Integer getFridge() {
        return fridge;
    }

    /**
     * 
     * @param fridge
     *     The fridge
     */
    public void setFridge(Integer fridge) {
        this.fridge = fridge;
    }

    /**
     * 
     * @return
     *     The stove
     */
    public Integer getStove() {
        return stove;
    }

    /**
     * 
     * @param stove
     *     The stove
     */
    public void setStove(Integer stove) {
        this.stove = stove;
    }

    /**
     * 
     * @return
     *     The washingmachine
     */
    public Integer getWashingmachine() {
        return washingmachine;
    }

    /**
     * 
     * @param washingmachine
     *     The washingmachine
     */
    public void setWashingmachine(Integer washingmachine) {
        this.washingmachine = washingmachine;
    }

    /**
     * 
     * @return
     *     The serventroom
     */
    public Boolean getServentroom() {
        return serventroom;
    }

    /**
     * 
     * @param serventroom
     *     The serventroom
     */
    public void setServentroom(Boolean serventroom) {
        this.serventroom = serventroom;
    }

    /**
     * 
     * @return
     *     The kitchen
     */
    public Boolean getKitchen() {
        return kitchen;
    }

    /**
     * 
     * @param kitchen
     *     The kitchen
     */
    public void setKitchen(Boolean kitchen) {
        this.kitchen = kitchen;
    }

    /**
     * 
     * @return
     *     The mess
     */
    public Boolean getMess() {
        return mess;
    }

    /**
     * 
     * @param mess
     *     The mess
     */
    public void setMess(Boolean mess) {
        this.mess = mess;
    }

    /**
     * 
     * @return
     *     The cookingallow
     */
    public Boolean getCookingallow() {
        return cookingallow;
    }

    /**
     * 
     * @param cookingallow
     *     The cookingallow
     */
    public void setCookingallow(Boolean cookingallow) {
        this.cookingallow = cookingallow;
    }

    /**
     * 
     * @return
     *     The hotwater
     */
    public Boolean getHotwater() {
        return hotwater;
    }

    /**
     * 
     * @param hotwater
     *     The hotwater
     */
    public void setHotwater(Boolean hotwater) {
        this.hotwater = hotwater;
    }

    /**
     * 
     * @return
     *     The waterpurifier
     */
    public Boolean getWaterpurifier() {
        return waterpurifier;
    }

    /**
     * 
     * @param waterpurifier
     *     The waterpurifier
     */
    public void setWaterpurifier(Boolean waterpurifier) {
        this.waterpurifier = waterpurifier;
    }

    /**
     * 
     * @return
     *     The internet
     */
    public Boolean getInternet() {
        return internet;
    }

    /**
     * 
     * @param internet
     *     The internet
     */
    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    /**
     * 
     * @return
     *     The security
     */
    public String getSecurity() {
        return security;
    }

    /**
     * 
     * @param security
     *     The security
     */
    public void setSecurity(String security) {
        this.security = security;
    }

    /**
     * 
     * @return
     *     The breakfast
     */
    public Boolean getBreakfast() {
        return breakfast;
    }

    /**
     * 
     * @param breakfast
     *     The breakfast
     */
    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    /**
     * 
     * @return
     *     The lunch
     */
    public Boolean getLunch() {
        return lunch;
    }

    /**
     * 
     * @param lunch
     *     The lunch
     */
    public void setLunch(Boolean lunch) {
        this.lunch = lunch;
    }

    /**
     * 
     * @return
     *     The dinner
     */
    public Boolean getDinner() {
        return dinner;
    }

    /**
     * 
     * @param dinner
     *     The dinner
     */
    public void setDinner(Boolean dinner) {
        this.dinner = dinner;
    }

    /**
     * 
     * @return
     *     The housekeeping
     */
    public Boolean getHousekeeping() {
        return housekeeping;
    }

    /**
     * 
     * @param housekeeping
     *     The housekeeping
     */
    public void setHousekeeping(Boolean housekeeping) {
        this.housekeeping = housekeeping;
    }

    /**
     * 
     * @return
     *     The parking
     */
    public Boolean getParking() {
        return parking;
    }

    /**
     * 
     * @param parking
     *     The parking
     */
    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    /**
     * 
     * @return
     *     The laundry
     */
    public Boolean getLaundry() {
        return laundry;
    }

    /**
     * 
     * @param laundry
     *     The laundry
     */
    public void setLaundry(Boolean laundry) {
        this.laundry = laundry;
    }

    /**
     * 
     * @return
     *     The entrytill
     */
    public Boolean getEntrytill() {
        return entrytill;
    }

    /**
     * 
     * @param entrytill
     *     The entrytill
     */
    public void setEntrytill(Boolean entrytill) {
        this.entrytill = entrytill;
    }

    /**
     * 
     * @return
     *     The girlentry
     */
    public Boolean getGirlentry() {
        return girlentry;
    }

    /**
     * 
     * @param girlentry
     *     The girlentry
     */
    public void setGirlentry(Boolean girlentry) {
        this.girlentry = girlentry;
    }

    /**
     * 
     * @return
     *     The drinking
     */
    public Boolean getDrinking() {
        return drinking;
    }

    /**
     * 
     * @param drinking
     *     The drinking
     */
    public void setDrinking(Boolean drinking) {
        this.drinking = drinking;
    }

    /**
     * 
     * @return
     *     The smoking
     */
    public Boolean getSmoking() {
        return smoking;
    }

    /**
     * 
     * @param smoking
     *     The smoking
     */
    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    /**
     * 
     * @return
     *     The nonveg
     */
    public Boolean getNonveg() {
        return nonveg;
    }

    /**
     * 
     * @param nonveg
     *     The nonveg
     */
    public void setNonveg(Boolean nonveg) {
        this.nonveg = nonveg;
    }

    /**
     * 
     * @return
     *     The gardianentry
     */
    public Object getGardianentry() {
        return gardianentry;
    }

    /**
     * 
     * @param gardianentry
     *     The gardianentry
     */
    public void setGardianentry(Object gardianentry) {
        this.gardianentry = gardianentry;
    }

    /**
     * 
     * @return
     *     The lift
     */
    public Boolean getLift() {
        return lift;
    }

    /**
     * 
     * @param lift
     *     The lift
     */
    public void setLift(Boolean lift) {
        this.lift = lift;
    }

    /**
     * 
     * @return
     *     The gaspipeline
     */
    public Boolean getGaspipeline() {
        return gaspipeline;
    }

    /**
     * 
     * @param gaspipeline
     *     The gaspipeline
     */
    public void setGaspipeline(Boolean gaspipeline) {
        this.gaspipeline = gaspipeline;
    }

    /**
     * 
     * @return
     *     The gym
     */
    public Boolean getGym() {
        return gym;
    }

    /**
     * 
     * @param gym
     *     The gym
     */
    public void setGym(Boolean gym) {
        this.gym = gym;
    }

    /**
     * 
     * @return
     *     The swimmingpool
     */
    public Boolean getSwimmingpool() {
        return swimmingpool;
    }

    /**
     * 
     * @param swimmingpool
     *     The swimmingpool
     */
    public void setSwimmingpool(Boolean swimmingpool) {
        this.swimmingpool = swimmingpool;
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

}
