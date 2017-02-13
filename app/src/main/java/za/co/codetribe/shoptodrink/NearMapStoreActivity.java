package za.co.codetribe.shoptodrink;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import za.co.codetribe.shoptodrink.classes.Area;
import za.co.codetribe.shoptodrink.classes.Buyer;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Distance;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.classes.Store;
import za.co.codetribe.shoptodrink.classes.User;
import za.co.codetribe.shoptodrink.classes.UserStore;

public class NearMapStoreActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    List<Address> addresses;
    private boolean valid;
    private Area area;
    private static Buyer buyer;
    private List<Store> storeList;
    private DatabaseReference db;
    private List<UserStore> userStoreList;
    private String key;
    private Order order;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mUserID;
    private String mPhotoUrl;
    private String mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_map_store);
        buildGoogleApiClient();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mEmail = mFirebaseUser.getEmail();
        mUserID = mFirebaseUser.getUid();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        storeList = new ArrayList<Store>();
        db = FirebaseDatabase.getInstance().getReference().child("Store").child("StoreList");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot data: dataSnapshot.getChildren())
                {

                    key = data.getKey();
                    Store store = data.getValue(Store.class);

                    Log.v("Check",store.getArea().getArea());

                    storeList.add(store);



                }





                if(buyer !=null) {

                    order = new Order();
                    userStoreList = distanceListOfStores(buyer, storeList);

                    //adapter = new StoreAdapter(StoreListActivity.this,R.layout.store_view,userStoreList);

                    for(final UserStore userStore: userStoreList)
                    {

                        LatLng marker = new LatLng(userStore.getStore().getArea().getLatitude(), userStore.getStore().getArea().getLongitude());
                        mMap.addMarker(new MarkerOptions().position(marker).title(userStore.getStore() + "("+Math.round(userStore.getDistance())+"KM)"));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(NearMapStoreActivity.this);

                                builder.setTitle(userStore.getStore().getName()+ "("+Math.round(userStore.getDistance())+"KM)")
                                        .setMessage(getResources().getString(R.string.shop_dialog_message));

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        User user = new User();
                                        user.setEmail(mEmail);
                                        user.setUserID(mUserID);
                                        order.setUserStore(userStore);
                                        order.setBuyer(buyer);

                                        Intent intent = new Intent(NearMapStoreActivity.this,UserStoreCatalogueActivity.class);
                                        intent.putExtra(Constants.ORDER,order);
                                        intent.putExtra(Constants.KEY,key);
                                        startActivity(intent);
                                    }
                                })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                dialog.dismiss();

                                            }
                                        });

                                Dialog dialog = builder.create();
                                dialog.show();

                                return false;
                            }
                        });


                    }






                    //lvStore.setAdapter(adapter);

                }else {
                    Toast.makeText(NearMapStoreActivity.this, "Error", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // mMap = googleMap;

        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            Toast.makeText(NearMapStoreActivity.this,String.valueOf(latitude),Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {


                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                String city = addresses.get(0).getLocality();
                String locat = addresses.get(0).getAddressLine(0)+","+city;


                area = new Area(latitude, longitude, locat);
                buyer = new Buyer(area);

                Toast.makeText(NearMapStoreActivity.this, locat, Toast.LENGTH_SHORT).show();

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
        mGoogleApiClient.disconnect();
        super.onPause();

    }




    public List<UserStore> distanceListOfStores(Buyer buyer, List<Store> storeList)
    {


        List<UserStore> userStoreList = new ArrayList<UserStore>();

        Distance distance = new Distance();


        distance.setStartP( new LatLng(buyer.getArea().getLatitude(),buyer.getArea().getLongitude()));

        for(Store store: storeList)
        {

            distance.setEndP(new LatLng(store.getArea().getLatitude(),store.getArea().getLongitude()));
            userStoreList.add(new UserStore(store,distance.CalculationByDistance()));



        }



        return  userStoreList;

    }

}
