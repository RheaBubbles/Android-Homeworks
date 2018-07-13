package com.hit.bubbl.weatherapp;

/**
 * @author Bubbles
 * @create 2018/7/13
 * @Describe
 */
public class WeatherInfo {
    private int id;
    private String temp;
    private String weather;
    private String wind;
    private String name;
    private String pm;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWind() {
        return wind;
    }
    public void setWind(String wind) {
        this.wind = wind;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPm() {
        return pm;
    }
    public void setPm(String pm) {
        this.pm = pm;
    }
    @Override
    public String toString() {
        return "[id=" + id + ", 温度=" + temp + ", 天气=" + weather + ", 风力="
                + wind + ", 城市=" + name + ", pm2.5=" + pm + "]";
    }
}
