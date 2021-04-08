package com.nikki.bean;

public class IP {
    //IP地址
    private String IP;
    //国家
    private String country;
    //省份
    private String province;
    //市
    private String city;
    //运营商
    private String isp;

    public IP() {
    }

    public String getIP() {
        return IP;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
    @Override
    public String toString() {
        return "IP{" +
                "IP='" + IP + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", isp='" + isp + '\'' +
                '}';
    }
}
