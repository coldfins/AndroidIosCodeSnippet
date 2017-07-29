package com.post.wall.wallpostapp.utility;

public class GetUrlClass {

    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GetUrlClass() {
//        url = "http://192.168.2.121:88/api/WallPostAPI/";    //LOCAL

        url = "http://52.39.2.103:86/api/WallPostAPI/";        //LIVE

        url = "http://34.212.127.62:86/api/WallPostAPI/";        //LIVE





        setUrl(url);
    }


}
