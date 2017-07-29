
package com.housing.housecheap.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPropertyByIdUser {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("p_rate")
    @Expose
    private String pRate;
    @SerializedName("property_details")
    @Expose
    private List<PropertyDetailUser> propertyDetailsUser = new ArrayList<PropertyDetailUser>();
    @SerializedName("propertyphotos")
    @Expose
    private List<String> propertyphotos = new ArrayList<String>();
    @SerializedName("property_panorama")
    @Expose
    private List<PropertyPanorama> propertyPanorama = new ArrayList<PropertyPanorama>();
    @SerializedName("propertyamenity")
    @Expose
    private PropertyamenityUser propertyamenity;
    @SerializedName("similar_properties")
    @Expose
    private List<SimilarPropertyUser> similarProperties = new ArrayList<SimilarPropertyUser>();

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * @return
     *     The pRate
     */
    public String getPRate() {
        return pRate;
    }

    /**
     * 
     * @param pRate
     *     The p_rate
     */
    public void setPRate(String pRate) {
        this.pRate = pRate;
    }

    /**
     * 
     * @return
     *     The propertyDetails
     */
    public List<PropertyDetailUser> getPropertyDetailsUser() {
        return propertyDetailsUser;
    }

    /**
     * 
     * @param propertyDetails
     *     The property_details
     */
    public void setPropertyDetailsUser(List<PropertyDetailUser> propertyDetailsUser) {
        this.propertyDetailsUser = propertyDetailsUser;
    }

    /**
     * 
     * @return
     *     The propertyphotos
     */
    public List<String> getPropertyphotos() {
        return propertyphotos;
    }

    /**
     * 
     * @param propertyphotos
     *     The propertyphotos
     */
    public void setPropertyphotos(List<String> propertyphotos) {
        this.propertyphotos = propertyphotos;
    }

    /**
     * 
     * @return
     *     The propertyPanorama
     */
    public List<PropertyPanorama> getPropertyPanorama() {
        return propertyPanorama;
    }

    /**
     * 
     * @param propertyPanorama
     *     The property_panorama
     */
    public void setPropertyPanorama(List<PropertyPanorama> propertyPanorama) {
        this.propertyPanorama = propertyPanorama;
    }

    /**
     * 
     * @return
     *     The propertyamenity
     */
    public PropertyamenityUser getPropertyamenity() {
        return propertyamenity;
    }

    /**
     * 
     * @param propertyamenity
     *     The propertyamenity
     */
    public void setPropertyamenity(PropertyamenityUser propertyamenity) {
        this.propertyamenity = propertyamenity;
    }

    /**
     * 
     * @return
     *     The similarProperties
     */
    public List<SimilarPropertyUser> getSimilarProperties() {
        return similarProperties;
    }

    /**
     * 
     * @param similarProperties
     *     The similar_properties
     */
    public void setSimilarProperties(List<SimilarPropertyUser> similarProperties) {
        this.similarProperties = similarProperties;
    }

}
