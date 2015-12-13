package model;


import java.io.Serializable;

public class Temperature implements Serializable {
    private String current;
    private String min;
    private String max;
    private String unit;

    public Temperature(){

    }

    public Temperature(String current, String min, String max, String unit) {
        System.out.println(current + min + max + unit);
        this.current = current;
        this.min = min;
        this.max = max;
        this.unit = unit;
    }


    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
