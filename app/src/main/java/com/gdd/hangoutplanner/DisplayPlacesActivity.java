package com.gdd.hangoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gdd.hangoutplanner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Place;

public class DisplayPlacesActivity extends AppCompatActivity {

    private ShareActionProvider share=null;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Converting HashMap Values into ArrayList - List<Place>
        HashMap<String, String> interestVsPlaces = (HashMap<String, String>) getIntent().getSerializableExtra("interestVsPlaces");
        List valueList = new ArrayList(interestVsPlaces.values());
        ArrayList<String> places = new ArrayList<>(80);
        for (int i=0 ; i< valueList.size(); i++){
            List<Place> placesList = (ArrayList) valueList.get(i);
                for (Place place : placesList){
                    System.out.println(place.getName());
                    places.add(place.getName());
                }
        }

        //Displaying All the Places in a list
        ListView selectedListView = (ListView) findViewById(R.id.listViewPlaces);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,places);
        selectedListView.setAdapter(itemsAdapter);

        //For Sharing
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "I am Sending a Test Message , Place me Places List Activity Layout");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_places, menu);
        MenuItem item=menu.findItem(R.id.share);
        share=(ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (share != null) {
            share.setShareIntent(mShareIntent);
        }
        return true;
    }

}
