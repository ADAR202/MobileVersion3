package org.me.gcu.mobileversion3;

//
// Name - Abdul Qadir Dar
// Student ID - S1511786
// Programme of Study - Computing Bsc
//

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;


public class filterActivity extends Activity {

    TextView title, description, link, pubDate, geoLat, geoLong;
    Spinner spinner;

    private static final String MyTag = "filterActivity";
    String sTitle = null;
    String sDescription = null;
    String sLink = null;
    String spubDate = null;
    String sgeoLat = null;
    String smagnitude = null;

   // String myList;

    ArrayList<String> myList;
    ArrayList<String> sgeoLong;
    String lastMagnitude;


    //aelected item is the item from the spinner

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("geoLat");
        final ArrayList<String> myListLong = (ArrayList<String>) getIntent().getSerializableExtra("geoLong");

        final ArrayList<String> myListMagnitude = (ArrayList<String>) getIntent().getSerializableExtra("magnitude");
        final ArrayList<String> myListLongDepth = (ArrayList<String>) getIntent().getSerializableExtra("depth");







this.myList = myList;
        setContentView(R.layout.filter_xml);

        Bundle extras = getIntent().getExtras();

        Intent intent = getIntent();
        custObj newCustObj = intent.getParcelableExtra("data");


//        No longer using custom object

//        String sdateTime = newCustObj.getDateTime();
//        String slocation = newCustObj.getLocation();
//        String slatitude = newCustObj.getLatitude();
//        String slongitude = newCustObj.getLongitude();
//        String sdepth = newCustObj.getDepth();
//        String smagnitude = newCustObj.getMagnitude();







//        String sTitle = null;
//        String sDescription = null;
//        String sLink = null;
//        String spubDate = null;
//        String sgeoLat = null;
//        String sgeoLong = null;

        if (extras != null) {

            sTitle = extras.getString("title");
            sDescription = extras.getString("description");
            sLink = extras.getString("link");
            spubDate = extras.getString("pubDate");
            sgeoLat = extras.getString("geoLat");



        }
        Log.e(MyTag, "rthernmost latitude i myList  " + myList);
        Log.e(MyTag, "rthernmost latitude i   " + title);

//        TextView dateTimeDisplay = findViewById(R.id.location);
//        TextView locationDisplay = findViewById(R.id.location);
        final TextView mostNorthItem = findViewById(R.id.mostNorth);
        final TextView southernMostItem = findViewById(R.id.mostSouth);

        final TextView mostEasthItem = findViewById(R.id.mostEast);
        final TextView westernMostItem = findViewById(R.id.mostWest);


        final TextView deepestDepth = findViewById(R.id.deepest);
        final TextView shallowestDepth = findViewById(R.id.shallowest);


        final TextView smallestMagnitude = findViewById(R.id.smallest);
        final TextView heighestMagnitude = findViewById(R.id.heighest);
        //        TextView longitudeDisplay = findViewById(R.id.location);
//        TextView depthDisplay = findViewById(R.id.location);
//        TextView magnitudeDisplay = findViewById(R.id.location);


        spinner = findViewById(R.id.MySpinner);
        ArrayAdapter<CharSequence>spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.Spinner2, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerText = parent.getItemAtPosition(position).toString();

                if (spinnerText.equalsIgnoreCase("north")){
                    double biggestItem = 0;
            for(String item: myList){


                double currentItem = parseDouble(item);
                Log.e(MyTag, "rthernmost latitude i   " + biggestItem);
                if (biggestItem < currentItem) {
                    biggestItem = currentItem;

                    mostNorthItem.setText("The northern most latitude is: "+ biggestItem);

                }

            }
        }else if (spinnerText.equalsIgnoreCase("south")){
                    double lowItem = 99999;
                    for(String item: myList){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2<lowItem){
                            lowItem = currentItem2;

                            southernMostItem.setText("The suthernest latitude is: "+ lowItem);
                        }
                    }
                }else if (spinnerText.equalsIgnoreCase("east")){
                    double eastItem = 0;
                    for(String item: myListLong){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 > eastItem){
                            eastItem = currentItem2;

                            mostEasthItem.setText("The most Eastern latitude is: "+ eastItem);
                        }
                    }
                }else if (spinnerText.equalsIgnoreCase("west")){
                    double westItem = 99999;
                    for(String item: myListLong){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 < westItem){
                            westItem = currentItem2;

                            westernMostItem.setText("The most western latitude is: "+ westItem);
                        }
                    }
                }

                else if (spinnerText.equalsIgnoreCase("deepest")){
                    double deepItem = 0;
                    for(String item: myListLongDepth){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 > deepItem){
                            deepItem = currentItem2;

                            deepestDepth.setText("The deepest depth is: "+ deepItem);
                        }
                    }
                }  else if (spinnerText.equalsIgnoreCase("shallowest")){
                    double shallowItem = 9999;
                    for(String item: myListLongDepth){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 < shallowItem){
                            shallowItem = currentItem2;

                            shallowestDepth.setText("The most shallow depth is: "+ shallowItem);
                        }
                    }
                }else if (spinnerText.equalsIgnoreCase("largest")){
                    double heighestItem = 0;
                    for(String item: myListMagnitude){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 > heighestItem){
                            heighestItem = currentItem2;

                            heighestMagnitude.setText("The heighest magnitude is: "+ heighestItem);
                        }
                    }
                }else if (spinnerText.equalsIgnoreCase("smallest")){
                    double smallestItem = 9999;
                    for(String item: myListMagnitude){


                        double currentItem2 = parseDouble(item);
                        if (currentItem2 < smallestItem){
                            smallestItem = currentItem2;

                            smallestMagnitude.setText("The lowest magnitude is: "+ smallestItem);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }




}
