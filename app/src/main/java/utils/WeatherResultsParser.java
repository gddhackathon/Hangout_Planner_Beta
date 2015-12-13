package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.CoOrdinate;
import model.Direction;
import model.Geometry;
import model.Humidity;
import model.Place;
import model.Pressure;
import model.Speed;
import model.Temperature;
import model.Weather;
import model.Wind;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class WeatherResultsParser {

    public static Weather parseWeatherResults(final String response) {
        Weather weather = new Weather();
        try {
            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);
            City city = new City();
            weather.setCity(city);
            if (jsonObject.has("list")) {

                JSONArray results = jsonObject.getJSONArray("list");

                for (int i = 0; i < results.length(); i++) {

                    if (results.getJSONObject(i).has("id")) {
                        weather.getCity().setId(results.getJSONObject(i).optString("id"));
                    }
                    if (results.getJSONObject(i).has("name")) {
                        weather.getCity().setName(results.getJSONObject(i).optString("name"));
                    }
                    if (results.getJSONObject(i).has("coord")) {
                        JSONObject geometry = results.getJSONObject(i).getJSONObject("coord");
                        setGeometry(geometry, weather);
                    }
                    if (results.getJSONObject(i).has("main")) {
                        JSONObject main = results.getJSONObject(i).getJSONObject("main");
                        getMain(main, weather);
                    }
                    if (results.getJSONObject(i).has("wind")) {
                        JSONObject main = results.getJSONObject(i).getJSONObject("wind");
                        setWind(main, weather);
                    }
                    if (results.getJSONObject(i).has("weather")) {
                        JSONArray types = results.getJSONObject(i).getJSONArray("weather");
                        setWeather(types, weather);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return weather;
    }

    private static void setGeometry(JSONObject geometry, Weather weather){
        CoOrdinate coOrdinate = new CoOrdinate();
        coOrdinate.setLat(geometry.optString("lat"));
        coOrdinate.setLat(geometry.optString("lng"));
        weather.getCity().setCoOrdinate(coOrdinate);
    }

    private static void setWind(JSONObject geometry, Weather weather){
        Speed speed = new Speed();
        Direction direction = new Direction();
        speed.setValue(geometry.optString("speed"));
        direction.setValue(geometry.optString("deg"));
        Wind wind = new Wind(speed,direction);
        weather.setWind(wind);
    }

    private static void getMain(JSONObject main, Weather weather){
        Temperature temperature = new Temperature();
        temperature.setCurrent(main.optString("temp"));
        temperature.setMin(main.optString("temp_min"));
        temperature.setMin(main.optString("temp_max"));
        temperature.setUnit("fahrenheit");
        Humidity humidity = new Humidity();
        humidity.setValue(main.optString("humidity"));
        humidity.setUnit("%");
        Pressure pressure = new Pressure();
        pressure.setValue(main.optString("pressure"));
        pressure.setUnit("hPa");
        weather.setHumidity(humidity);
        weather.setPressure(pressure);
        weather.setTemperature(temperature);
    }

    private static void setWeather(JSONArray types, Weather weather){
        List<String> interestTypes = new ArrayList<String>();
        try {
            JSONObject weatherInfo = types.getJSONObject(0);
            weather.setDescription(weatherInfo.optString("description"));
            weather.setIcon("http://openweathermap.org/img/w/" + weatherInfo.optString("icon") + ".png");
            System.out.println();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
