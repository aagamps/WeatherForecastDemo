package com.example.weatherforcastapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyResponse {


    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public Float message;

    @SerializedName("cnt")
    @Expose
    public Integer cnt;


    @SerializedName("list")
    @Expose
    public List<HourlyList> list = null;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Float getMessage() {
        return message;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<HourlyList> getList() {
        return list;
    }

    public void setList(List<HourlyList> list) {
        this.list = list;
    }
}