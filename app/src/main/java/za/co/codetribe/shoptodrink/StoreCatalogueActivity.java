package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.Manifest;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Distance;
import za.co.codetribe.shoptodrink.classes.Store;
import za.co.codetribe.shoptodrink.classes.Transmission;
import za.co.codetribe.shoptodrink.classes.UserStore;
import za.co.codetribe.shoptodrink.ui.GPSTracker;

public class StoreCatalogueActivity extends AppCompatActivity {


    private List<Store> storeList;
    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private ListView lvDistance;
    private String[] distStore;
    private Distance distance;
    double userLongitude;
    double userLatitude;
    private List<Store> purchStore;
    private double stretch;
    private int position;
    private Transmission transmission;
    private Intent intent;
    // GPSTracker class
    GPSTracker gps;

    private List<UserStore> store3;
    private List<UserStore> store5;
    private List<UserStore> store7;
    private  List<UserStore> store10;
    private List<UserStore> store15;
    private List<UserStore> store20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_catalogue);


        distStore = getResources().getStringArray(R.array.store_distance_array);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create class object
        gps = new GPSTracker(StoreCatalogueActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()) {

            userLatitude = gps.getLatitude();
            userLongitude = gps.getLongitude();
        }



       /* btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(StoreCatalogueActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();




                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();




                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });*/
        lvDistance = (ListView) findViewById(R.id.lvDistance);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,distStore);
        lvDistance.setAdapter(adapter);

        lvDistance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                position = i;

                storeDistance(storeList,position);
            }
        });
    }


    public void storeDistance(List<Store> storeList,int position)
    {
        transmission = new Transmission();
        store3 = new ArrayList<>();
        store5 = new ArrayList<>();
        store7 = new ArrayList<>();
        store10 = new ArrayList<>();
        store15 = new ArrayList<>();
        store20 = new ArrayList<>();


        distance = new Distance();
        distance.setStartP(new LatLng(userLatitude,userLongitude));


        for(Store store: storeList)
        {

            distance.setEndP(new LatLng(store.getArea().getLatitude(),store.getArea().getLongitude()));

           stretch = distance.CalculationByDistance();

            if(stretch <= 3)
            {


                UserStore userStore = new UserStore();

                userStore.setStore(store);
                userStore.setDistance(stretch);
                store3.add(userStore);

            }else if(stretch <= 5)
            {

                UserStore userStore = new UserStore();
                userStore.setStore(store);
                userStore.setDistance(stretch);
                store5.add(userStore);

            }else if(stretch <= 7)
            {

                UserStore userStore = new UserStore();
                userStore.setStore(store);
                userStore.setDistance(stretch);
                store7.add(userStore);
            }else if(stretch <=10)
            {

                UserStore userStore = new UserStore();
                userStore.setStore(store);
                userStore.setDistance(stretch);
                store10.add(userStore);
            }else if(stretch <=15)
            {

                UserStore userStore = new UserStore();
                userStore.setStore(store);
                userStore.setDistance(stretch);
                store15.add(userStore);
            }else if(stretch <= 20)
            {

                UserStore userStore = new UserStore();
                userStore.setStore(store);
                userStore.setDistance(stretch);
                store20.add(userStore);
            }
        }


        if(position ==3)
        {

            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store3);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);
        }else if(position == 5)
        {
            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store5);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);

        }else if(position == 7)
        {

            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store7);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);
        }else if(position == 10)
        {

            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store10);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);

        }else if(position == 15)
        {

            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store15);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);

        }else if(position == 20)
        {

            intent = new Intent(StoreCatalogueActivity.this,StoreActivity.class);
            transmission.setObject(store20);
            intent.putExtra(Constants.TRANSMISSION,transmission);
            startActivity(intent);

        }



    }





}
