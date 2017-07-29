package com.food.eathub.Model;

public class GetUrlofApi {
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GetUrlofApi() {
		super();
		//live
		//url = "http://www.khanado.com/api/KhanaDoApi/";

		// url = "http://52.39.2.103/api/KhanaDoApi/";
		// url = "http://192.168.2.153:888/api/KhanaDoApi/";
		 url = "http://34.212.127.62/api/KhanaDoApi/";

		//http://34.212.127.62/Help
		setUrl(url);
	}

}
