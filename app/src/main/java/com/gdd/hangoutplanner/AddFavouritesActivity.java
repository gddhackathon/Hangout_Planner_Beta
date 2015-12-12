package com.gdd.hangoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gdd.hangoutplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AddFavouritesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] latLongs;
    private String addressSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView3);
        System.out.println(getIntent().getStringExtra("latLon"));
        textView.setText(getIntent().getStringExtra("selectedAddress") + getIntent().getStringExtra("latLon"));
        latLongs = getIntent().getStringExtra("selectedAddress").split(":");
        addressSelected = getIntent().getStringExtra("latLon");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void destinationOverview(View view){
        Intent intent = new Intent(this, DestinationOverviewActivity.class);
        intent.putExtra("latLon", getIntent().getStringExtra("latLon"));
        ArrayList<String> interests = new ArrayList<String>();
        interests.add("lodging");
        interests.add("point_of_interest");
        interests.add("place_of_worship");
        interests.add("restaurant");
        interests.add("bar");
        intent.putStringArrayListExtra("interests", interests);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.valueOf(latLongs[0]);
        double longitude = Double.valueOf(latLongs[1]);
        LatLng address = new LatLng(lat, longitude);
        mMap.addMarker(new MarkerOptions().position(address).title(addressSelected));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
    }

}
