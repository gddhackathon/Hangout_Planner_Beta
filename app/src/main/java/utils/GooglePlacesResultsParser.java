package utils;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.CoOrdinate;
import model.Geometry;
import model.Place;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class GooglePlacesResultsParser {

    public static List<Place> parseGoogleParse(final String response) {

        List<Place> places = new ArrayList<Place>();

        try {
            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("results")) {

                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    Place place = new Place();

                    if (results.getJSONObject(i).has("name")) {
                        place.setName(results.getJSONObject(i).optString("name"));
                    }
                    if (results.getJSONObject(i).has("geometry")) {
                        JSONObject geometry = results.getJSONObject(i).getJSONObject("geometry");
                        place.setGeometry(getGeometry(geometry));
                    }
                    if (results.getJSONObject(i).has("icon")) {
                        place.setIcon(results.getJSONObject(i).optString("icon"));
                    }
                    if (results.getJSONObject(i).has("place_id")) {
                        place.setPlace_id(results.getJSONObject(i).optString("place_id"));
                    }
                    if (results.getJSONObject(i).has("rating")) {
                        place.setRating(results.getJSONObject(i).optString("rating"));
                    }
                    if (results.getJSONObject(i).has("vicinity")) {
                        place.setAddress(results.getJSONObject(i).optString("vicinity"));
                    }
                    if (results.getJSONObject(i).has("types")) {
                        JSONArray types = results.getJSONObject(i).getJSONArray("types");
                        place.setTypes(getTypes(types));
                    }
                    places.add(place);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Place>();
        }
        return places;
    }

    private static Geometry getGeometry(JSONObject geometry){
        CoOrdinate coOrdinate = null;
        Geometry geometry1 = null;
        if(geometry.has("location")){
            try {
                String lat = geometry.getJSONObject("location").optString("lat");
                String lng = geometry.getJSONObject("location").optString("lng");
                coOrdinate = new CoOrdinate(lat, lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Geometry(coOrdinate);
    }

    private static List<String> getTypes(JSONArray types){
        List<String> interestTypes = new ArrayList<String>();
        for(int i = 0; i < types.length(); i++){
            try {
                interestTypes.add(types.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return interestTypes;
    }

}