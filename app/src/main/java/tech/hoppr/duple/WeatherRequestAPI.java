package tech.hoppr.duple;

import android.content.Context;
import android.location.Address;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherRequestAPI{
    private Context mContext;
    private CanGeoLocation mGeoLocation;
    private Address mAddress;
    private String mZipcode = "10002";

    public WeatherRequestAPI(Context context, GeoLocationAddress location) {
        this.mContext = context;
        this.mGeoLocation = location;

        mAddress = mGeoLocation.fetchAddress(mZipcode);
    }

    public WeatherRequestAPI(Context context, GeoLocationAddress location, String zipcode) {
        this.mContext = context;
        this.mGeoLocation = location;
        this.mZipcode = zipcode;
        mAddress = mGeoLocation.fetchAddress(mZipcode);
    }

    final private String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/onecall";

    final private String PARAM_QUERY = "?";
    final private String LATITUDE = "lat";
    final private String LONGITUDE = "lon";
    final private String EXCLUDE = "exclude";
    final private String APPID = "appid";

    final private String API = "17ebb4f754fbdd243a6291f9ade686eb";
    final private String exclusions = "hourly,minutely";


    public URL buildUrl() {
        Uri builtUri = Uri.parse(WEATHER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, mZipcode)
                .appendQueryParameter(LATITUDE, String.valueOf(mAddress.getLatitude()))
                .appendQueryParameter(LONGITUDE, String.valueOf(mAddress.getLongitude()))
                .appendQueryParameter(EXCLUDE, exclusions)
                .appendQueryParameter(APPID, API)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
