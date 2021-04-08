package com.nikki.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUser {
    private String only_name;
    private String user_account;
    private String user_password;
    private String user_avatar;
    private String input_time;

    public AppUser() {
    }

    public String getOnly_name() {
        return only_name;
    }

    public String getUser_account() {
        return user_account;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public String getInput_time() {
        return input_time;
    }

    public void setOnly_name(String only_name) {
        this.only_name = only_name;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public void setInput_time() {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        this.input_time = sm.format(date);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "only_name='" + only_name + '\'' +
                ", user_account='" + user_account + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_avatar='" + user_avatar + '\'' +
                ", input_time='" + input_time + '\'' +
                '}';
    }
}
