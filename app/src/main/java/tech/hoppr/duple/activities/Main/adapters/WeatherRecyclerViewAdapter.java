package tech.hoppr.duple.activities.Main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.DayOfWeek;
import java.util.ArrayList;

import tech.hoppr.duple.models.DailyWeather;
import tech.hoppr.duple.R;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {
    private Context mContext;
    private ArrayList<DailyWeather> mDailyWeatherList;

    public WeatherRecyclerViewAdapter(Context context, ArrayList<DailyWeather> dailyWeatherList) {
        this.mContext = context;
        this.mDailyWeatherList = dailyWeatherList;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public TextView mDayText;
        public ImageView mWeatherIcon;
        public TextView mMinTemp;
        public TextView mMaxTemp;
        public TextView mCurrentDay;

        @SuppressLint("CutPasteId")
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            mDayText = itemView.findViewById(R.id.current_day_main);
            mWeatherIcon = itemView.findViewById(R.id.weather_icon);
            mMinTemp = itemView.findViewById(R.id.min_temp);
            mMaxTemp = itemView.findViewById(R.id.max_temp);
            mCurrentDay = itemView.findViewById(R.id.current_day);
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.weather_data_list_item, parent, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        DailyWeather currentDay = mDailyWeatherList.get(position);

        String weatherIcon = currentDay.getmImageUrl();
        int minTemp = currentDay.getmMinTemp();
        int maxTemp = currentDay.getmMaxTemp();
        DayOfWeek currDay = currentDay.getmWeekDay();
        holder.mMinTemp.setText(String.format("%s°", minTemp));
        holder.mMaxTemp.setText(String.format("%s°", maxTemp));
        holder.mCurrentDay.setText(String.valueOf(currDay));

        Picasso.get().load(weatherIcon).fit().centerInside().into(holder.mWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return mDailyWeatherList.size();
    }
}
