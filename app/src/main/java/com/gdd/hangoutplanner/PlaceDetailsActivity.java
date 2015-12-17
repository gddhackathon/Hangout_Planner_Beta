package com.gdd.hangoutplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdd.hangoutplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URL;
import java.util.ArrayList;

import model.HangoutPlanner;
import model.Photo;
import model.Place;

public class PlaceDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ShareActionProvider share=null;
    private Intent mShareIntent;
    private GoogleMap mMap;
    private Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Place Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
         place  = (Place)getIntent().getExtras().get("destination");
        toolbar.setTitle(place.getAddress());
        toolbar.setSubtitle(place.getFormattedAddress());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.placeMap);
        mapFragment.getMapAsync(this);

        //For Sharing
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "Address : " + place.getFormattedAddress() + "\n"
                + "Phone : " + place.getFormattedPhoneNumber() + "\n"
                + "Web : " + place.getPlaceWebSite() + "\n"
                + "Rating : " + place.getRating());

        //Loading Data onto page.
        TextView phone = (TextView) findViewById(R.id.textView7);
        phone.setText(place.getFormattedPhoneNumber());
        TextView intlPhone = (TextView) findViewById(R.id.textView7);
        intlPhone.setText(place.getInternationalPhoneNumber());
        TextView website = (TextView) findViewById(R.id.textView9);
        website.setText(place.getPlaceWebSite());
        TextView open = (TextView) findViewById(R.id.textView14);
        open.setText(place.getOpenNow());

        int temp=1;
        if(null != place.getPhotos()) {
            for (Photo photo : place.getPhotos()) {
                if (null != photo) {
                    if (temp == 1) {
                        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
                        Bitmap bmp = getBitmap(place);
                        if (bmp != null)
                            imageView.setImageBitmap(bmp);

                    }
                    if (temp == 2) {
                        ImageView imageView = (ImageView) findViewById(R.id.imageView8);
                        Bitmap bmp = getBitmap(place);
                        if (bmp != null)
                            imageView.setImageBitmap(bmp);
                    }
                    if (temp == 3) {
                        ImageView imageView = (ImageView) findViewById(R.id.imageView9);
                        Bitmap bmp = getBitmap(place);
                        if (bmp != null)
                            imageView.setImageBitmap(bmp);
                        break;
                    }
                    temp++;
                }
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Snackbar.make(view, "Yet to Implement Direction", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    @Nullable
    private Bitmap getBitmap(Place place) {
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(place.getIcon());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception e){

        }
        return bmp;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final ArrayList<Place> selectedInterestVsPlaces = (ArrayList<Place>) getIntent().getSerializableExtra("selectedInterestVsPlaces");
                Intent intent = new Intent(getApplication(), DisplayPlacesActivity.class);
                intent.putExtra("selectedInterestVsPlaces", (ArrayList)selectedInterestVsPlaces);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.valueOf(place.getGeometry().getCoOrdinate().getLat());
        double longitude = Double.valueOf(place.getGeometry().getCoOrdinate().getLon());
        LatLng address = new LatLng(lat, longitude);
        mMap.addMarker(new MarkerOptions().position(address).title(place.getFormattedAddress()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        float zoomLevel = 5.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, zoomLevel));
    }


}
