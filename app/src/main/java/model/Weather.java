package model;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class Weather {
    private String description;
    private String icon;
    private City city;
    private Temperature temperature;
    private Humidity humidity;
    private Pressure pressure;
    private Wind wind;
    private Clouds clouds;
    private Precipitation precipitation;

    public Weather(){

    }
    public Weather(String description, String icon, City city, Temperature temperature,
                   Humidity humidity, Pressure pressure, Wind wind, Clouds clouds, Precipitation precipitation){
        this.description = description;
        this.icon = icon;
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.clouds = clouds;
        this.precipitation = precipitation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Precipitation precipitation) {this.precipitation = precipitation;
    }

}
