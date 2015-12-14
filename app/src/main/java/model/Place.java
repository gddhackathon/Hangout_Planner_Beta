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
    public Place(Geometry geometry, String icon, String name, String place_id, String rating, String address
                    ,String priceLevel, String openNow, List<String> types) {
        this.geometry = geometry;
        this.icon = icon;
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.address = address;
        this.priceLevel = priceLevel;
        this.openNow = openNow;
        this.types = types;
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
}
