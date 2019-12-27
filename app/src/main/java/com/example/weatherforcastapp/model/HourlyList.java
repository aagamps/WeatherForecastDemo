package com.example.weatherforcastapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyList {


    @SerializedName("dt")
    @Expose
    public Integer dt;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("weather")
    @Expose
    public List<Weather> weatherList;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
