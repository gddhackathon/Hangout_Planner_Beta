package model;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class Clouds {

    public Clouds(String value, String name){
        this.name = name;
        this.value = value;
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
