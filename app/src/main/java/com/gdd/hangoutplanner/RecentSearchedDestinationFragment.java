package com.gdd.hangoutplanner;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import utils.GeocodingLocation;
import utils.GooglePlacesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentSearchedDestinationFragment extends Fragment {

    private String latLon;
    private String selectedAddress;
    public RecentSearchedDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragmet_recent_searched_destination, container, false);
        return rootView;
    }



}
