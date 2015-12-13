package model;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class CoOrdinate {

    private String lat;
    private String lon;

    public CoOrdinate(){

    }

    public CoOrdinate(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

}

