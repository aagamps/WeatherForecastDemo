package com.example.weatherforcastapp.api;

import com.example.weatherforcastapp.model.HourlyResponse;
import com.example.weatherforcastapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeatherStatusById(@Query("id") long cityId, @Query("appid") String appid);

    @GET("/data/2.5/forecast/hourly")
    Call<HourlyResponse> getWeatherStatusHourly(@Query("id") long cityId, @Query("appid") String appid);


}
