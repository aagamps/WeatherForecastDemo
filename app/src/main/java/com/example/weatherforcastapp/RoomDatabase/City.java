package com.example.weatherforcastapp.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.weatherforcastapp.model.Coord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class City {

    @PrimaryKey
    @ColumnInfo(name = "index")
    @SerializedName("index")
    @Expose
    public Integer index;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    public Integer id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String name;

    @ColumnInfo(name = "country")
    @SerializedName("country")
    @Expose
    public String country;

    @Ignore
    @SerializedName("coord")
    @Expose
    public Coord coord;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
