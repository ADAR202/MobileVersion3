package org.me.gcu.mobileversion3;

//
// Name - Abdul Qadir Dar
// Student ID - S1511786
// Programme of Study - Computing Bsc
//

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

//import gcu.mpd.bgsdatastarter.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TextView rawDataDisplay;
    private Button startButton;
    private Button filterButton;
    private String result;
    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    double finalMagnitude;
    double finalDepth;


    private static final String MyTag = "MainActivity";
    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> description;
    ArrayList<String> links;
    ArrayList<String> pubDate;
    ArrayList<String> geoLat;
    ArrayList<String> geoLong;
    ArrayList<String> magnitudeArray;
    ArrayList<String> depthArray;

    ArrayList<custObj> objectList;
    custObj objectItem;

    ArrayList<ParcelObject>parcelObjects;
    ParcelObject parcelObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        filterButton = (Button) findViewById(R.id.filterButton);
        filterButton.setOnClickListener(this);

        lvRss = (ListView) findViewById(R.id.lvRss);
        titles = new ArrayList<String>();
        description = new ArrayList<String>();
        links = new ArrayList<String>();
        pubDate = new ArrayList<String>();
        geoLat = new ArrayList<String>();
        geoLong = new ArrayList<String>();
        magnitudeArray = new ArrayList<>();
        depthArray = new ArrayList<>();
        objectList = new ArrayList<custObj>();
        parcelObjects = new ArrayList<>();


        Log.e(MyTag,"This is the Custom object holding everything: " + objectList);


        filterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
//                Intent intent = new Intent(MainActivity.this,filterActivity.class);
//                intent.putExtra("objectList",objectList.get(position));
//
//
//                startActivity(intent);



                Intent mIntent = new Intent(MainActivity.this, filterActivity.class);

                mIntent.putExtra("title",titles);
                mIntent.putExtra("description",description);
                mIntent.putExtra("link",links);
                mIntent.putExtra("pubDate",pubDate);
                mIntent.putExtra("geoLat",geoLat);
                mIntent.putExtra("geoLong",geoLong);
                mIntent.putExtra("depth",depthArray);
                mIntent.putExtra("magnitude",magnitudeArray);


//                getIntent().putParcelableArrayListExtra(parcelObjects);
                Log.e(MyTag,"itestrun this thing" + objectList);
                startActivity(mIntent);

            }
        });


        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                Log.d(MyTag, "onItemClick description is: "+ description);
//                String[] theSplit = description.get(position).split(":", -1);
//
//                Log.d(MyTag, "onItemClick: "+ theSplit[7]);
//                String[] twoSplit = theSplit[7].split("km",-1);
//                Log.d(MyTag, "onItemClick: this is supposed to be the depth:"+twoSplit[0]);
//
//                String[] getmagArray = theSplit[8].split(" ", -1);
//                String lastMagnitude = getmagArray[3];
//                Log.d(MyTag, "this is thwe mag:"+lastMagnitude);

                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("title",titles.get(position));
                intent.putExtra("description",description.get(position));
                intent.putExtra("link",links.get(position));
                intent.putExtra("pubDate",pubDate.get(position));
                intent.putExtra("geoLat",geoLat.get(position));
                intent.putExtra("geoLong",geoLong.get(position));
                intent.putExtra("magnitude",magnitudeArray.get(position));


                startActivity(intent);
            }
        });




    }



    public void onClick(View aview)
    {


        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }
    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            custObj currentRecord = null;


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
//                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(getInputStream(aurl), "UTF_8");
                boolean insideItem = false;
                int eventType = parser.getEventType(); //loop control variable
//                while ((inputLine = in.readLine()) != null)
//                {
//                    result = result + inputLine;
//                    Log.e("MyTag",inputLine);
//
//                }
//                in.close();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if the tag is called "item"
                        if (parser.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                            currentRecord = new custObj();
                        }
                        //if the tag is called "title"
