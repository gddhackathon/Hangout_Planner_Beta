package com.gdd.hangoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.HangoutPlanner;
import model.Place;
import utils.CustomListAdapter;
import utils.DownloadGooglePlacesInfo;
import utils.ExceptionHandler;
import utils.HangoutPlannerUtil;

public class DisplayPlacesActivity extends AppCompatActivity {

    final String GOOGLE_KEY = HangoutPlannerUtil.GOOGLE_KEY;

    private String placeid=null;
    private Place placeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_display_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        HangoutPlanner hangoutPlanner = (HangoutPlanner) getApplicationContext();
        toolbar.setTitle("Results Found ");
        toolbar.setSubtitle(hangoutPlanner.getSelectedAddress());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        // Converting HashMap Values into ArrayList - List<Place>
       final ArrayList<Place> selectedInterestVsPlaces = (ArrayList<Place>) getIntent().getSerializableExtra("selectedInterestVsPlaces");
        //List valueList = new ArrayList(interestVsPlaces.values());
        //ArrayList places = (ArrayList)valueList.get(0);
        ArrayList<Place> sortedPlaces = getSortedPlaces(selectedInterestVsPlaces);
        emptyCheckForPlaces(sortedPlaces);
        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        lv1.setAdapter(new CustomListAdapter(this, sortedPlaces));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Place newsData = (Place) o;
                placeSelected = newsData;
                placeid = newsData.getPlace_id();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String googlePlacesDetailsURL = getUrl(newsData.getPlace_id());
                Place place = DownloadGooglePlacesInfo.makeCallForPlaceDetails(googlePlacesDetailsURL);
                Intent intent = new Intent(getApplication(), PlaceDetailsActivity.class);
                intent.putExtra("selectedInterestVsPlaces", (ArrayList) selectedInterestVsPlaces);
                intent.putExtra("destination", place);
                startActivity(intent);
            }
        });

        Button button =(Button) findViewById(R.id.buttonSearchOther);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AddFavouritesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void emptyCheckForPlaces(ArrayList<Place> sortedPlaces) {
        TextView textView = (TextView)findViewById(R.id.textViewNoPlaces);
        Button button =(Button) findViewById(R.id.buttonSearchOther);
        if((null == sortedPlaces) || (sortedPlaces.size()==0)){
            textView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
        else {
            textView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
    }

    private ArrayList<Place> getSortedPlaces(ArrayList<Place> selectedInterestVsPlaces) {
        HangoutPlanner hangoutPlanner = (HangoutPlanner) getApplicationContext();
        List<String> selectedInteresets = hangoutPlanner.getSelectedInteresets();
        HashMap<Place, Integer> selectedInterestPlacesInSortedOrder = new HashMap<Place, Integer>();
        for(Place place :selectedInterestVsPlaces){
            int currentPlacematchingCount = 1;
            for(String interest : selectedInteresets){
                if(place.getTypes().contains(interest)){
                    currentPlacematchingCount++;
                }
            }
            selectedInterestPlacesInSortedOrder.put(place, currentPlacematchingCount);
        }
        return sortPlacesInOrder(selectedInterestPlacesInSortedOrder);
    }

    private ArrayList<Place> sortPlacesInOrder(HashMap<Place, Integer> selectedInterestPlacesInSortedOrder){
        List<Map.Entry<Place, Integer>> list = new ArrayList<Map.Entry<Place, Integer>>(selectedInterestPlacesInSortedOrder.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Place, Integer>>() {
            public int compare(Map.Entry<Place, Integer> o1, Map.Entry<Place, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        ArrayList<Place> places = new ArrayList<Place>();
        for(Map.Entry<Place, Integer> entry:list){
            places.add(entry.getKey());
        }
        System.out.println("sorted places");
        for(Place place :places){
            System.out.println("place = " + place.toString());
        }
        return places;
    }

    private String getUrl(String placeId) {
        String placeDetailsURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeId+"&key=" + GOOGLE_KEY;
        System.out.println("placeDetailsURL = " + placeDetailsURL);
        return placeDetailsURL;
    }

}
