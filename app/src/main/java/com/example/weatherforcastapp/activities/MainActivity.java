package com.example.weatherforcastapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.weatherforcastapp.R;
import com.example.weatherforcastapp.RoomDatabase.City;
import com.example.weatherforcastapp.RoomDatabase.DatabaseClient;
import com.example.weatherforcastapp.RoomDatabase.SelectedCity;
import com.example.weatherforcastapp.adapters.CityListAdapter;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainAcivity";
    private String json = null;
    private List<City> cityList = new ArrayList<>();
    private List<SelectedCity> selectedCityList = new ArrayList<>();
    private SelectedCity selectedCity;
    private JsonReader jsonReader;
    private RecyclerView rvCityList;
    private CityListAdapter adapter;
    private SpinnerDialog spinnerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Cities");


        //readJSONFromAsset();


        rvCityList = findViewById(R.id.rvCityList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvCityList.setLayoutManager(llm);


        class GetCityList extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                cityList = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .cityDao()
                        .getAll();

                selectedCityList = new ArrayList<>();
                selectedCityList = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .selectedCityDao()
                        .getAll();


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (cityList.size() > 0) {

                    ArrayList<String> cityNames = new ArrayList<>();

                    for (int i = 0; i < cityList.size(); i++) {
                        cityNames.add(cityList.get(i).getName());
                    }

                    getSpinnerDialog(cityNames);

                }

                if (selectedCityList.size() > 0) {

                    adapter = new CityListAdapter(MainActivity.this, selectedCityList);
                    rvCityList.setAdapter(adapter);

                }


            }
        }

        GetCityList getCityList = new GetCityList();
        getCityList.execute();


    }

    private void getSpinnerDialog(ArrayList<String> cityNames) {

        spinnerDialog = new SpinnerDialog(MainActivity.this, cityNames, "Select Cities");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                Toast.makeText(MainActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < cityList.size(); i++) {
                    if (item.equals(cityList.get(i).name)) {

                        selectedCity = new SelectedCity();
                        selectedCity.setId(cityList.get(i).getId());
                        selectedCity.setName(item);

                        AddData addData = new AddData();
                        addData.execute();
                        break;
                    }
                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);

        MenuItem item = menu.findItem(R.id.addButton);

        if (item != null) {

            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if (spinnerDialog != null) {
                        spinnerDialog.showSpinerDialog();
                    }
                    return false;
                }
            });
        }
        return true;
    }

    public void readJSONFromAsset() {
        try {
            InputStream is = getResources().openRawResource(R.raw.citylist_0);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.e(TAG, "readJSONFromAsset: " + json);
            json.trim();
            cityList = getResultList(City.class);
            Log.e(TAG, "readJSONFromAsset: " + cityList);

            SaveData saveData = new SaveData();
            saveData.execute();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class SaveData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            for (int i = 0; i < cityList.size(); i++) {

                City city = cityList.get(i);
                DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .cityDao()
                        .insert(city);
            }


            return null;
        }
    }

    class AddData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .selectedCityDao()
                    .insert(selectedCity);

            selectedCityList = new ArrayList<>();
            selectedCityList = DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .selectedCityDao()
                    .getAll();


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (selectedCityList.size() > 0) {

                adapter = new CityListAdapter(MainActivity.this, selectedCityList);
                rvCityList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }
    }


    public <T> T getResult(Class<T> type) {
        return new Gson().fromJson(json, type);

    }

    public <T> List<T> getResultList(Class<T> type) {
        List<T> arrayList = getResult(ArrayList.class);
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < arrayList.size(); i++) {
            String jsonString = new Gson().toJson(arrayList.get(i));
            list.add(new Gson().fromJson(jsonString, type));
        }
        return list;

    }

}
