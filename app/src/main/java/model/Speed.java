package model;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class Speed {
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
