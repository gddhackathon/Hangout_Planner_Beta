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

public class AddFavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView3);
        System.out.println(getIntent().getStringExtra("latLon"));
        textView.setText(getIntent().getStringExtra("selectedAddress") + getIntent().getStringExtra("latLon"));

    }

    public void destinationOverview(View view){
        Intent intent = new Intent(this, DestinationOverviewActivity.class);
        startActivity(intent);
    }

}
