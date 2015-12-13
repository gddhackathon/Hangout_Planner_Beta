package model;

import java.io.Serializable;


public class Humidity implements Serializable {
    public Humidity(){

    }
    public Humidity(String value, String unit){
        this.value = value;
        this.unit = unit;
    }

    private String value;
    private String unit;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
