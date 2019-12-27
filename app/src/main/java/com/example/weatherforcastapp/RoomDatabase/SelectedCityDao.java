package com.example.weatherforcastapp.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface SelectedCityDao {


    @Query("SELECT * FROM selectedCity")
    List<SelectedCity> getAll();

    @Query("DELETE FROM selectedCity")
    void deleteAll();

    @Insert
    void insert(SelectedCity selectedCity);

    @Delete
    void delete(SelectedCity selectedCity);

    @Update
    void update(SelectedCity selectedCity);



}
