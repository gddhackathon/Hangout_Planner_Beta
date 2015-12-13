package com.gdd.hangoutplanner;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import utils.DownloadWeatherInfo;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class WeatherActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
    }

        // Uses AsyncTask to download the XML feed from stackoverflow.com.
    public void invokeRestService(String serviceURL) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            System.out.println("networkInfo available");
            new DownloadWeatherInfo().makeCall(serviceURL);
        } else {
            //textView.setText("No network connection available.");
        }
    }
}
