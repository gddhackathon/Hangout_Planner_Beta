package utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gdd.hangoutplanner.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Place;

/**
 * Created by Khatravath on 12/13/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Place> listData;
    private LayoutInflater layoutInflater;
    private LruCache<String, Bitmap> mMemoryCache;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;

    public CustomListAdapter(Context aContext, ArrayList<Place> listData) {
        if(null == mMemoryCache){
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
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
            holder.typesView = (TextView) convertView.findViewById(R.id.types);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            holder.address = (TextView) convertView.findViewById(R.id.textViewAddress);

            LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

            holder.openNow = (TextView) convertView.findViewById(R.id.textViewOpenNow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.imagePlace != null) {
            String imageURL = listData.get(position).getIcon();
            ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(holder.imagePlace,mMemoryCache);
            Bitmap bitmap = getBitmapFromMemCache(imageURL);
            if(null != bitmap){
                System.out.println("Loading from cache");
                holder.imagePlace.setImageBitmap(bitmap);
            }
            else {
                imageDownloaderTask.execute(imageURL);
            }
        }
        holder.address.setText(listData.get(position).getAddress());
        setColorToOpenNowText(position, holder);
        holder.headlineView.setText(listData.get(position).getName());
        holder.reporterNameView.setText(listData.get(position).getAddress());
        holder.typesView.setText(getTypes(listData.get(position).getTypes()));
        if( null !=(listData.get(position).getRating()))
        holder.rating.setRating(Float.valueOf(listData.get(position).getRating()));
        return convertView;
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private void setColorToOpenNowText(int position, ViewHolder holder) {
        if(Boolean.valueOf(listData.get(position).getOpenNow())){
            holder.openNow.setText("Open Now");
            holder.openNow.setTextColor(Color.GREEN);
        }
        else{
            holder.openNow.setText("Closed");
            holder.openNow.setTextColor(Color.RED);
        }
    }

    private String getTypes(List<String> googleTypes){
        StringBuilder type = new StringBuilder("#Types:");
        List<String> applicationInterests = Arrays.asList("bar", "cafe", "food", "shopping_mall",
                        "restaurant", "place_of_worship", "museum", "park");
        for(String applicationInterest : applicationInterests){
            if(googleTypes.contains(applicationInterest)){
                type.append(" ").append(applicationInterest).append(",");
            }
        }
        return  type.lastIndexOf(",") == -1 ?  type.toString() :type.deleteCharAt(type.lastIndexOf(",")).toString();
    }

    static class ViewHolder {
        ImageView imagePlace;
        TextView headlineView;
        TextView typesView;
        TextView reporterNameView;
        RatingBar rating;
        TextView openNow;
        TextView address;
    }

}