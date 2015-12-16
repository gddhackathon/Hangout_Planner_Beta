package utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation {

    private static final String TAG = "GeocodingLocation";

    public static void getLocationFromAddress(final String locationAddress,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List
                            addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address)addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append(":");
                        sb.append(address.getLongitude());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", locationAddress);
                        bundle.putString("latLon", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", locationAddress);
                        bundle.putString("latLon","Unable to get Latitude and Longitude for this address location.");
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        final USStateCode usStateCode = new USStateCode();
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder currentAddress = new StringBuilder();
                        /*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            System.out.println("address.getAddressLine(i) = " + address.getAddressLine(i));
                            sb.append(address.getAddressLine(i)).append("\n");
                        }*/
                        System.out.println("address.getLocality() = " + address.getLocality());
                        System.out.println("address.getSubLocality() = " + address.getSubLocality());
                        System.out.println("address.getAdminArea() = " + address.getAdminArea());
                        System.out.println("address.getCountryCode() = " + address.getCountryCode());
                        System.out.println("address.getCountryName() = " + address.getCountryName());
                        System.out.println("address.getFeatureName() = " + address.getFeatureName());
                        System.out.println("address.getPhone() = " + address.getPhone());
                        System.out.println("address.getPostalCode() = " + address.getPostalCode());
                        System.out.println("address.getPremises() = " + address.getPremises());
                        System.out.println("address.getSubAdminArea() = " + address.getSubAdminArea());
                        System.out.println("address.getSubThoroughfare() = " + address.getSubThoroughfare());
                        System.out.println("address.getThoroughfare() = " + address.getThoroughfare());
                        System.out.println("address.describeContents() = " + address.describeContents());
                        System.out.println("address.getURL() = " + address.getUrl());
                        if(null != address.getSubThoroughfare() && !address.getSubThoroughfare().isEmpty()){
                            currentAddress.append(address.getSubThoroughfare()).append(" ");
                        }
                        if(null != address.getThoroughfare() && !address.getThoroughfare().isEmpty()){
                            currentAddress.append(address.getThoroughfare()).append(",");
                        }
                        if(null != address.getLocality() && !address.getLocality().isEmpty()){
                            currentAddress.append(address.getLocality()).append(",");
                        }
                        if(null != address.getAdminArea() && !address.getAdminArea().isEmpty()){
                            currentAddress.append(usStateCode.getStateCodeMapping().get(address.getAdminArea().trim().toLowerCase())).append(",");
                        }
                        if(null != address.getCountryName() && !address.getCountryName().isEmpty()){
                            currentAddress.append(address.getCountryName());
                        }
                        result = currentAddress.toString();
                        System.out.println("result = " + result);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
