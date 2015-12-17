package com.gdd.hangoutplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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

public class AddFavouritesActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

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
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_add_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select your Interests");
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        enableBackButtonCustomLogic(toolbar);

        HangoutPlanner hangoutPlanner = (HangoutPlanner) getApplicationContext();
        String latLon = hangoutPlanner.getLatLon();
        String selectedAddress = hangoutPlanner.getSelectedAddress();
        System.out.println(latLon);
        latLongs = latLon.split(":");
        addressSelected = selectedAddress;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button buttonGetPlaces = (Button) findViewById(R.id.buttonGetPlaces);
        listView = (ListView) findViewById(R.id.listInterests);
        ArrayList<String> interests = addAllInterests();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, interests);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        buttonGetPlaces.setOnClickListener(this);

    }

    @NonNull
    private ArrayList<String> addAllInterests() {
        ArrayList<String> interests = new ArrayList<String>();
        interests.add("restaurant");
        interests.add("cafe");
        interests.add("movie theater");
        interests.add("bar");
        interests.add("tourist attractions");
        interests.add("amusement park");
        interests.add("art gallery");
        interests.add("casino");
        interests.add("camp ground");
        interests.add("museum");
        interests.add("night clubs");
        interests.add("park");
        interests.add("shopping mall");
        interests.add("spa");
        interests.add("aquarium");
        interests.add("zoo");
        return interests;
    }

    private void enableBackButtonCustomLogic(Toolbar toolbar) {
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(AddFavouritesActivity.this);
                }
            });
            toolbar.inflateMenu(R.menu.menu_places);
        }
    }

    public void onClick(View v) {
        getSelectChecksAndStartNextActivity();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            getSelectChecksAndStartNextActivity();
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
        float zoomLevel = 5.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, zoomLevel));
    }


    private void getSelectChecksAndStartNextActivity() {
        selectedChecks.clear();
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            selectedChecks.add(selectedItems.get(i));
        }
        Intent intent = getNextActivityIntent();
        // start the ResultActivity
        startActivity(intent);
    }

}
