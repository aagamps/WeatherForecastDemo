package com.example.weatherforcastapp.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    public static final String BASE_URL = "https://samples.openweathermap.org";
//    public static final String BASE_URL3 = "https://reqres.in/api/users?page=2";

    private static String TAG = "RetrofitClient";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
