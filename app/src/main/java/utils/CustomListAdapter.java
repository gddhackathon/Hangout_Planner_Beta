package utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gdd.hangoutplanner.R;

import org.apache.commons.codec.binary.StringUtils;

import java.net.URL;
import java.util.ArrayList;

import model.Place;

/**
 * Created by Khatravath on 12/13/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Place> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<Place> listData) {
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
            convertView = layoutInflater.inflate(R.layout.place_list_item, parent, false);
            holder = new ViewHolder();
            holder.imagePlace = (ImageView) convertView.findViewById(R.id.imagePlace);
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.reporter);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

            holder.priceLevel = (TextView) convertView.findViewById(R.id.textViewPricelevel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(listData.get(position).getIcon());
             bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception e){

        }
        if (bmp !=null)
        holder.imagePlace.setImageBitmap(bmp);
        holder.priceLevel.setText(listData.get(position).getOpenNow());
        holder.headlineView.setText(listData.get(position).getName());
        holder.reporterNameView.setText(listData.get(position).getAddress());
        if( null !=(listData.get(position).getRating()))
        holder.rating.setRating(Float.valueOf(listData.get(position).getRating()));
        return convertView;
    }

    static class ViewHolder {
        ImageView imagePlace;
        TextView headlineView;
        TextView reporterNameView;
        RatingBar rating;
        TextView priceLevel;
    }
}