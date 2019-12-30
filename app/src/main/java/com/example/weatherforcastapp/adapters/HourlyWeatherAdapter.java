package com.example.weatherforcastapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforcastapp.R;
import com.example.weatherforcastapp.RoomDatabase.City;
import com.example.weatherforcastapp.activities.WeatherDetailsActivity;
import com.example.weatherforcastapp.model.HourlyList;
import com.example.weatherforcastapp.model.HourlyResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private Context context;
    private List<HourlyList> hourlyResponseList;
    private String iconName = "";

    public HourlyWeatherAdapter(Context context, List<HourlyList> hourlyResponseList) {
        this.context = context;
        this.hourlyResponseList = hourlyResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HourlyList hourlyResponse = hourlyResponseList.get(position);

        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("HH:mm");
            Date date = input.parse(hourlyResponse.getDtTxt());
            String time = output.format(date);

            holder.temp.setText(hourlyResponse.getMain().getTemp().toString() + "°");
            holder.hour.setText(time);

            for (int i = 0; i < hourlyResponse.getWeatherList().size(); i++) {
                iconName = hourlyResponse.getWeatherList().get(i).getIcon();
                holder.weather.setText(hourlyResponse.getWeatherList().get(i).getMain());

            }
            String uri = "@drawable/icon" + iconName;
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.icon.setImageDrawable(res);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return hourlyResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cvHourlyWeather;
        private TextView temp, hour, weather;
        private ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvHourlyWeather = itemView.findViewById(R.id.cvHourlyWeather);
            temp = itemView.findViewById(R.id.temp);
            hour = itemView.findViewById(R.id.hour);
            icon = itemView.findViewById(R.id.icon);
            weather = itemView.findViewById(R.id.weather);
        }
    }
}
