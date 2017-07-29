package com.housing.housecheap.model;

public class searchTabItem {

	int id;
	String itemName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public searchTabItem(int id, String itemName) {
		super();
		this.id = id;
		this.itemName = itemName;
	}

	public searchTabItem() {
		super();
	}

}
