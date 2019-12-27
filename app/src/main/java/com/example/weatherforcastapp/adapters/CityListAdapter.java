package com.example.weatherforcastapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforcastapp.R;
import com.example.weatherforcastapp.RoomDatabase.AppDatabase;
import com.example.weatherforcastapp.RoomDatabase.DatabaseClient;
import com.example.weatherforcastapp.RoomDatabase.SelectedCity;
import com.example.weatherforcastapp.activities.WeatherDetailsActivity;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private Context context;
    private List<SelectedCity> cityList;

    public CityListAdapter(Context context, List<SelectedCity> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SelectedCity city = cityList.get(position);

        holder.tvCityName.setText(city.getName());

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, WeatherDetailsActivity.class);
                intent.putExtra("cityId", city.getId());
                context.startActivity(intent);


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class DeleteCity extends AsyncTask<SelectedCity, Void, Void> {


                    @Override
                    protected Void doInBackground(SelectedCity... selectedCities) {

                        DatabaseClient.getInstance(context.getApplicationContext())
                                .getAppDatabase()
                                .selectedCityDao()
                                .delete(city);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        cityList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cityList.size());
                    }
                }

                DeleteCity deleteCity = new DeleteCity();
                deleteCity.execute();
            }
        });

    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cvCityList;
        private TextView tvCityName, next;
        private ImageView icon, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvCityList = itemView.findViewById(R.id.cvCityList);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            icon = itemView.findViewById(R.id.icon);
            delete = itemView.findViewById(R.id.delete);
            next = itemView.findViewById(R.id.next);
        }
    }
}
