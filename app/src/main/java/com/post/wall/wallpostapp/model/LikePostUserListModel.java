package com.post.wall.wallpostapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ved_pc on 3/4/2017.
 */

public class LikePostUserListModel {
    int error_code;
    String status;
    String msg;
    ArrayList<User> likeusers;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<User> getLikeusers() {
        return likeusers;
    }

    public void setLikeusers(ArrayList<User> likeusers) {
        this.likeusers = likeusers;
    }
}
