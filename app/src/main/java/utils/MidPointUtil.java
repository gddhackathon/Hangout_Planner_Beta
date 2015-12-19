package utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.Geometry;

/**
 * Created by AchsahSiri on 12/18/2015.
 */
public class MidPointUtil {

    public String getLocation(Geometry geometry1,Geometry geometry2, int weightage1stPersopn, int  weightage2stPersopn, Context context) {
        StringBuilder midAddress = new StringBuilder();
        double chicagolat1 = Double.parseDouble(geometry1.getCoOrdinate().getLat()) * Math.PI / 180;
        double chicagolon1 = Double.parseDouble(geometry1.getCoOrdinate().getLon()) * Math.PI / 180;

        double newyorklat1 = Double.parseDouble(geometry2.getCoOrdinate().getLat()) * Math.PI / 180;
        double newYorklon1 = Double.parseDouble(geometry2.getCoOrdinate().getLon()) * Math.PI / 180;

        double X1 = Math.cos(chicagolat1) * Math.cos(chicagolon1);
        double Y1 = Math.cos(chicagolat1) * Math.sin(chicagolon1);
        double Z1 = Math.sin(chicagolat1);

        double X2 = Math.cos(newyorklat1) * Math.cos(newYorklon1);
        double Y2 = Math.cos(newyorklat1) * Math.sin(newYorklon1);
        double Z2 = Math.sin(newyorklat1);

        double X3 = ((X1 * weightage1stPersopn) + (X2 * weightage2stPersopn));
        double Y3 = ((Y1 * weightage1stPersopn) + (Y2 * weightage2stPersopn));
        double Z3 = ((Z1 * weightage1stPersopn) + (Z2 * weightage2stPersopn));

        double Lon = Math.atan2(Y3, X3);
        double Hyp = Math.sqrt(X3 * X3 + Y3 * Y3);
        double Lat = Math.atan2(Z3, Hyp);

        double latInDec = Lat * 180 / Math.PI;
        double lonInDec = Lon * 180 / Math.PI;

        System.out.println("latInDec = " + latInDec);
        System.out.println("lonInDec = " + lonInDec);

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;

        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latInDec, lonInDec, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder currentAddress = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    //System.out.println("address.getAddressLine(i) = " + address.getAddressLine(i));
                    currentAddress.append(address.getAddressLine(i)).append(" ");
                }
                System.out.println("currentAddress = " + currentAddress);
                midAddress.append(currentAddress).append(" ");
            }
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
        return midAddress.append(":").append(latInDec).append(":").append(lonInDec).toString();
    }
}
