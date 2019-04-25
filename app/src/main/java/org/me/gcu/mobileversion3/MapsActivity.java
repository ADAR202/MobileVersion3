package org.me.gcu.mobileversion3;

//
// Name - Abdul Qadir Dar
// Student ID - S1511786
// Programme of Study - Computing Bsc
//

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Double.parseDouble;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView title, description, link, pubDate, geoLat, geoLong, TVmagnitude;

    private GoogleMap mMap;
    double latitude, longitude, finalMagnitudez;
    double magnitude;

    String sTitle = null;
    String sDescription = null;
    String sLink = null;
    String spubDate = null;
    String sgeoLat = null;
    String sgeoLong = null;
    String lastMagnitude;
    String smagnitude;



    private static final String MyTag = "MapsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Bundle extras = getIntent().getExtras();



        if (extras != null) {

            sTitle = extras.getString("title");
            sDescription = extras.getString("description");
            sLink = extras.getString("link");
            spubDate = extras.getString("pubDate");
            sgeoLat = extras.getString("geoLat");
            sgeoLong = extras.getString("geoLong");

//            smagnitude = extras.getString("magnitude");

//            magnitude = parseDouble(smagnitude);

            latitude = parseDouble(sgeoLat);
            longitude = parseDouble(sgeoLong);
        }

        title = findViewById(R.id.title);
        title.setText("Title is: " + "\n" + sTitle + "\n");

        description = findViewById(R.id.description);
        description.setText("Description is: " + "\n" + sDescription + "\n");

        link = findViewById(R.id.link);
        link.setText("Link: " + "\n" + sLink + "\n");

//        pubDate = findViewById(R.id.pubDate);
//        pubDate.setText("Publish Date: " + "\n" + spubDate + "\n");


//      pubDate, Latitude and longitude are in description so not needed to be displayed seperatly

//        geoLat = findViewById(R.id.geoLat);
//        geoLat.setText("Latitude: " + "\n" + sgeoLat + "\n");
//
//        geoLong = findViewById(R.id.geoLong);
//        geoLong.setText("Longitude: " + "\n" + sgeoLong + "\n");
//
//        pubDate = findViewById(R.id.pubDate);
//        pubDate.setText("Publish date is: " + "\n" + spubDate + "\n");

//        TVmagnitude = findViewById(R.id.magnitude);
//        TVmagnitude.setText("magnitude: " + "\n" + smagnitude + "\n");



//        Log.e(MyTag, "msg "+ finalMagnitude);
//        if (finalMagnitude >= 1.0 ) {
//            GradientDrawable gr = (GradientDrawable) findViewById(R.id.boxMagnitude).getBackground();
//            gr.setColor(Color.parseColor("#ff5100"));
//        }
//        else {
//
//            GradientDrawable gr = (GradientDrawable) findViewById(R.id.boxMagnitude).getBackground();
//            gr.setColor(Color.parseColor("#449c00"));
//        }


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String info = sTitle;
        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(pos).title(info));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 8));

    }
}
