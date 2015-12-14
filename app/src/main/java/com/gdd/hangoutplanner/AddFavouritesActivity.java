package com.gdd.hangoutplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gdd.hangoutplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.HangoutPlanner;
import utils.ExceptionHandler;

public class AddFavouritesActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private String[] latLongs;
    private String addressSelected;

    private CheckBox chkBar;
    private CheckBox chkMovies;
    private CheckBox chklocalmall;
    private CheckBox ckhShopping;
    private CheckBox chkHotel;
    private CheckBox chkfood;

    final ArrayList<String> selectedChecks = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_add_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select your Interests");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        HangoutPlanner hangoutPlanner = (HangoutPlanner) getApplicationContext();
        String latLon = hangoutPlanner.getLatLon();
        String selectedAddress = hangoutPlanner.getSelectedAddress();
        System.out.println(latLon);
        latLongs = latLon.split(":");
        addressSelected = selectedAddress;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addListenersToCheckBoxes();
        Button buttonGetPlaces = (Button) findViewById(R.id.buttonGetPlaces);
        buttonGetPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getNextActivityIntent();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Intent intent = getNextActivityIntent();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getNextActivityIntent() {
        HangoutPlanner hangoutPlanner = (HangoutPlanner)getApplicationContext();
        Map<String,String> googleTypesMapping = hangoutPlanner.getGoogleTypesMapping();
        List<String> checkedFavourites = new ArrayList<>();
        if (!selectedChecks.isEmpty()) {
            for (String favourite : selectedChecks) {
                checkedFavourites.add(googleTypesMapping.get(favourite));
            }
        }
        hangoutPlanner.setSelectedInteresets(checkedFavourites);
        Intent intent = new Intent(this, DestinationOverviewActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.valueOf(latLongs[0]);
        double longitude = Double.valueOf(latLongs[1]);
        LatLng address = new LatLng(lat, longitude);
        mMap.addMarker(new MarkerOptions().position(address).title(addressSelected));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        float zoomLevel = 6.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, zoomLevel));
    }

    public void addListenersToCheckBoxes() {
        chkBar = (CheckBox) findViewById(R.id.checkBar);
        chkMovies = (CheckBox) findViewById(R.id.checkMovies);
        chklocalmall = (CheckBox) findViewById(R.id.checkLoclMal);
        ckhShopping = (CheckBox) findViewById(R.id.checkShopping);
        chkHotel = (CheckBox) findViewById(R.id.checkHotel);
        chkfood = (CheckBox) findViewById(R.id.checkFood);

        chkBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("bar");
                } else {
                    if (selectedChecks.contains("bar")) {
                        selectedChecks.remove("bar");
                    }
                }
            }
        });
        chkMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("movies");
                }else {
                    if (selectedChecks.contains("movies")) {
                        selectedChecks.remove("movies");
                    }
                }
            }
        });
        chklocalmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("localmall");
                }else {
                    if (selectedChecks.contains("localmall")) {
                        selectedChecks.remove("localmall");
                    }
                }
            }
        });
        ckhShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("shopping");
                }else {
                    if (selectedChecks.contains("shopping")) {
                        selectedChecks.remove("shopping");
                    }
                }
            }
        });
        chkHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("hotel");
                }else {
                    if (selectedChecks.contains("hotel")) {
                        selectedChecks.remove("hotel");
                    }
                }
            }
        });
        chkfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedChecks.add("food");
                }else {
                    if (selectedChecks.contains("food")) {
                        selectedChecks.remove("food");
                    }
                }
            }
        });

    }

}
