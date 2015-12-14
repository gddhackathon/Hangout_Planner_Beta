package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Place;

/**
 * Created by AchsahSiri on 12/13/2015.
 */
public class ShortingLogic {

    private ArrayList<Place> getShortedList(String item, HashMap<String, List<Place>> interestVsPlaces) {
        List<Place> selectedInterestPlaces = new ArrayList<Place>();
        List<ArrayList<Place>> otherInterestPlaces = new ArrayList<ArrayList<Place>>();
        for (Map.Entry<String, List<Place>> places : interestVsPlaces.entrySet()) {
            String interest = places.getKey();
            if(item.equals(interest)){
                selectedInterestPlaces = places.getValue();
            }
            else{
                otherInterestPlaces.add((ArrayList<Place>) places.getValue());
            }
        }
        return sortSelectedInterestPlaces(selectedInterestPlaces, otherInterestPlaces);
    }

    private ArrayList<Place> sortSelectedInterestPlaces(List<Place> selectedInterestPlaces, List<ArrayList<Place>> otherInterestPlaces) {
        HashMap<Place, Integer> selectedInterestPlacesInSortedOrder = new HashMap<Place, Integer>();
        for(Place place :selectedInterestPlaces){
            int currentPlacematchingCount = 1;
            for(ArrayList<Place> placeArrayList : otherInterestPlaces){
                if(placeArrayList.contains(place)){
                    currentPlacematchingCount++;
                }
            }
            selectedInterestPlacesInSortedOrder.put(place, currentPlacematchingCount);
        }
        return sortPlacesInOrder(selectedInterestPlacesInSortedOrder);
    }

    private ArrayList<Place> sortPlacesInOrder(HashMap<Place, Integer> selectedInterestPlacesInSortedOrder){
        List<Map.Entry<Place, Integer>> list = new ArrayList<Map.Entry<Place, Integer>>(selectedInterestPlacesInSortedOrder.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Place, Integer>>() {
            public int compare(Map.Entry<Place, Integer> o1, Map.Entry<Place, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        ArrayList<Place> places = new ArrayList<Place>();
        for(Map.Entry<Place, Integer> entry:list){
            places.add(entry.getKey());
        }
        System.out.println("sorted places");
        for(Place place :places){
            System.out.println("place = " + place.toString());
        }
        return places;
    }
}
