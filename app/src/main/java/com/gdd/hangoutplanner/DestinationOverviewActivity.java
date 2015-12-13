package com.gdd.hangoutplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Place;
import model.Weather;
import utils.DownloadGooglePlacesInfo;
import utils.DownloadWeatherInfo;

public class DestinationOverviewActivity extends AppCompatActivity {

    List<GooglePlace> venuesList;
    final String GOOGLE_KEY = "AIzaSyA2pWuAzJ_agDXpISSGDEh1hnk6B7SPMOw";
    final String latitude = "40.7463956";
    final String longtitude = "-73.9852992";

    ArrayAdapter<String> myAdapter;

    ListView placesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String latLon = getIntent().getStringExtra("latLon");

        TextView addressSelectedTextView  = (TextView) findViewById(R.id.textViewTitle);
        addressSelectedTextView.setText(getIntent().getStringExtra("addressSelected"));

        //List to show what is selected
        ListView selectedListView = (ListView) findViewById(R.id.listViewSelectedFav);
        ArrayList<String> selectedChecks = getIntent().getStringArrayListExtra("checkedFavourites");
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,selectedChecks);
        selectedListView.setAdapter(itemsAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String tempURL = getWeatherURL();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Weather weather = DownloadWeatherInfo.makeCall(tempURL);
        System.out.println("icon) = " + weather.getIcon());
        final HashMap<String,List<Place>>  interestVsPlaces = new HashMap<String,List<Place>>();
        for(String interset :selectedChecks) {
            String googlePlacesURL = getUrl(interset);
            System.out.println(googlePlacesURL);
            List<Place> places = DownloadGooglePlacesInfo.makeCall(googlePlacesURL);
            interestVsPlaces.put(interset, places);
        }

        for (Map.Entry<String, List<Place>> places : interestVsPlaces.entrySet()) {
            String interest = places.getKey();
            List<Place> values = places.getValue();
            System.out.println("interest" + interest);
            for(Place place :values){
                System.out.println("place. = " + place.getName());
            }
        }

        //On Click of any list item
        selectedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String item = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplication(), DisplayPlacesActivity.class);
                intent.putExtra("interestVsPlaces", interestVsPlaces);
                startActivity(intent);
            }
        });

        //Loading Weathe Details
        TextView textViewTemp = (TextView) findViewById(R.id.textViewTemp);
        textViewTemp.setText("Right Now " + weather.getTemperature().getCurrent());
        TextView textViewMax = (TextView) findViewById(R.id.textViewMax);
        textViewMax.setText("Max "+weather.getTemperature().getMax());
        TextView textViewMin = (TextView) findViewById(R.id.textViewMin);
        textViewMin.setText("Min "+weather.getTemperature().getMin());
        TextView textViewCondition = (TextView) findViewById(R.id.textViewCondition);
        textViewCondition.setText(weather.getDescription());
        TextView textViewhumidity = (TextView) findViewById(R.id.textViewhumidity);
        textViewhumidity.setText("Humidity"+weather.getHumidity().getValue());
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(weather.getIcon());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception e){

        }
        if (bmp !=null)
            imageView.setImageBitmap(bmp);
    }

    private String getWeatherURL(){
        return "http://api.openweathermap.org/data/2.5/find?lat="+getLat()+"&lon="+getLon()+"&cnt=1&units=Imperial&appid=2de143494c0b295cca9337e1e96b00e0&mode=json";
    }

    private String getLat(){
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return latLon[0];
    }

    private String getLon(){
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return latLon[1];
    }

    private String getUrl(String interest) {
        return "https://maps.googleapis.com/maps/api/place/search/json?" + getLatLon() +"&"+getTypes(interest)+"&radius=50000&sensor=true&key=" + GOOGLE_KEY;
    }

    private String getLatLon() {
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return "location="+latLon[0]+","+latLon[1];
    }

    private String getTypes(String interest){
        StringBuilder types = new StringBuilder("types=");
        types.append(interest);
        return types.toString();
    }

}

