package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.Place;

/**
 * Created by AchsahSiri on 12/12/2015.
 */
public class DownloadGooglePlacesInfo
{
        public static List<Place> makeCall(String url) {
            List<Place> places = new ArrayList<Place>();
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(new ConnectionManager().getInputStream(url)));
                String line;
                System.out.println(rd.toString());
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb);
                places = GooglePlacesResultsParser.parseGoogleParse(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return places;
        }
}
