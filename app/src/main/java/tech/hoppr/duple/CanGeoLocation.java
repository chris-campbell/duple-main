package tech.hoppr.duple;

import android.content.Context;
import android.location.Address;

public interface CanGeoLocation {
    Address fetchAddress(String Location);
    Address fetchReverseAddress(Context context, double lat, double lon);
}
