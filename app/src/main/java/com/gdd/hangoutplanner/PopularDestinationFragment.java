package com.gdd.hangoutplanner;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import com.gdd.hangoutplanner.R;

import java.util.ArrayList;

import db.RecentSearchDbHelper;
import model.HangoutPlanner;
import model.RecentSearch;
import utils.CurrentLocationProvider;
import utils.GeocodingLocation;
import utils.GooglePlacesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularDestinationFragment extends Fragment {

    private String latLon;
    private String selectedAddress;
    public PopularDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final HangoutPlanner hangoutPlanner = (HangoutPlanner)getActivity().getApplicationContext();

        final View rootView = inflater.inflate(R.layout.fragment_popular_destination, container, false);
        autoCompletePlaces(rootView);

        ImageView addFavourites = (ImageView) rootView.findViewById(R.id.addFavourites);
        addFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latLon == null) {
                    //Toast.makeText(getActivity().getApplicationContext(), R.string.invalid_place_error_message, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                    final HangoutPlanner hangoutPlanner = (HangoutPlanner) getActivity().getApplicationContext();
                    hangoutPlanner.setLatLon(latLon);
                    hangoutPlanner.setSelectedAddress(selectedAddress);
                    RecentSearchDbHelper recentSearchDbHelper = new RecentSearchDbHelper(getContext());
                    ArrayList<RecentSearch> recentSearch = recentSearchDbHelper.getResentSearches();
                    ArrayList recentSearchAddress = new ArrayList<>();
                    for(RecentSearch search : recentSearch){
                        recentSearchAddress.add(search.getAddress());
                    }
                    if(!recentSearchAddress.contains(selectedAddress)){
                        recentSearchDbHelper.insertResentSearch(selectedAddress,latLon);
                    }
                    startActivity(intent);
                }
            }
        });

        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        //popular Destinations
        ImageView imageNearBy = (ImageView)rootView.findViewById(R.id.populardestinations_w);
        imageNearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentLocationProvider currentLocationProvider = new CurrentLocationProvider(getActivity().getApplicationContext());

                final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
                boolean isGPSEnabled = manager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = manager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGPSEnabled && !isNetworkEnabled) {
                    buildAlertMessageNoGps();
                }else {
                    currentLocationProvider.getCurrentLocation();
                    Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                    startActivity(intent);
                }
            }
        });
        ImageView imageNyc = (ImageView)rootView.findViewById(R.id.populardestinations_nyc);
        imageNyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                hangoutPlanner.setLatLon("40.7127:-74.0059");
                hangoutPlanner.setSelectedAddress("New York City");
                startActivity(intent);
            }
        });
        ImageView imageChicago = (ImageView)rootView.findViewById(R.id.populardestinations_chicago);
        imageChicago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                hangoutPlanner.setLatLon("41.836944:-87.684722");
                hangoutPlanner.setSelectedAddress("Chicago");
                startActivity(intent);
            }
        });
        ImageView imageMiami = (ImageView)rootView.findViewById(R.id.populardestinations_miami);
        imageMiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                hangoutPlanner.setLatLon("25.775278:-80.208889");
                hangoutPlanner.setSelectedAddress("Miami");
                startActivity(intent);
            }
        });
        ImageView imageSeattle= (ImageView)rootView.findViewById(R.id.populardestinations_seattle);
        imageSeattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                hangoutPlanner.setLatLon("47.609722:-122.333056");
                hangoutPlanner.setSelectedAddress("Seattle");
                startActivity(intent);
            }
        });
        ImageView imageLA = (ImageView)rootView.findViewById(R.id.populardestinations_la);
        imageLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                hangoutPlanner.setLatLon("34.05:-118.25");
                hangoutPlanner.setSelectedAddress("Los Angeles");
                startActivity(intent);
            }
        });
        return rootView;
    }


    private void autoCompletePlaces(View rootView) {
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) rootView.findViewById(R.id.searchDestAutoCompView);
        System.out.println("On text change" + autoCompView.getText().toString());
        autoCompView.setAdapter(new GooglePlacesUtil(getActivity().getApplicationContext(),
                R.layout.places_list_item, R.id.contact_name));
        autoCompView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                String str = (String) adapterView.getItemAtPosition(position);
                String address = str;
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getLocationFromAddress(address, getActivity().getApplicationContext(), new GeocoderHandler());
               // Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                if (latLon == null) {
                   // Toast.makeText(getActivity().getApplicationContext(), R.string.invalid_place_error_message, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                    final HangoutPlanner hangoutPlanner = (HangoutPlanner) getActivity().getApplicationContext();
                    hangoutPlanner.setLatLon(latLon);
                    hangoutPlanner.setSelectedAddress(selectedAddress);
                    // RecentSearchDbHelper recentSearchDbHelper = new RecentSearchDbHelper(getActivity());
                    //  startActivity(intent);
                }
            }
        });
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String locationLatLon;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    locationLatLon = bundle.getString("latLon");
                    break;
                default:
                    locationAddress = null;
                    locationLatLon = null;
            }
            latLon = locationLatLon;
            selectedAddress  = locationAddress;
           // Toast.makeText(getActivity().getApplicationContext(), latLon, Toast.LENGTH_SHORT).show();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems is disabled, Do you want to enable it now?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
