package com.example.weatherforcastapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforcastapp.R;
import com.example.weatherforcastapp.Utility.Network;
import com.example.weatherforcastapp.adapters.CityListAdapter;
import com.example.weatherforcastapp.adapters.HourlyWeatherAdapter;
import com.example.weatherforcastapp.api.ApiInterface;
import com.example.weatherforcastapp.api.RetrofitClient;
import com.example.weatherforcastapp.model.HourlyList;
import com.example.weatherforcastapp.model.HourlyResponse;
import com.example.weatherforcastapp.model.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.weatherforcastapp.R.color.colorPrimaryLight;

public class WeatherDetailsActivity extends AppCompatActivity {

    private int cityId = 0;
    ProgressDialog progressDoalog;
    private Network network;
    private static String TAG = "WeatherDetailsActivity";
    private WeatherResponse weatherResponse;
    private HourlyResponse hourlyResponse;
    private List<HourlyList> todayList = new ArrayList<>();
    private List<HourlyList> tomorrowList = new ArrayList<>();
    private RecyclerView rvhourlyTemp;
    private HourlyWeatherAdapter adapter;
    private Button btnToday, btnTomorrow;
    private String icon;
    private ImageView ivIcon;
    private View viewLine;

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
        btnToday = findViewById(R.id.btnToday);
        btnTomorrow = findViewById(R.id.btnTomorrow);
        ivIcon = findViewById(R.id.ivIcon);
        viewLine = findViewById(R.id.viewLine);
        btnToday.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        rvhourlyTemp = findViewById(R.id.rvhourlyTemp);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvhourlyTemp.setLayoutManager(llm);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayList != null && todayList.size() > 0) {
                    adapter = new HourlyWeatherAdapter(WeatherDetailsActivity.this, todayList);
                    rvhourlyTemp.setAdapter(adapter);
                    btnToday.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                    btnTomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tomorrowList != null && tomorrowList.size() > 0) {
                    adapter = new HourlyWeatherAdapter(WeatherDetailsActivity.this, tomorrowList);
                    rvhourlyTemp.setAdapter(adapter);
                    btnTomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                    btnToday.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });


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
            icon = weatherResponse.getWeather().get(i).getIcon();
        }
        pressure.setText(weatherResponse.getMain().getPressure().toString() + " Pa");
        humidity.setText(weatherResponse.getMain().getHumidity().toString());
        tempUp.setText(weatherResponse.getMain().getTempMax().toString() + "°");
        tempDown.setText(weatherResponse.getMain().getTempMin().toString() + "°");
        windSpeed.setText(weatherResponse.getWind().getSpeed().toString() + " km/h");
        windDegree.setText(weatherResponse.getWind().getDeg().toString() + "°");

        String uri = "@drawable/icon" + icon;
        int imageResource = getResources().getIdentifier(uri, null, this.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        ivIcon.setImageDrawable(res);


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
                    try {
                        Date date;
                        String dateString, firstDate = "", secondDate = "";
                        int j = 0;
                        for (int i = 0; i < hourlyResponse.getList().size(); i++) {
                            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                            date = input.parse(hourlyResponse.getList().get(i).getDtTxt());
                            dateString = output.format(date);
                            if (i == 0) {
                                firstDate = dateString;
                            }
                            if (firstDate.equals(dateString)) {
                                todayList.add(hourlyResponse.getList().get(i));
                            } else if (j == 0) {
                                j = 1;
                                secondDate = dateString;
                            }
                            if (secondDate.equals(dateString)) {
                                tomorrowList.add(hourlyResponse.getList().get(i));
                            }
                        }
                        adapter = new HourlyWeatherAdapter(WeatherDetailsActivity.this, todayList);
                        rvhourlyTemp.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
