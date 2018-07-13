package com.hit.bubbl.savexmlapp;

/**
 * @author Bubbles
 * @create 2018/7/13
 * @Describe
 */
public class SmsInfo {
    private int id;
    private long date;
    private String body;
    private String address;
    public SmsInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    public SmsInfo(int id, long date, String body, String address) {
        super();
        this.id = id;
        this.date = date;
        this.body = body;
        this.address = address;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}