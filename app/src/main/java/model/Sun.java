package model;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class Sun {
    private String rise;
    private String set;

    public Sun(String rise, String set) {
        this.rise = rise;
        this.set = set;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
