package com.example.weatherforcastapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforcastapp.R;
import com.example.weatherforcastapp.Utility.Network;
import com.example.weatherforcastapp.adapters.CityListAdapter;
import com.example.weatherforcastapp.adapters.HourlyWeatherAdapter;
import com.example.weatherforcastapp.api.ApiInterface;
import com.example.weatherforcastapp.api.RetrofitClient;
import com.example.weatherforcastapp.model.HourlyResponse;
import com.example.weatherforcastapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherDetailsActivity extends AppCompatActivity {

    private int cityId = 0;
    ProgressDialog progressDoalog;
    private Network network;
    private static String TAG = "WeatherDetailsActivity";
    private WeatherResponse weatherResponse;
    private HourlyResponse hourlyResponse;
    private RecyclerView rvhourlyTemp;
    private HourlyWeatherAdapter adapter;

    private TextView cityName, temp, weatherMain, weatherDescripton, pressure, humidity, tempUp, tempDown, windSpeed, windDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        getSupportActionBar().hide();
        network = new Network(this);
        Intent intent = getIntent();
        cityId = intent.getIntExtra("cityId", 0);

        initialize();

        if (cityId > 0) {
            getWeatherData();
        }

    }

    private void initialize() {

        cityName = findViewById(R.id.cityName);
        temp = findViewById(R.id.temp);
        weatherMain = findViewById(R.id.weatherMain);
        weatherDescripton = findViewById(R.id.weatherDescripton);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        tempUp = findViewById(R.id.tempUp);
        tempDown = findViewById(R.id.tempDown);
        windSpeed = findViewById(R.id.windSpeed);
        windDegree = findViewById(R.id.windDegree);

        rvhourlyTemp = findViewById(R.id.rvhourlyTemp);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvhourlyTemp.setLayoutManager(llm);


    }

    private void getWeatherData() {

        if (network.isNetworkAvailable()) {

            progressDoalog = new ProgressDialog(WeatherDetailsActivity.this);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();

            getWeatherDetails();

            getHourlyWeatherDetails();

        } else {
            Toast.makeText(this, "Netwrok is not Available", Toast.LENGTH_LONG).show();
        }


    }

    private void getWeatherDataSet() {

        cityName.setText(weatherResponse.getName());
        temp.setText(weatherResponse.getMain().getTemp().toString() + "°");
        for (int i = 0; i < weatherResponse.getWeather().size(); i++) {
            weatherMain.setText(weatherResponse.getWeather().get(i).getMain());
            weatherDescripton.setText(weatherResponse.getWeather().get(i).getDescription());
        }
        pressure.setText(weatherResponse.getMain().getPressure().toString() + " Pa");
        humidity.setText(weatherResponse.getMain().getHumidity().toString());
        tempUp.setText(weatherResponse.getMain().getTempMax().toString() + "°");
        tempDown.setText(weatherResponse.getMain().getTempMin().toString() + "°");
        windSpeed.setText(weatherResponse.getWind().getSpeed().toString() + " km/h");
        windDegree.setText(weatherResponse.getWind().getDeg().toString() + "°");


    }

    private void getWeatherDetails() {


        ApiInterface apiService = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<WeatherResponse> service = apiService.getWeatherStatusById(cityId, "b6907d289e10d714a6e88b30761fae22");
        service.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {

                weatherResponse = response.body();
                Log.i(TAG, "Responce= " + weatherResponse);

                if (weatherResponse != null) {
                    getWeatherDataSet();
                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getHourlyWeatherDetails() {

        ApiInterface apiService = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<HourlyResponse> service = apiService.getWeatherStatusHourly(cityId, "b6907d289e10d714a6e88b30761fae22");
        service.enqueue(new Callback<HourlyResponse>() {

            @Override
            public void onResponse(Call<HourlyResponse> call, retrofit2.Response<HourlyResponse> response) {

                progressDoalog.dismiss();
                hourlyResponse = response.body();
                Log.i(TAG, "Responce= " + hourlyResponse);

                if (hourlyResponse != null) {
                    adapter = new HourlyWeatherAdapter(WeatherDetailsActivity.this, hourlyResponse.getList());
                    rvhourlyTemp.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<HourlyResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(WeatherDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();

            }
        });

    }
}
