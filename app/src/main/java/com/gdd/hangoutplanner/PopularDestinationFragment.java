package com.gdd.hangoutplanner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gdd.hangoutplanner.R;

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
        // Inflate the layout for this fragment
        // Defined Array values to show in ListView
        View rootView = inflater.inflate(R.layout.fragment_popular_destination, container, false);
        String[] values = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };
        ListView listView = (ListView) rootView.findViewById(R.id.listPopularDestiantions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_view_item_popular_destination,R.id.info_text, values);
        listView.setAdapter(adapter);
        return rootView;
    }

}
