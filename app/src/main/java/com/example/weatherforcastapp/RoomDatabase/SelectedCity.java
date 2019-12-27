package com.example.weatherforcastapp.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class SelectedCity {

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
}
