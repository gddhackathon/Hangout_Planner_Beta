package com.gdd.hangoutplanner;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.gdd.hangoutplanner.R;

import utils.GooglePlacesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularDestinationFragment extends Fragment {


    public PopularDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_destination, container, false);
        autoCompletePlaces(rootView);
        return rootView;
    }

    private void autoCompletePlaces(View rootView) {
        System.out.println("in auto complete places");
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) rootView.findViewById(R.id.searchDestAutoCompView);
        autoCompView.setAdapter(new GooglePlacesUtil(getActivity().getApplicationContext(), R.layout.places_list_item,R.id.contact_name));
        autoCompView.setOnItemClickListener(autoCompView.getOnItemClickListener());
    }

}
