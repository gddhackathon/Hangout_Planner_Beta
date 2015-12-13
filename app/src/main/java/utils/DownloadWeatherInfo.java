package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Weather;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class DownloadWeatherInfo {

    public static Weather makeCall(String url) {
        Weather weather = null;
        try {
           StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(new ConnectionManager().getInputStream(url)));
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
            weather =  WeatherResultsParser.parseWeatherResults(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }
}
