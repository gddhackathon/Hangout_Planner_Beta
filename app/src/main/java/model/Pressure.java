package model;

import java.io.Serializable;


public class Pressure  implements Serializable {

    public Pressure(){

    }
    public Pressure(String value, String unit){
        this.unit = unit;
        this.value = value;
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
