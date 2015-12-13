package model;

import java.io.Serializable;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class City implements Serializable {
    private String id;
    private String name;
    private CoOrdinate coOrdinate;
    private Country country;
    private Sun sun;

    public City(){

    }

    public City(String id, String name, CoOrdinate coOrdinate, Country country, Sun sun) {
        this.id = id;
        this.name = name;
        this.coOrdinate = coOrdinate;
        this.country = country;
        this.sun = sun;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoOrdinate getCoOrdinate() {
        return coOrdinate;
    }

    public void setCoOrdinate(CoOrdinate coOrdinate) {
        this.coOrdinate = coOrdinate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }
}
