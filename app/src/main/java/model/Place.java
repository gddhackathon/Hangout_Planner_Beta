package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class Place implements Comparable, Serializable {

    private Geometry geometry;
    private String icon;
    private String name;
    private String place_id;
    private String rating;
    private String address;
    private String priceLevel;
    private String openNow;
    private List<String> types;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private String internationalPhoneNumber;
    private List<String> openWhen;
    private List<Photo> photos;
    private List<Review> reviews;
    private String placeURL;
    private String placeWebSite;


    public int compareTo(Object obj){
        Place place=(Place)obj;
        if(place_id.equals(place.place_id))
            return 0;
        else
            return -1;
    }

    @Override
    public boolean equals(Object place) {
        if ((place instanceof Place) && (((Place) place).getPlace_id().equalsIgnoreCase(place_id))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return result = prime * result + ((place_id == null) ? 0 : place_id.hashCode());
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public Place(){

    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getOpenNow() {
        return openNow;
    }

    public void setOpenNow(String openNow) {
        this.openNow = openNow;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public List<String> getOpenWhen() {
        return openWhen;
    }

    public void setOpenWhen(List<String> openWhen) {
        this.openWhen = openWhen;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPlaceURL() {
        return placeURL;
    }

    public void setPlaceURL(String placeURL) {
        this.placeURL = placeURL;
    }

    public String getPlaceWebSite() {
        return placeWebSite;
    }

    public void setPlaceWebSite(String placeWebSite) {
        this.placeWebSite = placeWebSite;
    }
}
