
package com.housing.housecheap.model;
import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PropertyPanorama implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pano_url")
    @Expose
    private String panoUrl;

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
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The panoUrl
     */
    public String getPanoUrl() {
        return panoUrl;
    }

    /**
     * 
     * @param panoUrl
     *     The pano_url
     */
    public void setPanoUrl(String panoUrl) {
        this.panoUrl = panoUrl;
    }

}
