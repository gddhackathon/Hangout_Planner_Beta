package utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import model.HangoutPlanner;

/**
 * Created by AchsahSiri on 12/15/2015.
 */
public class CurrentLocationProvider implements LocationListener {

    Context context;
    HangoutPlanner hangoutPlanner;

    public CurrentLocationProvider(Context context){
        this.context = context;
        this.hangoutPlanner = (HangoutPlanner)context.getApplicationContext();
    }

    private LocationManager locationManager;

    public void getCurrentLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        String latitude = null;
        String longitude = null;
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        }
        if (isNetworkEnabled) {
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            }

            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Log.d("activity", "LOC by Network");
                    latitude = Double.toString(location.getLatitude());
                    longitude = Double.toString(location.getLongitude());
                }

            }
        }
        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "RLOC: loc by GPS");
                        latitude = Double.toString(location.getLatitude());
                        longitude = Double.toString(location.getLongitude());
                    }
                }

            }
        }
        hangoutPlanner.setLatLon(latitude+":"+longitude);
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), context, new GeocoderHandler());
    }

    @Override
    public void onLocationChanged(Location location) {/*
        System.out.println("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        hangoutPlanner.setLatLon(latitude+":"+longitude);
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), context, new GeocoderHandler());
    */}

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            System.out.println("locationAddress = " + locationAddress);
            hangoutPlanner.setSelectedAddress(locationAddress);
        }
    }
}
