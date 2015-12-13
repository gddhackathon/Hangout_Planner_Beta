package model;

import java.io.Serializable;


public class Geometry implements Serializable {
    public Geometry(CoOrdinate coOrdinate) {
        this.coOrdinate = coOrdinate;
    }

    public CoOrdinate getCoOrdinate() {
        return coOrdinate;
    }

    public void setCoOrdinate(CoOrdinate coOrdinate) {
        this.coOrdinate = coOrdinate;
    }

    private CoOrdinate coOrdinate;
}
