package com.gdd.hangoutplanner;

import android.content.Intent;
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
import java.util.List;

public class AddFavouritesActivity extends AppCompatActivity implements OnMapReadyCallback,
        ShareActionProvider.OnShareTargetSelectedListener {

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

    private ShareActionProvider share=null;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        TextView textView = (TextView) findViewById(R.id.textView3);
        System.out.println(getIntent().getStringExtra("latLon"));
        textView.setText(getIntent().getStringExtra("selectedAddress") + getIntent().getStringExtra("latLon"));
        latLongs = getIntent().getStringExtra("latLon").split(":");
        addressSelected = getIntent().getStringExtra("selectedAddress");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addListenersToCheckBoxes();

        //Below code is removable to other activity
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "I am Sending a Test Message , Place me Places List Activity Layout");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_next) {
            Intent intent = new Intent(this, DestinationOverviewActivity.class);
            intent.putExtra("latLon", getIntent().getStringExtra("latLon"));
            ArrayList<String> interests = new ArrayList<String>();
            interests.add("lodging");
            interests.add("point_of_interest");
            interests.add("place_of_worship");
            interests.add("restaurant");
            interests.add("bar");
            intent.putStringArrayListExtra("interests", interests);
            intent.putStringArrayListExtra("checkedFavourites", selectedChecks);
            intent.putExtra("addressSelected", addressSelected);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem item=menu.findItem(R.id.share);

        share=(ShareActionProvider)MenuItemCompat.getActionProvider(item);

        if (share != null) {
            share.setShareIntent(mShareIntent);
        }

        return true;
    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source,
                                         Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();

        return(false);
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
