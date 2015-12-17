package utils;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CoOrdinate;
import model.Geometry;
import model.Photo;
import model.Place;
import model.Review;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class GooglePlacesResultsParser {

    public static List<Place> parseGooglePlaceSearchResults(final String response) {

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
                    if(results.getJSONObject(i).has("opening_hours")){
                        JSONObject openingHours = results.getJSONObject(i).getJSONObject("opening_hours");
                        place.setOpenNow(getOpenNow(openingHours));
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

    public static Place parseGooglePlaceDetailsResults(final String response) {

        Place place = new  Place();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("result")) {

                JSONObject result = jsonObject.getJSONObject("result");
                    if (result.has("formatted_address")) {
                        place.setFormattedAddress(result.optString("formatted_address"));
                    }
                    if (result.has("formatted_phone_number")) {
                        place.setFormattedPhoneNumber(result.optString("formatted_phone_number"));
                    }
                    if (result.has("international_phone_number")) {
                        place.setInternationalPhoneNumber(result.optString("international_phone_number"));
                    }
                    if (result.has("name")) {
                        place.setName(result.optString("name"));
                    }
                    if(result.has("opening_hours")){
                        JSONObject openingHours = result.getJSONObject("opening_hours");
                        place.setOpenNow(getOpenNow(openingHours));
                        place.setOpenWhen(getWeekdayText(openingHours));
                    }
                    if (result.has("place_id")) {
                        place.setPlace_id(result.optString("place_id"));
                    }
                    if (result.has("geometry")) {
                        JSONObject geometry = result.getJSONObject("geometry");
                        place.setGeometry(getGeometry(geometry));
                    }
                    if (result.has("photos")) {
                        JSONArray types = result.getJSONArray("photos");
                        place.setPhotos(getPhotos(types));
                    }
                    if (result.has("icon")) {
                        place.setIcon(result.optString("icon"));
                    }
                    if (result.has("price_level")) {
                        place.setPriceLevel(getPriceLevel(result.optString("price_level")));
                    }
                    if (result.has("reference")) {
                        place.setPhotoReference(result.optString("reference"));
                    }
                    if (result.has("rating")) {
                        place.setRating(result.optString("rating"));
                    }
                    if (result.has("reviews")) {
                        JSONArray types = result.getJSONArray("reviews");
                        place.setReviews(getReviews(types));
                    }
                    if (result.has("vicinity")) {
                        place.setAddress(result.optString("vicinity"));
                    }
                    if (result.has("types")) {
                        JSONArray types = result.getJSONArray("types");
                        place.setTypes(getTypes(types));
                    }
                    if (result.has("url")) {
                        place.setPlaceURL(result.optString("url"));
                    }
                    if (result.has("website")) {
                        place.setPlaceWebSite(result.optString("website"));
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return place;
        }
        return place;
    }

    private static Geometry getGeometry(JSONObject geometry){
        CoOrdinate coOrdinate = null;
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

    private static List<Photo> getPhotos(JSONArray types){
        List<Photo> photos = new ArrayList<>();
        for(int i = 0; i < types.length(); i++){
            Photo photo = new Photo();
            try {
                photo.setHeight(types.getJSONObject(i).getString("height"));
                photo.setWidth(types.getJSONObject(i).getString("width"));
                photo.setPhotoReference(types.getJSONObject(i).getString("photo_reference"));
                photos.add(photo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return photos;
    }

    private static List<Review> getReviews(JSONArray types){
        List<Review> reviews = new ArrayList<>();
        for(int i = 0; i < types.length(); i++){
            Review review = new Review();
            try {
                review.setAuthorName(types.getJSONObject(i).getString("author_name"));
                review.setRating(types.getJSONObject(i).getString("rating"));
                review.setProfilePhotoUrl(types.getJSONObject(i).getString("profile_photo_url"));
                review.setText(types.getJSONObject(i).getString("text"));
                reviews.add(review);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    private static String getOpenNow(JSONObject openingHours){
        String openNow = "No Data";
        if(openingHours.has("open_now")){
                openNow = openingHours.optString("open_now");
        }
        return openNow;
    }

    private static List<String> getWeekdayText(JSONObject openingHours){
        List<String> openWhen = new ArrayList<>();
        try {
            if (openingHours.has("weekday_text")) {
                JSONArray types = openingHours.getJSONArray("weekday_text");
                openWhen = getTypes(types);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return openWhen;
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

    private static String getPriceLevel(String priveLevel){
        Map<String,String> priceLevelMapping = new HashMap<>();
        priceLevelMapping.put("0","Free");
        priceLevelMapping.put("1","Inexpensive");
        priceLevelMapping.put("2","Moderate");
        priceLevelMapping.put("3","Expensive");
        priceLevelMapping.put("4","Very Expensive");
        return priceLevelMapping.get(priveLevel);
    }

}