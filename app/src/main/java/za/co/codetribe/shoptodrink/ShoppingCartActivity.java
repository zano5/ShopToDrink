package za.co.codetribe.shoptodrink;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.Adapters.CatalogueAdapter;
import za.co.codetribe.shoptodrink.Adapters.SpacesItemDecoration;
import za.co.codetribe.shoptodrink.classes.Area;
import za.co.codetribe.shoptodrink.classes.Buyer;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Order;


public class ShoppingCartActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private Order order;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private CatalogueAdapter adapter;
    private RecyclerView rvShoppingCart;
    private Area area;
    private boolean valid;
    private Activity activity;
    private Context context;
    private Buyer buyer;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    List<Address> addresses;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        rvShoppingCart = (RecyclerView) findViewById(R.id.rvShoppingCart);
        rvShoppingCart.setLayoutManager(new GridLayoutManager(ShoppingCartActivity.this, 2));
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);
        ButterKnife.bind(this);


        adapter = new CatalogueAdapter(this, order.getItems(),false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvShoppingCart.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvShoppingCart.setAdapter(adapter);
        buildGoogleApiClient();
        adapter.notifyDataSetChanged();


    }




    public void onNext(View view)
    {



        if (order.getItems() != null) {


            if (valid == true) {
                Buyer buyer = new Buyer(area);
               order.setBuyer(buyer);
                order.setRequest(false);

                Toast.makeText(ShoppingCartActivity.this,"Zanoh is a boss",Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(ShoppingCartActivity.this, StoreListActivity.class);
               //intent.putExtra(Constants.BUYER,getBuyer());
                //startActivity(intent);
            } else {

                Toast.makeText(ShoppingCartActivity.this, "Can not get your location", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(ShoppingCartActivity.this, getResources().getString(R.string.empty_cart_text), Toast.LENGTH_SHORT).show();

        }


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

                Toast.makeText(ShoppingCartActivity.this, locat, Toast.LENGTH_SHORT).show();

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


   /* public void onSubmit(View view) {







            if (order.getItems() != null) {


                if (valid == true) {
                    order.setArea(area);
                    order.setRequest(false);
                    db.setOrderList(order);

                    Toast.makeText(ShoppingCartActivity.this, getResources().getString(R.string.order_success_text), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(ShoppingCartActivity.this, "Can not get your location", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(ShoppingCartActivity.this, getResources().getString(R.string.empty_cart_text), Toast.LENGTH_SHORT).show();

            }

    }*/






}
