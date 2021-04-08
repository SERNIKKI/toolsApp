package com.nikki.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Loginer {
    private String only_name;
    private String user_account;
    private String user_password;
    private String avatar;
    private String input_time;

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setInput_time() {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        this.input_time = sm.format(date);
    }

    public String getUser_account() {
        return user_account;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getInput_time() {
        return input_time;
    }

    public Loginer(String only_name, String avatar) {
        this.only_name = only_name;
        this.avatar = avatar;
    }

    public void setOnly_name(String only_name) {
        this.only_name = only_name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOnly_name() {
        return only_name;
    }


    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "Loginer{" +
                "only_name='" + only_name + '\'' +
                ", user_account='" + user_account + '\'' +
                ", user_password='" + user_password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", input_time='" + input_time + '\'' +
                '}';
    }

    public Loginer() {
    }
}
