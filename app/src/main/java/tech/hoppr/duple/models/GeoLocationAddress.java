package tech.hoppr.duple.models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class GeoLocationAddress implements CanGeoLocation {
    private Context mContext;
    private Address mAddress;

    public GeoLocationAddress(Context context) {
        mContext = context;
    }

    @Override
    public Address fetchAddress(String location) {
        try {
            mAddress = new FetchAddressAsyncTask(mContext, location).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return mAddress;
    }

    @Override
    public Address fetchReverseAddress(Context context, double lat, double lon) {
        try {
            mAddress = new ReverseFetchAddressAsyncTask(mContext, lat, lon).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return mAddress;
    }

    public static class FetchAddressAsyncTask extends AsyncTask<Void, Void, Address> {
        WeakReference<Context> mContext;
        String mZipCode;

        FetchAddressAsyncTask(Context context, String zipCode) {
            mContext = new WeakReference<>(context);
            mZipCode = zipCode;
        }

        @Override
        protected Address doInBackground(Void... voids) {
            List<Address> addressList;

            Geocoder geocoder = new Geocoder(mContext.get());
            Address address = null;

            try {
                addressList = geocoder.getFromLocationName(mZipCode, 1);
                address = addressList.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return address;
        }
    }

    public static class ReverseFetchAddressAsyncTask extends AsyncTask<Void, Void, Address> {
        WeakReference<Context> mContext;
        double mLat;
        double mLon;

        ReverseFetchAddressAsyncTask(Context context, double lat, double lon) {
            mContext = new WeakReference<>(context);
            this.mLat = lat;
            this.mLon = lon;
        }

        @Override
        protected Address doInBackground(Void... voids) {
            List<Address> addresses;
            Geocoder geocoder = new Geocoder(mContext.get(), Locale.getDefault());
            Address address = null;
            try {
                addresses = geocoder.getFromLocation(mLat, mLon, 1);
                address = addresses.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return address;
        }
    }
}
