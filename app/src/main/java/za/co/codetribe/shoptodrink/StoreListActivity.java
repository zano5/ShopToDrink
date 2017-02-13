package za.co.codetribe.shoptodrink;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import za.co.codetribe.shoptodrink.Adapters.StoreAdapter;
import za.co.codetribe.shoptodrink.classes.Area;
import za.co.codetribe.shoptodrink.classes.Buyer;
import za.co.codetribe.shoptodrink.classes.Distance;
import za.co.codetribe.shoptodrink.classes.Store;
import za.co.codetribe.shoptodrink.classes.UserStore;
import butterknife.ButterKnife;

public class StoreListActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private ListView lvStore;
    private List<Store> storeList;
    private StoreAdapter adapter;
    private List<UserStore> userStoreList;
    private static Buyer buyer;
    private DatabaseReference db;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    List<Address> addresses;
    private boolean valid;
    private Area area;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        lvStore = ButterKnife.findById(this,R.id.lvStores);
        buildGoogleApiClient();

       // helper = new FirebaseHelper();
        //storeList = helper.getStores();


        storeList = new ArrayList<Store>();
        db = FirebaseDatabase.getInstance().getReference().child("Store").child("StoreList");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot data: dataSnapshot.getChildren())
                {

                    Store store = data.getValue(Store.class);

                    Log.v("Check",store.getArea().getArea());

                    storeList.add(store);



                }



                Toast.makeText(StoreListActivity.this,String.valueOf(storeList.size()),Toast.LENGTH_LONG).show();

                if(buyer !=null) {


                    userStoreList = distanceListOfStores(buyer, storeList);

                    adapter = new StoreAdapter(StoreListActivity.this,R.layout.store_view,userStoreList);




                    lvStore.setAdapter(adapter);

                }else {
                    Toast.makeText(StoreListActivity.this, "Error", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }

    public List<UserStore> distanceListOfStores(Buyer buyer,List<Store> storeList)
    {

      List<UserStore> userStoreList = new ArrayList<UserStore>();

        Distance distance = new Distance();


        distance.setStartP( new LatLng(buyer.getArea().getLatitude(),buyer.getArea().getLongitude()));

        for(Store store: storeList)
        {

            distance.setEndP(new LatLng(store.getArea().getLatitude(),store.getArea().getLongitude()));
            userStoreList.add(new UserStore(store,distance.CalculationByDistance()));

            Toast.makeText(StoreListActivity.this,"Dick",Toast.LENGTH_LONG).show();
            Toast.makeText(StoreListActivity.this,"Dick",Toast.LENGTH_LONG).show();

        }



        return  userStoreList;

    }

    public  void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);





        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            // mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            valid = true;

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            // Toast.makeText(ShoppingCartActivity.this,String.valueOf(latitude),Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {


                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                String locat = addresses.get(0).getAddressLine(0);

                area = new Area(latitude, longitude, locat);
                buyer = new Buyer(area);

                Toast.makeText(StoreListActivity.this, locat, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();






                }
            }

    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    public void onDestroy() {
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }



    @Override
    protected void onPause() {
        mGoogleApiClient.connect();
        super.onPause();

    }

    @Override
    public void onResume()
    {
        mGoogleApiClient.connect();
        super.onResume();
    }






}
