package com.gdd.hangoutplanner;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gdd.hangoutplanner.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.CoOrdinate;
import model.Geometry;

public class SuggestPlacesActivity extends AppCompatActivity {

    private TextView textView;
    private StringBuilder stringBuilder = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> selectedPlaces = getIntent().getStringArrayListExtra("selectedContactsList");
        for(String place :selectedPlaces){
            System.out.println("place = " + place + "]");
        }
        String[] place1 = selectedPlaces.get(0).split(":");
        String[] place2 = selectedPlaces.get(1).split(":");
        System.out.println("place = " + place1);
        System.out.println("place2 = " + place2);
        Geometry newYrok = new Geometry(new CoOrdinate(place1[0], place1[1]));
        Geometry chicago = new Geometry(new CoOrdinate(place2[0], place2[1]));
        /*Geometry newYrok = new Geometry(new CoOrdinate("40.7142700", "-74.0059700"));
        Geometry chicago = new Geometry(new CoOrdinate("41.8500300", "-87.6500500"));*/
        //getLocation(newYrok,chicago,1,1);
        //getLocation(newYrok,chicago,1,2);
        //getLocation(newYrok,chicago,2,1);
        //getLocation(newYrok,chicago,3,1);
        textView.setText(stringBuilder.toString());
    }




}
