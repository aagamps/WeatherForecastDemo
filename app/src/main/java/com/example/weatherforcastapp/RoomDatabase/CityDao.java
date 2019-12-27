package com.example.weatherforcastapp.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface CityDao {


    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("DELETE FROM city")
    void deleteAll();

    @Insert
    void insert(City city);

    @Delete
    void delete(City city);

    @Update
    void update(City city);



}
