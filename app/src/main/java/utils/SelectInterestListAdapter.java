package utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.gdd.hangoutplanner.R;

import java.util.ArrayList;

import model.Interest;

/**
 * Created by Khatravath on 12/13/2015.
 */
public class SelectInterestListAdapter extends BaseAdapter {
    private ArrayList<Interest> listData;
    private LayoutInflater layoutInflater;

    public SelectInterestListAdapter(Context aContext, ArrayList<Interest> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.interests_list_item, parent, false);
            holder = new ViewHolder();
            holder.imagePlace = (ImageView) convertView.findViewById(R.id.imageViewInterest);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       if(listData.get(position).getName().equalsIgnoreCase("restaurant")){
           holder.imagePlace.setImageResource(R.drawable.restaurant);
       }
        if(listData.get(position).getName().equalsIgnoreCase("cafe")){
            holder.imagePlace.setImageResource(R.drawable.cafe);
        }
        if(listData.get(position).getName().equalsIgnoreCase("movie_theater")){
            holder.imagePlace.setImageResource(R.drawable.movie_theater);
        }
        if(listData.get(position).getName().equalsIgnoreCase("bar")){
            holder.imagePlace.setImageResource(R.drawable.bar);
        }
        if(listData.get(position).getName().equalsIgnoreCase("amusement_park")){
            holder.imagePlace.setImageResource(R.drawable.amusement_park);
        }
        if(listData.get(position).getName().equalsIgnoreCase("art_gallery")){
            holder.imagePlace.setImageResource(R.drawable.art_gallery);
        }
        if(listData.get(position).getName().equalsIgnoreCase("casino")){
            holder.imagePlace.setImageResource(R.drawable.casino);
        }
        if(listData.get(position).getName().equalsIgnoreCase("campground")){
            holder.imagePlace.setImageResource(R.drawable.campground);
        }
        if(listData.get(position).getName().equalsIgnoreCase("museum")){
            holder.imagePlace.setImageResource(R.drawable.museum);
        }
        if(listData.get(position).getName().equalsIgnoreCase("night_club")){
            holder.imagePlace.setImageResource(R.drawable.night_clubs);
        }
        if(listData.get(position).getName().equalsIgnoreCase("park")){
            holder.imagePlace.setImageResource(R.drawable.park);
        }
        if(listData.get(position).getName().equalsIgnoreCase("shopping_mall")){
            holder.imagePlace.setImageResource(R.drawable.shopping_mall);
        }
        if(listData.get(position).getName().equalsIgnoreCase("spa")){
            holder.imagePlace.setImageResource(R.drawable.spa);
        }
        if(listData.get(position).getName().equalsIgnoreCase("aquarium")){
            holder.imagePlace.setImageResource(R.drawable.aquarium);
        }
        if(listData.get(position).getName().equalsIgnoreCase("zoo")){
            holder.imagePlace.setImageResource(R.drawable.zoo);
        }
        if(listData.get(position).getName().equalsIgnoreCase("tourist attractions")){
            holder.imagePlace.setImageResource(R.drawable.tourist_attractions);
        }
        return convertView;
    }

    static class ViewHolder {
        CheckBox checkBox;
        ImageView imagePlace;
    }
}