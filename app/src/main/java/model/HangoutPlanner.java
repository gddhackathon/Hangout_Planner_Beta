package model;

import android.app.Application;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by AchsahSiri on 12/14/2015.
 */
public class HangoutPlanner extends Application {

    private String latLon;
    private String selectedAddress;
    private List<String> selectedInteresets = new ArrayList<>();

    private Map<String, String> googleTypesMapping;

    public HangoutPlanner(){
        googleTypesMapping = getGoogleHanoutMapping();
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public List<String> getSelectedInteresets() {
        return selectedInteresets;
    }

    public void setSelectedInteresets(List<String> selectedInteresets) {
        this.selectedInteresets = selectedInteresets;
    }

    public Map<String, String> getGoogleTypesMapping() {
        return googleTypesMapping;
    }

    public void setGoogleTypesMapping(Map<String, String> googleTypesMapping) {
        this.googleTypesMapping = googleTypesMapping;
    }

    private Map<String, String> getGoogleHanoutMapping(){
        Map<String, String> googleTypeMapping = new HashMap<>();
        googleTypeMapping.put("Restaurant", "restaurant");
        googleTypeMapping.put("Cafe", "cafe");
        googleTypeMapping.put("Movie theater", "movie_theater");
        googleTypeMapping.put("Bar", "bar");
        googleTypeMapping.put("Amusement park", "amusement_park");
        googleTypeMapping.put("Art gallery", "art_gallery");
        googleTypeMapping.put("Casino", "casino");
        googleTypeMapping.put("Camp ground", "campground");
        googleTypeMapping.put("Museum", "museum");
        googleTypeMapping.put("Night clubs", "night_club");
        googleTypeMapping.put("Park", "park");
        googleTypeMapping.put("Shopping mall", "shopping_mall");
        googleTypeMapping.put("Spa", "spa");
        googleTypeMapping.put("Aquarium", "aquarium");
        googleTypeMapping.put("zoo", "zoo");
        googleTypeMapping.put("Tourist attractions", "tourist attractions");
        return googleTypeMapping;
    }

}
