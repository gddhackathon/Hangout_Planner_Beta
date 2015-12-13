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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gdd.hangoutplanner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Place;
import utils.CustomListAdapter;

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
        ArrayList places = (ArrayList)valueList.get(0);
        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        lv1.setAdapter(new CustomListAdapter(this, places));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Place newsData = (Place) o;
                Toast.makeText(getApplication(), "Selected :" + " " + newsData.getName(), Toast.LENGTH_LONG).show();
            }
        });


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
