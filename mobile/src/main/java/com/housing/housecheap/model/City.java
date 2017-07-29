
package com.housing.housecheap.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("state")
    @Expose
    private String state;

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The stateId
     */
    public Integer getStateId() {
        return stateId;
    }

    /**
     * 
     * @param stateId
     *     The state_id
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

	public City(String name) {
		super();
		this.name = name;
	}
    
    
    

}
