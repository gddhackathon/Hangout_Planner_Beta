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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import model.HangoutPlanner;
import model.Interest;
import model.Place;
import model.Weather;
import utils.CustomListAdapter;
import utils.DownloadGooglePlacesInfo;
import utils.DownloadWeatherInfo;
import utils.ExceptionHandler;
import utils.InterestListAdapter;

public class DestinationOverviewActivity extends AppCompatActivity {

    final String GOOGLE_KEY = "AIzaSyA2pWuAzJ_agDXpISSGDEh1hnk6B7SPMOw";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_destination_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Destination Weather and Interests");
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        HangoutPlanner hangoutPlanner = (HangoutPlanner) getApplicationContext();
        System.out.println("hangoutPlanner = " + hangoutPlanner.getSelectedInteresets().toArray().toString());
        TextView addressSelectedTextView  = (TextView) findViewById(R.id.textViewTitle);
        addressSelectedTextView.setText(hangoutPlanner.getSelectedAddress());

        //List to show what is selected
        final ListView selectedListView = (ListView) findViewById(R.id.listViewSelectedFav);
        List<String> selectedChecks = hangoutPlanner.getSelectedInteresets();
        if(!selectedChecks.contains("Tourist attractions"))
        {
            selectedChecks.add("Tourist attractions");
        }
        for(String fav :selectedChecks){
            System.out.println("fav = " + fav);
        }
        /*ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,selectedChecks);
        selectedListView.setAdapter(itemsAdapter);*/


        ArrayList<Interest> selectedIntArrayList= new ArrayList<Interest>();
        for(String name : selectedChecks){
            Interest interest = new Interest();
            interest.setName(name);
            selectedIntArrayList.add(interest);
        }
        selectedListView.setAdapter(new InterestListAdapter(this, selectedIntArrayList));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        String tempURL = getWeatherURL();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Weather weather = DownloadWeatherInfo.makeCall(tempURL);
        System.out.println("icon) = " + weather.getIcon());

        //On Click of any list item
        selectedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Object o = selectedListView.getItemAtPosition(position);
                Interest newsData = (Interest) o;
                String item = newsData.getName();
                Intent intent = new Intent(getApplication(), DisplayPlacesActivity.class);
                if(!"Tourist attractions".equalsIgnoreCase(item)) {
                    String googlePlacesURL = getUrl(item);
                    System.out.println(googlePlacesURL);
                    List<Place> places = DownloadGooglePlacesInfo.makeCall(googlePlacesURL);
                    intent.putExtra("selectedInterestVsPlaces", (ArrayList)places);
                }
                if(item.equalsIgnoreCase("Tourist attractions")){
                    String googlePlacesURLForTouristAttractions = getURLForTouristAttractions();
                    List<Place> places = DownloadGooglePlacesInfo.makeCall(googlePlacesURLForTouristAttractions);
                    ArrayList<Place> filteredPlaces = filterTravelAgency(places);
                    intent.putExtra("selectedInterestVsPlaces",filteredPlaces);
                }
                startActivity(intent);
            }
        });

        //Loading Weathe Details
        TextView textViewTemp = (TextView) findViewById(R.id.textViewTemp);
        textViewTemp.setText(weather.getTemperature().getCurrent() + "\u2109");
        TextView textViewMax = (TextView) findViewById(R.id.textViewMax);
        textViewMax.setText("Max        "+weather.getTemperature().getMax() +  "\u2109");
        TextView textViewMin = (TextView) findViewById(R.id.textViewMin);
        textViewMin.setText("Min        "+weather.getTemperature().getMin()+  "\u2109")  ;
        TextView textViewCondition = (TextView) findViewById(R.id.textViewCondition);
        textViewCondition.setText(weather.getDescription());
        TextView textViewhumidity = (TextView) findViewById(R.id.textViewhumidity);
        textViewhumidity.setText("Humidity   "+weather.getHumidity().getValue());
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
        String latLon[] = getSavedLatLon().split(":");
        return latLon[0];
    }

    private String getLon(){
        String latLon[] = getSavedLatLon().split(":");
        return latLon[1];
    }

    private ArrayList<Place> filterTravelAgency(List<Place> places) {
        ArrayList<Place> filteredPlaces = new ArrayList<Place>();
        for(Place place : places){
            if(!place.getTypes().contains("travel_agency")){
                filteredPlaces.add(place);
            }
        }
        return filteredPlaces;
    }
    private String getUrl(String interest) {
        return "https://maps.googleapis.com/maps/api/place/search/json?" + getLatLon() +"&"+getTypes(interest)+"&radius=50000&sensor=true&key=" + GOOGLE_KEY;
    }

    private String getLatLon() {
        String latLon[] = getSavedLatLon().split(":");
        return "location="+latLon[0]+","+latLon[1];
    }

    private String getTypes(String interest){
        StringBuilder types = new StringBuilder("types=");
        types.append(interest);
        return types.toString();
    }

    private String getSavedLatLon(){
        HangoutPlanner hangoutPlanner = (HangoutPlanner)getApplicationContext();
        return hangoutPlanner.getLatLon();
    }

    private String getURLForTouristAttractions() {
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attractions+in+"+getQuery()+"&key="+GOOGLE_KEY;
        System.out.println("url = " + url);
        return url;
    }

    private String getQuery(){
        HangoutPlanner hangoutPlanner =  (HangoutPlanner) getApplicationContext();
        String selectedPlace = getFormattedAddress(hangoutPlanner.getSelectedAddress());
        return selectedPlace;
    }

    private String getFormattedAddress(String selectedAddress){
        StringBuilder address = new StringBuilder();
        List<String> addressList = Arrays.asList(selectedAddress.split(","));
        System.out.println("addressList = " + addressList);
        if(addressList.size() == 1){
            address.append(addressList.get(0).trim().replace(" ", "+"));
        }
        else if(addressList.size() == 2){
            address.append(addressList.get(0).trim().replace(" ", "+")+"+"+addressList.get(1).trim().replace(" ", "+"));
        }
        else if(addressList.size() > 2){
            address.append(addressList.get(addressList.size()-3).trim().replace(" ", "+")+"+"+addressList.get(addressList.size()-2).trim().replace(" ", "+")
                    /*+"+"+addressList.get(addressList.size()-1).trim().replace(" ", "+")*/);
        }
        return address.toString();
    }
}

