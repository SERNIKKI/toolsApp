package com.nikki.bean;

public class Weather {
    //日期
    private String date;
    //城市
    private String city;
    //农历
    private String moon;
    //周
    private String week;
    //温度
    private String temperature;
    //湿度
    private String humidity;
    //天气
    private String info;
    //更新时间
    private String time;
    //风向
    private String direct;
    //风力
    private String power;
    //天气相关指数
    //运动
    private String yundong;
    //穿衣
    private String chuanyi;
    //过敏
    private String guomin;
    //带伞
    private String daisan;
    //空调
    private String kongtiao;
    //感冒指数
    private String ganmao;
    //紫外线
    private String ziwaixian;
    //舒适度
    private String shushidu;

    public Weather() {
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getMoon() {
        return moon;
    }

    public String getWeek() {
        return week;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getInfo() {
        return info;
    }

    public String getTime() {
        return time;
    }

    public String getDirect() {
        return direct;
    }

    public String getPower() {
        return power;
    }

    public String getYundong() {
        return yundong;
    }

    public String getChuanyi() {
        return chuanyi;
    }

    public String getGuomin() {
        return guomin;
    }

    public String getDaisan() {
        return daisan;
    }

    public String getKongtiao() {
        return kongtiao;
    }

    public String getGanmao() {
        return ganmao;
    }

    public String getZiwaixian() {
        return ziwaixian;
    }

    public String getShushidu() {
        return shushidu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setYundong(String yundong) {
        this.yundong = yundong;
    }

    public void setChuanyi(String chuanyi) {
        this.chuanyi = chuanyi;
    }

    public void setGuomin(String guomin) {
        this.guomin = guomin;
    }

    public void setDaisan(String daisan) {
        this.daisan = daisan;
    }

    public void setKongtiao(String kongtiao) {
        this.kongtiao = kongtiao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public void setZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
    }

    public void setShushidu(String shushidu) {
        this.shushidu = shushidu;
    }

    @Override
    public String toString() {
        return "日期: " + date + '\n' +
                "城市: " + city + '\n' +
                "农历: " + moon + '\n' +
                 week + '\n' +
                "温度: " + temperature + '\n' +
                "湿度: " + humidity + '\n' +
                "天气: " + info + '\n' +
                "更新时间: " + time + '\n' +
                "风向: " + direct + '\n' +
                "风力: " + power + '\n' +
                "运动推荐: " + yundong + '\n' +
                "穿衣推荐: " + chuanyi + '\n' +
                "过敏情况: " + guomin + '\n' +
                "外出带伞: " + daisan + '\n' +
                "空调: " + kongtiao + '\n' +
                "感冒指数: " + ganmao + '\n' +
                "紫外线指数: " + ziwaixian + '\n' +
                "舒适度: " + shushidu + '\n';
    }
}
