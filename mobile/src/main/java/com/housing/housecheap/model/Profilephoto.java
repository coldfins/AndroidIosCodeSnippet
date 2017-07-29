
package com.housing.housecheap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profilephoto {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("big")
    @Expose
    private Big big;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("thumb150")
    @Expose
    private Thumb150 thumb150;
    @SerializedName("thumb175")
    @Expose
    private Thumb175 thumb175;
    @SerializedName("thumb200")
    @Expose
    private Thumb200 thumb200;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The big
     */
    public Big getBig() {
        return big;
    }

    /**
     * 
     * @param big
     *     The big
     */
    public void setBig(Big big) {
        this.big = big;
    }

    /**
     * 
     * @return
     *     The medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    /**
     * 
     * @return
     *     The thumb150
     */
    public Thumb150 getThumb150() {
        return thumb150;
    }

    /**
     * 
     * @param thumb150
     *     The thumb150
     */
    public void setThumb150(Thumb150 thumb150) {
        this.thumb150 = thumb150;
    }

    /**
     * 
     * @return
     *     The thumb175
     */
    public Thumb175 getThumb175() {
        return thumb175;
    }

    /**
     * 
     * @param thumb175
     *     The thumb175
     */
    public void setThumb175(Thumb175 thumb175) {
        this.thumb175 = thumb175;
    }

    /**
     * 
     * @return
     *     The thumb200
     */
    public Thumb200 getThumb200() {
        return thumb200;
    }

    /**
     * 
     * @param thumb200
     *     The thumb200
     */
    public void setThumb200(Thumb200 thumb200) {
        this.thumb200 = thumb200;
    }

}
