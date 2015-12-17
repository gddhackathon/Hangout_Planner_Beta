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
import android.widget.TextView;

import com.gdd.hangoutplanner.R;

import model.Place;

public class PlaceDetailsActivity extends AppCompatActivity {

    private ShareActionProvider share=null;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Place Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Place place  = (Place)getIntent().getExtras().get("destination");

        TextView textView = (TextView) findViewById(R.id.textViewTitle);
        textView.setText(place.getName());

        //For Sharing
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "Address : " + place.getAddress() +"\n"
        + "Name : " + place.getAddress() + "\n"
        + "Rating : " + place.getRating());
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
