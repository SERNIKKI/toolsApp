package com.nikki.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rhesis {
    private int    id;
    private String rhesis_sentence;
    private String rhesis_writer;
    private String rhesis_book;
    private String inputtime;
    private String url;


    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getRhesis_sentence() {
        return rhesis_sentence;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRhesis_sentence(String rhesis_sentence) {
        this.rhesis_sentence = rhesis_sentence;
    }

    public void setRhesis_writer(String rhesis_writer) {
        this.rhesis_writer = rhesis_writer;
    }

    public void setRhesis_book(String rhesis_book) {
        this.rhesis_book = rhesis_book;
    }

    public String getRhesis_writer() {
        return rhesis_writer;
    }

    public String getRhesis_book() {
        return rhesis_book;
    }

    public String getInputtime(){
        return inputtime;
    }

    public void setInputdata(){
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        this.inputtime = sm.format(date);
    }
}
