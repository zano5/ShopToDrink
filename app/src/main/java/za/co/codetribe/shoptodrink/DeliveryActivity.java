package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Order;

public class DeliveryActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Order order;
    private List<Address> addressList;
    private Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);
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

        // Add a marker in Sydney and move the camera


        LatLng myLocation = null;
        try {
            myLocation = getLatLng(order);
            mMap.addMarker(new MarkerOptions().position(myLocation).title(order.getBuyer().getArea().getArea()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }

       // mMap.setMyLocationEnabled(true);
    }

    private LatLng getLatLng(Order order) throws IOException {
    addressList= mGeocoder.getFromLocationName(order.getBuyer().getArea().getArea(),1);

        double latitude = addressList.get(0).getLatitude();
        double longitude = addressList.get(0).getLongitude();

        return  new LatLng(latitude,longitude);

    }



}
