package model;

import java.io.Serializable;


public class Precipitation implements Serializable {

    public Precipitation(String mode){
        this.mode = mode;
    }
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
