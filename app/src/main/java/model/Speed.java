package model;


import java.io.Serializable;

public class Speed implements Serializable {
    public Speed(){

    }

    public Speed(String value, String name){
        this.value = value;
        this.name = name;
    }

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
