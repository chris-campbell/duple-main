package tech.hoppr.duple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // widgets
    private RequestQueue mRequestQueue;
    private WeatherRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mCityLocation;
    private TextView mShortWeatherDesc;
    private TextView mCurrentWeatherTemp;
    private GeoLocationAddress mGeoLocation;
    private ImageView mWeatherIcon;
    private DayOfWeek mCurrentDay;
    private TextView mOpenDialog;
    private String zipValue;
    private String weatherUnit;
    WeatherRequestAPI mWeatherRequestAPI;

    // vars
    private String iconBaseUrl = "https://openweathermap.org/img/wn/";
    private ArrayList<DailyWeather> mWeatherDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityLocation = findViewById(R.id.city_location);
        mRecyclerView = findViewById(R.id.weather_data_recycler_view);
        mCurrentWeatherTemp = findViewById(R.id.temperature_digit);
        mShortWeatherDesc = findViewById(R.id.short_weather_description);
        mWeatherIcon = findViewById(R.id.weather_image_icon);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue = Volley.newRequestQueue(this);
        mGeoLocation = new GeoLocationAddress(this);

    }

    @Override
    protected void onStart() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        zipValue = prefs.getString("zip_code_entry", "11203");
        weatherUnit = prefs.getString("temp_unit", "fahrenheit");

        mWeatherRequestAPI = getWeatherRequestAPI();
        URL mUrl = mWeatherRequestAPI.buildUrl();
        parseJson(mUrl);
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private WeatherRequestAPI getWeatherRequestAPI() {
        WeatherRequestAPI mWeatherRequestAPI;
        if (zipValue != null) {
            mWeatherRequestAPI = new WeatherRequestAPI(this, mGeoLocation, zipValue);
        } else {
            mWeatherRequestAPI = new WeatherRequestAPI(this, mGeoLocation);
        }
        return mWeatherRequestAPI;
    }


    private void parseJson(URL url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url.toString(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Sets up current weather data
                    currentWeatherData(response);

                    // Setup daily weather data and set to recycler view adapter
                    dailyWeatherData(response);

                    mAdapter = new WeatherRecyclerViewAdapter(MainActivity.this, mWeatherDataList);
                    mRecyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
        mWeatherDataList.clear();
    }


    private void setCurrentWeatherData(String currentWeatherDesc, double currentTemp, Address countyLocation, String currentWeatherIcon) {
        String url;

        mCurrentWeatherTemp.setText(String.valueOf((int) currentTemp));

        mCityLocation.setText(stringAfterFirstComma(countyLocation.getAddressLine(0)));
        mShortWeatherDesc.setText(
                String.format("%s%s", currentWeatherDesc.substring(0, 1).toUpperCase(),
                        currentWeatherDesc.substring(1)));
        url = String.format("%s%s@2x.png", iconBaseUrl, currentWeatherIcon);
        Picasso.get().load(url).fit().centerInside().into(mWeatherIcon);
    }

    private void currentWeatherData(JSONObject response) throws JSONException {
        double currentTemp = response.getJSONObject("current").getDouble("temp");
        int formatedTemp;
        double lat = response.getDouble("lat");
        double lon = response.getDouble("lon");
        Address countyLocation = mGeoLocation.fetchReverseAddress(this, lat, lon);
        JSONArray currentWeatherData;
        String currentWeatherIcon;
        String currentWeatherDesc;

        if (weatherUnit.equals("fahrenheit")) {
            formatedTemp = (int) kelvinToFahrenheit(currentTemp);
        } else {
            formatedTemp = (int) kelvinToCelsius(currentTemp);
        }

        currentWeatherData = response.getJSONObject("current").getJSONArray("weather");
        currentWeatherDesc = currentWeatherData.getJSONObject(0).getString("description");
        currentWeatherIcon = currentWeatherData.getJSONObject(0).getString("icon");
        setCurrentWeatherData(currentWeatherDesc, formatedTemp, countyLocation, currentWeatherIcon);
    }

    private void dailyWeatherData(JSONObject response) throws JSONException {
        int maxTemp = 0;
        int minTemp = 0;
        String description;
        String imageUrl = null;
        String icon;
        JSONObject dayData;
        JSONObject temp;
        Calendar calendar = Calendar.getInstance();

        JSONArray dailyWeatherList = response.getJSONArray("daily");

        for (int d = 0; d < dailyWeatherList.length(); d++) {
            dayData = dailyWeatherList.getJSONObject(d);
            temp = dayData.getJSONObject("temp");

            if (weatherUnit.equals("fahrenheit")) {
                minTemp = (int) kelvinToFahrenheit(temp.getDouble("min"));
                maxTemp = (int) kelvinToFahrenheit(temp.getDouble("max"));
            } else {
                minTemp = (int) kelvinToCelsius(temp.getDouble("min"));
                maxTemp = (int) kelvinToCelsius(temp.getDouble("max"));
            }

            description = dayData.getJSONArray("weather").getJSONObject(0).getString("description");
            icon = dayData.getJSONArray("weather").getJSONObject(0).getString("icon");
            imageUrl = String.format("%s%s@2x.png", iconBaseUrl, icon);
            formatDate(calendar, d);
            mWeatherDataList.add(new DailyWeather(minTemp, maxTemp, description, imageUrl, mCurrentDay));
        }

    }

    private void formatDate(Calendar calendar, int d) {
        String simpleFormat = new SimpleDateFormat("EEEE").
                format(calendar.getTime());

        DayOfWeek dayOfWeek = DayOfWeek.valueOf(simpleFormat.toUpperCase());
        mCurrentDay = dayOfWeek.plus(d);
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9 / 5 + 32;
    }

    private String stringAfterFirstComma(String str) {
        return str.substring(str.indexOf(",") + 1, str.length());
    }
}