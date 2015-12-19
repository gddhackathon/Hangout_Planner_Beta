package com.gdd.hangoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gdd.hangoutplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CoOrdinate;
import model.Geometry;
import model.HangoutPlanner;
import model.Place;
import utils.ContactsUtil;
import utils.DownloadGooglePlacesInfo;
import utils.GeocodingLocation;
import utils.MidPointUtil;

public class SelectFriendsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView lv;
    private ListView addressView;
    ArrayAdapter<String> adapter;
    private ListView selectedContacts;
    private ArrayAdapter<String> scViewAdapter;
    EditText inputSearch;
    private List<String> selectedContactsList = new ArrayList<>();
    private List<Geometry> geometries = new ArrayList<>();
    Map<String,String> addressPhoneNumberMapping = getAddressMapping();
    String latLon = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Select Friends");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        lv = (ListView)findViewById(R.id.listViewContacts);
        List<String> contacts = ContactsUtil.displayContacts(getContentResolver());
        //addAddressToContact(contacts);
        adapter = new ArrayAdapter<String>(this,R.layout.contacts_list_item,R.id.contact_name, contacts);
        lv.setAdapter(adapter);
        lv.setVisibility(View.GONE);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (0 == cs.length()) {
                    lv.setVisibility(View.GONE);
                } else {
                    SelectFriendsActivity.this.adapter.getFilter().filter(cs);
                    lv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout4);
                relativeLayout.setVisibility(View.VISIBLE);
                final String item = (String) parent.getItemAtPosition(position);
                System.out.println(item);
                String[] contactName = item.split(":");
                GeocodingLocation locationAddress = new GeocodingLocation();
                String address = getAddress(contactName[1].trim());
                System.out.println("Address = " + address);
                locationAddress.getLocationFromAddress(address, getApplicationContext(), new GeocoderHandler());
                selectedContactsList.add(contactName[0].trim() + ":" + contactName[1].trim() + ";" + address);
                selectedContacts = (ListView) findViewById(R.id.selectedContacts);
                scViewAdapter = new ArrayAdapter<String>(SelectFriendsActivity.this, R.layout.selected_contacts_list_item, R.id.selected_contact_name, selectedContactsList);
                selectedContacts.setAdapter(scViewAdapter);
                selectedContacts.setVisibility(View.VISIBLE);
                inputSearch.setText("");
            }
        });
    }

    private String getAddress(String s) {
        return addressPhoneNumberMapping.get(s);
    }

    private Map<String,String> getAddressMapping() {
        Map<String,String> addressPhoneNumberMapping = new HashMap<>();
        addressPhoneNumberMapping.put("(309) 826-8134","6 Ross Dr, Bloomington, IL");
        addressPhoneNumberMapping.put("(309) 532-6286","Disneyland Dr, Anaheim, CA 92802");
        addressPhoneNumberMapping.put("(309) 530-6564","233 S Wacker Dr, Chicago, IL 60606");
        addressPhoneNumberMapping.put("(201) 450-8240","1180 Seven Seas Dr, Lake Buena Vista, FL 32830");
        return addressPhoneNumberMapping;
    }

    public void findPlace(View view){
        MidPointUtil midPointUtil = new MidPointUtil();
        String[] location1 = midPointUtil.getLocation(geometries.get(0), geometries.get(1), 1, 1, getApplicationContext()).split(":");
        String[] location2 = midPointUtil.getLocation(geometries.get(0),geometries.get(1),2,1,getApplicationContext()).split(":");
        //String[] location3 = midPointUtil.getLocation(geometries.get(0), geometries.get(1), 1, 2, getApplicationContext()).split(":");
        //String[] location4 = midPointUtil.getLocation(geometries.get(0),geometries.get(1),3,1,getApplicationContext()).split(":");
        System.out.println("location1 = " + location1[0]+location1[1]+location1[2]);
        System.out.println("location2 = " + location2[0]+location2[1]+location2[2]);
        //System.out.println("location3 = " + location3[0]+location3[1]+location3[2]);
        //System.out.println("location4 = " + location4[0]+location4[1]+location4[2]);
        CoOrdinate c1 = new CoOrdinate();
        c1.setLat(location1[1]);
        c1.setLon(location1[2]);
        Geometry g1 = new Geometry(c1);
        geometries.add(g1);
        CoOrdinate c2 = new CoOrdinate();
        c2.setLat(location2[1]);
        c2.setLon(location2[2]);
        Geometry g2 = new Geometry(c1);
        geometries.add(g2);
        double person1Lat = Double.parseDouble(geometries.get(0).getCoOrdinate().getLat());
        double person1Lon = Double.parseDouble(geometries.get(0).getCoOrdinate().getLon());
        LatLng person1LatLon = new LatLng(person1Lat, person1Lon);
        mMap.addMarker(new MarkerOptions().position(person1LatLon));
        double person2Lat = Double.parseDouble(geometries.get(1).getCoOrdinate().getLat());
        double person2Lon = Double.parseDouble(geometries.get(1).getCoOrdinate().getLon());
        LatLng person2LatLon = new LatLng(person2Lat, person2Lon);
        mMap.addMarker(new MarkerOptions().position(person2LatLon).title(location1[0]));
        double lat1 = Double.parseDouble(location1[1]);
        double longitude1 = Double.parseDouble(location1[2]);
        LatLng address1 = new LatLng(lat1, longitude1);
        mMap.addMarker(new MarkerOptions().position(address1).title(location1[0]));
        double lat2 = Double.parseDouble(location2[1]);
        double longitude2 = Double.parseDouble(location2[2]);
        LatLng address2 = new LatLng(lat2, longitude2);
        mMap.addMarker(new MarkerOptions().position(address2).title(location2[0]));
        double lat = Double.valueOf(lat2);
        double longitude = Double.valueOf(longitude2);
        LatLng address = new LatLng(lat, longitude);
        mMap.addMarker(new MarkerOptions().position(address).title(location2[0]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        float zoomLevel = 5.0f;
        addressView = (ListView)findViewById(R.id.listView);
        List<String> suggAddress = new ArrayList<>();
        suggAddress.add(location1[0]);
        suggAddress.add(location2[0]);
        //addAddressToContact(contacts);
        adapter = new ArrayAdapter<String>(this,R.layout.contacts_list_item,R.id.contact_name, suggAddress);
        adapter.notifyDataSetChanged();
        addressView.setAdapter(adapter);
        addressView.setVisibility(View.VISIBLE);
        addressView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = addressView.getItemAtPosition(position);
                String address = (String) o;
                HangoutPlanner hangoutPlanner = (HangoutPlanner)getApplicationContext();
                hangoutPlanner.setSelectedAddress(address);
                hangoutPlanner.setLatLon(geometries.get(position+2).getCoOrdinate().getLat() + ":" + geometries.get(position+2).getCoOrdinate().getLon());
                Intent intent = new Intent(getApplication(), AddFavouritesActivity.class);
                startActivity(intent);
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
            String[] latLonArray = latLon.split(":");
            CoOrdinate coOrdinate = new CoOrdinate(latLonArray[0],latLonArray[1]);
            Geometry geometry = new Geometry(coOrdinate);
            geometries.add(geometry);
            System.out.println("latLon = " + latLon);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
    }
}
