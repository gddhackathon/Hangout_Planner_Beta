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
        googleTypeMapping.put("bar", "bar");
        googleTypeMapping.put("movies", "movie_theater");
        googleTypeMapping.put("hotel", "lodging");
        googleTypeMapping.put("shopping", "shopping_mall");
        googleTypeMapping.put("localmall", "shopping_mall");
        googleTypeMapping.put("food", "food");
        googleTypeMapping.put("cafe", "cafe");
        return googleTypeMapping;
    }

}