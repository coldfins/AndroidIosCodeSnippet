package com.housing.housecheap.utility;

public class GetUrl {
	public String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GetUrl() {

		// url="http://54.69.148.36:5000/api";
		//http://192.168.2.119:3000/api_list
		//url = "http://aerosky.in:5000/api";

		//url="http://192.168.2.119:3000/api_list";

		//url="http://13.58.33.217:82/api";

		//base url working
		//url="http://192.168.2.147:3000/api";

		//live url
		url="http://13.58.33.217:82/api";

		setUrl(url);
	}

}
