package model;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class Geometry {
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
