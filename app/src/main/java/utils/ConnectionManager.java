package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AchsahSiri on 12/11/2015.
 */
public class ConnectionManager {

    public InputStream getInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        // Starts the query
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