//                        else if (xpp.getName().equalsIgnoreCase("title"))
//                        {
//                            if (insideItem)
//                            {
//                                // extract the text between <title> and </title>
//                                titles.add(xpp.nextText());
////                                rssItemList.add(rssItemList.getTitle());
//                            }
//                        }
                        else if (parser.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                titles.add(parser.nextText());

                                Log.e(MyTag, "This is whats in the titles arraylist " + titles);

                            }
                        }
                        //if the tag is called "link"
                        else if (parser.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                objectList.add(currentRecord);

//                                String.Description = xpp.getName(description);


                                String descriptionContainer = parser.nextText();
//

                                String[] descriptionSeperator = descriptionContainer.split(";" );


                                String dateAndTimeContainer = descriptionSeperator[0];
                                String[] dateTimeSeperator = dateAndTimeContainer.split(": " );
                                String dateTime = dateTimeSeperator[1];
                                currentRecord.setDateTime(dateAndTimeContainer);

                                String locationContainer = descriptionSeperator[1];
                                String[] locationSeperator = locationContainer.split(":" );
                                String location = locationSeperator[1];
                                currentRecord.setLocation(locationContainer);

                                String latLongContainer = descriptionSeperator[2];
                                String[] latLong2 = latLongContainer.split(":" );
                                String latLongJoined = latLong2[1];
                                String[] latLongSeperator = latLongJoined.split("," );
                                String latitude = latLongSeperator[0];
                                String longitude = latLongSeperator[1];
                                currentRecord.setLatitude(latitude);
                                currentRecord.setLongitude(longitude);



                                String depthContainer = descriptionSeperator[3];
                                String[] depthSeperator = depthContainer.split(":" );
                                String depth = depthSeperator[1];
                                currentRecord.setDepth(depthContainer);



                                String[] example = depth.split(" ", -1);

                                finalDepth = parseDouble(example[1].trim());
//                                Log.e(MyTag, "msg depth "+ finalDepth);

                                depthArray.add(example[1]);

                                Log.e(MyTag, "this is what the depthArray holds " + depthArray);

                                String magnitudeContainer = descriptionSeperator[4];
                                String[] magnitudeSeperator = magnitudeContainer.split(":" );
                                String magnitude = magnitudeSeperator[1].trim();
                                currentRecord.setMagnitude(magnitudeContainer);

                                magnitudeArray.add(magnitude);



                                Log.e(MyTag, "This is whats magnitudeArray holds " + magnitudeArray);

                                finalMagnitude = parseDouble(magnitude.trim());
//                                Log.e(MyTag, "msg "+ finalMagnitude);





                                String earthquakeReport = "Location is: " + location + "\n" + "Date and Time occured: " + dateTime + "\n" + "Latitude: " + latitude  + " Longitude: " + longitude + "\n" + "Depth of earthquake: " + depth + "\n" + "Magnitude of earthquake: " + magnitude;



                                // extract the text between <link> and </link>
                                description.add(earthquakeReport);
//                                descView.add(quakeData);

                                Log.e(MyTag, "This is whats in the description arraylist " + description);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                links.add(parser.nextText());

                                Log.e(MyTag, "This is whats in the links arraylist " + links);


                            }
                        }
                        else if (parser.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                pubDate.add(parser.nextText());

                                Log.e(MyTag, "This is whats in the pubDate arraylist " + pubDate);
                            }
                        }
                        else if (parser.getName().equalsIgnoreCase("geo:lat")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                geoLat.add(parser.nextText());

                                Log.e(MyTag, "This is whats in the geoLat arraylist " + geoLat);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("geo:long")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                geoLong.add(parser.nextText());

                                Log.e(MyTag, "This is whats in the geoLong arraylist " + geoLong);
                            }
                        }
                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = parser.next(); //move to next element
                }

                for (custObj fitem: objectList){

                    String dat = fitem.getDateTime().toString();
                    String loc = fitem.getLocation().toString();
                    String lat = fitem.getLatitude().toString();
                    String lngt = fitem.getLongitude().toString();
                    String dpth = fitem.getDepth().toString();
                    String mgt = fitem.getMagnitude().toString();

                    parcelObject = new ParcelObject(dat,loc,lat,lngt,dpth,mgt);
                    parcelObjects.add(parcelObject);



//                    Log.d(MyTag, "this is a location inside my objList: "+a);
                    Log.e(MyTag, "This is whats in the objectList inside fitem " + fitem.toString());
                }


            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            catch (XmlPullParserException e)
            {
                Log.e("MyTag", "ioexception");
            }




            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);



//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, titles );
////                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, links );
//
//                    lvRss.setAdapter(adapter);
////                    lvRss.setAdapter(adapter2);

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, description );
//
//
//                    lvRss.setAdapter(adapter);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, description );


                    lvRss.setAdapter(adapter);
                    // Filter Search

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, description );





//                    Filtered search

                    adapter2 = adapter;
                    final String appone = String.valueOf(adapter);




//                    app = String.valueOf(adapter);
                    appone.trim().replaceAll("\\s+", " ");


//                    adapter2 = adapter2.replaceAll(" ", "").trim();
//                    adapter2.trim().replaceAll("\\s+", " ");

                    lvRss.setAdapter(adapter2);

                    EditText theFilter = (EditText) findViewById(R.id.searchFilter);

                    final ArrayAdapter<String> finalAdapter = adapter2;

//                    adapter2.trim().replaceAll(" ", " ");

                    theFilter.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                (MainActivity.this).adapter.getFilter().filter(s);
                            lvRss.setTextFilterEnabled(true);
//                            lvRss.setFilterText(s.toString().trim());
//                            adapter2.trim().replaceAll(" ", "");
                            finalAdapter.getFilter().filter(s.toString());

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            finalAdapter.getFilter().filter(s.toString().trim());

                        }
                    });

                    Log.e(MyTag, "This is whats in the lisview lvRSS which is everything taken that is needed from the link  " + description);


                }
            });
        }

    }

}
