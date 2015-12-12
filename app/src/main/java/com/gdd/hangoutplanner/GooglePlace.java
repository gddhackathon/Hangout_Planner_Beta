package com.gdd.hangoutplanner;

public class GooglePlace {

    private String name;
    private String category;
    private String rating;
    private String open;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpenNow() {
        return open;
    }

    public void setOpenNow(String open) {
        this.open = open;
    }

    public GooglePlace() {
        this.name = "";
        this.rating = "";
        this.open = "";
        this.setCategory("");
    }
}
