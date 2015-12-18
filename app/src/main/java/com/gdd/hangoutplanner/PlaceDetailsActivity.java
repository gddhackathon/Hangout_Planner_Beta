package com.gdd.hangoutplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.gdd.hangoutplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.HangoutPlanner;
import model.Photo;
import model.Place;
import model.Review;
import utils.HangoutPlannerUtil;
import utils.ImageDownloaderTask;

public class PlaceDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ShareActionProvider share=null;
    private Intent mShareIntent;
    private GoogleMap mMap;
    private Place place;
    private int currImage = 0;
    private int totalIamge;
    final String GOOGLE_KEY = HangoutPlannerUtil.GOOGLE_KEY;
    private LruCache<String, Bitmap> mMemoryCache;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Place Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
         place  = (Place)getIntent().getExtras().get("destination");
        toolbar.setTitle(place.getName());
        toolbar.setSubtitle(place.getFormattedAddress());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.placeMap);
        mapFragment.getMapAsync(this);

        //For Sharing
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "Name : " + place.getName() + "\n"
                + "Address : " + place.getFormattedAddress() + "\n"
                + "Phone : " + place.getFormattedPhoneNumber() + "\n"
                + "Web : " + place.getPlaceWebSite() + "\n"
                + "Rating : " + place.getRating());

        //Loading Data onto page.
        TextView phone = (TextView) findViewById(R.id.textView7);
        phone.setText(place.getFormattedPhoneNumber());
        TextView intlPhone = (TextView) findViewById(R.id.textView7);
        intlPhone.setText(place.getInternationalPhoneNumber());
        TextView website = (TextView) findViewById(R.id.textView9);
        website.setText(place.getPlaceWebSite());
        TextView open = (TextView) findViewById(R.id.textView14);
        open.setText(place.getOpenNow());
        updateTextColor(open);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        initializeImageSwitcher();
        setInitialImage();
        setImageRotateListener();
        setOpenHours();
        setReviews();
        setRating();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Snackbar.make(view, "Yet to Implement Direction", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    private void setRating() {
        if(place.getRating() != null || ("".equalsIgnoreCase(place.getRating()))) {
            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            ratingBar.setRating(Float.valueOf(place.getRating()));
        }
    }

    private void setReviews() {
        TextView textView = (TextView) findViewById(R.id.textView18);
        List<Review> reviews = place.getReviews();
        if(null != place.getReviews()){
            int temp=1;
            String reviewString="";
            for(Review review : reviews){
                reviewString =reviewString +"\n"+ "Name : " + review.getAuthorName()+"\n"+
                               review.getText() + "\n" +
                        "Rating : " + review.getRating()+"\n" +
                "                          ---------------------------------------------------------------------------                           ";
                if(temp == 4)
                    break;
                temp++;
            }
            textView.setText(reviewString);
        }
    }

    private void setOpenHours() {
        TextView openWhen = (TextView) findViewById(R.id.textView13);
        if(null != place.getOpenWhen()) {
            String time="";
            List<String> timing = place.getOpenWhen();
            for (String opentiming : timing) {
                time = time + "\n" + opentiming;
            }
            openWhen.setText(time);
        }
    }

    private void updateTextColor(TextView open) {
        if(Boolean.valueOf(place.getOpenNow())){
            open.setText("Open Now");
            open.setTextColor(Color.GREEN);
        }
        else{
            open.setText("Closed");
            open.setTextColor(Color.RED);
        }
    }

    private void initializeImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(PlaceDetailsActivity.this);
                imageView.setMinimumWidth(600);
                imageView.setMinimumHeight(600);
                imageView.setMaxWidth(1600);
                imageView.setMaxHeight(5000);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    private void setImageRotateListener() {
        final Button rotatebutton = (Button) findViewById(R.id.NextImage);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage++;
                if (currImage == place.getPhotos().size()) {
                    currImage = 0;
                }
                setCurrentImage();
            }
        });
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask((ImageView)imageSwitcher.getCurrentView(),mMemoryCache);
        String photoReference;
        if(null != place.getPhotos()) {
            photoReference = place.getPhotos().get(currImage).getPhotoReference();
        }
        else{
            //Random Google Image Reference
            photoReference ="CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU";
        }
        String photoWidth = "600";//place.getPhotos().get(currImage).getWidth();
        String photoHeight = "600";//place.getPhotos().get(currImage).getHeight();
        String googleImageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth="+photoWidth+"&maxheight="+photoHeight+"&photoreference="+photoReference+"&key="+GOOGLE_KEY;
        imageDownloaderTask.execute(googleImageURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_places, menu);
        MenuItem item=menu.findItem(R.id.share);
        share=(ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (share != null) {
            share.setShareIntent(mShareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final ArrayList<Place> selectedInterestVsPlaces = (ArrayList<Place>) getIntent().getSerializableExtra("selectedInterestVsPlaces");
                Intent intent = new Intent(getApplication(), DisplayPlacesActivity.class);
                intent.putExtra("selectedInterestVsPlaces", (ArrayList)selectedInterestVsPlaces);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.valueOf(place.getGeometry().getCoOrdinate().getLat());
        double longitude = Double.valueOf(place.getGeometry().getCoOrdinate().getLon());
        LatLng address = new LatLng(lat, longitude);
        Marker marker= mMap.addMarker(new MarkerOptions().position(address).title(place.getName() +" ( "+place.getFormattedAddress()+" )"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        float zoomLevel = 6.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, zoomLevel));
        marker.showInfoWindow();
    }


}
