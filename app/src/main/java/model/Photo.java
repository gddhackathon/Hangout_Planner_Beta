package model;

import java.io.Serializable;

/**
 * Created by AchsahSiri on 12/17/2015.
 */
public class Photo implements Serializable {

    private String height;
    private String width;
    private String photoReference;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
