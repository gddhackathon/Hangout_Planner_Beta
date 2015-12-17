package model;

import java.io.Serializable;

public class CoOrdinate  implements Serializable {

    private String lat;
    private String lon;

    public CoOrdinate(){

    }

    public CoOrdinate(String lat, String lon) {
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

