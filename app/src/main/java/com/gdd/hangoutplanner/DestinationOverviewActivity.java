package com.gdd.hangoutplanner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gdd.hangoutplanner.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("In Destination overview activity" + getIntent().getStringExtra("latLon"));

        ListView selectedListView = (ListView) findViewById(R.id.listViewSelectedFav);
        ArrayList<String> selectedChecks = getIntent().getStringArrayListExtra("checkedFavourites");
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,selectedChecks);
        selectedListView.setAdapter(itemsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new googleplaces().execute();
    }
    
    private class googleplaces extends AsyncTask<Void,Void,String> {
        String temp;
    @Override
    protected String doInBackground(Void... params) {
        System.out.println(getWeatherURL());
        temp = makeCall(getWeatherURL());
        System.out.println(getUrl());
        temp = makeCall(getUrl());
        return temp;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        if (temp == null) {
        } else {
           // venuesList = parseGoogleParse(temp);
        }
    }
}

    @NonNull
    private String getUrl() {
        return "https://maps.googleapis.com/maps/api/place/search/json?" + getLatLon() +"&"+getTypes()+"&radius=50000&sensor=true&key=" + GOOGLE_KEY;
    }

    private String getWeatherURL(){
        return "http://api.openweathermap.org/data/2.5/weather?lat="+getLat()+"&lon="+getLon()+"&appid=3afc641863812329b5de4b04bebf1937";
    }

    private String getLat(){
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return latLon[0];
    }

    private String getLon(){
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return latLon[1];
    }

    private String getLatLon() {
        String latLon[] = getIntent().getStringExtra("latLon").split(":");
        return "location="+latLon[0]+","+latLon[1];
    }

    private String getTypes(){
        StringBuilder types = new StringBuilder("types=");
        ArrayList<String> interests = getIntent().getStringArrayListExtra("interests");
        for(String interset : interests){
            types.append(interset).append("|");
        }
        return types.deleteCharAt(types.lastIndexOf("|")).toString();
    }

    public static String makeCall(String url) {
        String replyString = "";
        try {
            URL placesUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) placesUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(150000);
            urlConnection.setConnectTimeout(150000);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyString.trim();
    }


    private static List<GooglePlace> parseGoogleParse(final String response) {

        List<GooglePlace> temp = new ArrayList<GooglePlace>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            System.out.println(jsonObject);
            /*if (jsonObject.has("results")) {

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    GooglePlace poi = new GooglePlace();

                    if (jsonArray.getJSONObject(i).has("name")) {

                        poi.setName(jsonArray.getJSONObject(i).optString("name"));
                        poi.setRating(jsonArray.getJSONObject(i).optString("rating", " "));

                        if (jsonArray.getJSONObject(i).has("opening_hours")) {
                            if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
                                    poi.setOpenNow("YES");
                                } else {
                                    poi.setOpenNow("NO");
                                }
                            }
                        } else {
                            poi.setOpenNow("Not Known");
                        }

                        if (jsonArray.getJSONObject(i).has("types")) {
                            JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");
                            for (int j = 0; j < typesArray.length(); j++) {
                                poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());
                            }
                        }
                    }
                    temp.add(poi);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<GooglePlace>();
        }
        return temp;
    }
}

