package com.gdd.hangoutplanner;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.RecentSearchDbHelper;
import model.HangoutPlanner;
import model.RecentSearch;
import utils.GeocodingLocation;
import utils.GooglePlacesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentSearchedDestinationFragment extends Fragment{

    private String latLon;
    private String selectedAddress;
    private ArrayList<RecentSearch> recentSearches= null;
    public RecentSearchedDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragmet_recent_searched_destination, container, false);
        final RecentSearchDbHelper recentSearchDbHelper = new RecentSearchDbHelper(getContext());
        recentSearches = recentSearchDbHelper.getResentSearches();
        ArrayList<String> searches = new ArrayList<>();
        for(RecentSearch recentSearch :recentSearches){
            searches.add(recentSearch.getAddress());
        }
        final ListView recentSearchListView = (ListView) rootView.findViewById(R.id.listViewRecentSearches);
        recentSearchListView.setClickable(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.recent_searches, R.id.address, searches);
        recentSearchListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recentSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String str = (String) arg0.getItemAtPosition(position);
                System.out.println("on item liked" + str);
                String address = str;
                RecentSearch selectedRecentSearch = recentSearches.get(position);
                HangoutPlanner hangoutPlanner = (HangoutPlanner) getActivity().getApplicationContext();
                hangoutPlanner.setLatLon(selectedRecentSearch.getLatLon());
                hangoutPlanner.setSelectedAddress((selectedRecentSearch.getAddress()));
                Toast.makeText(getActivity(), hangoutPlanner.getSelectedAddress(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddFavouritesActivity.class);
                startActivity(intent);
            }
        });

        Button button = (Button) rootView.findViewById(R.id.buttonClear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentSearchDbHelper.clearHistory();
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return rootView;
    }
}
