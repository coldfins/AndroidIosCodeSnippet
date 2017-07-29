package com.medical.utils;

public class GetAllUrl {
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GetAllUrl() {
		// url="http://192.168.2.32:8888/NearBy/";
//		url = "http://192.168.2.153:88/api/DocAppSysAPI/";
//		url = "http://52.39.2.103:81/api/DocAppSysAPI/";


		url = "http://34.212.127.62:82/api/DocAppSysAPI/";       // Server Url
		setUrl(url);
	}

}
