package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.classes.Ads;
import za.co.codetribe.shoptodrink.ui.RuntimePermissionsActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends RuntimePermissionsActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private String mEmail;
    private GoogleApiClient mGoogleApiClient;
     List<Ads> list = new ArrayList<>();;

    private ViewFlipper viewFlipper;
    private List<Ads> ads = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 20;
    private TextView tvName;
    private TextView tvEmail;
    private ImageView ivImage;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvName = ButterKnife.findById(header,R.id.tvName);
        tvEmail = ButterKnife.findById(header,R.id.tvEmail);
       ivImage = ButterKnife.findById(header,R.id.ivImage);


        ads = new ArrayList<Ads>();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();







        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            mEmail = mFirebaseUser.getEmail();
            tvEmail.setText(mEmail);
            tvName.setText(mUsername);
            Glide.with(MainActivity.this).load(mPhotoUrl).fitCenter().into(ivImage);
        }




        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();











      Query dq =  FirebaseDatabase.getInstance().getReference().child("Ads").child("AdsList");;
        dq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {

                    Ads ad = (Ads) data.getValue(Ads.class);


                    //Toast.makeText(MainActivity.this,ad.getUrl(),Toast.LENGTH_SHORT).show();


                    list.add(ad);
                    //ads.add(ad);





                    Log.v("Zano",ad.getUrl());

                }

                adImages(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        //adImages(ads);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }


    public void adImages(List<Ads> list)
    {

        if(list.size() == 5) {

            ImageView ivAdvert1 = ButterKnife.findById(MainActivity.this, R.id.ivAdvert1);
            ImageView ivAdvert2 = ButterKnife.findById(MainActivity.this, R.id.ivAdvert2);
            ImageView ivAdvert3 = ButterKnife.findById(MainActivity.this, R.id.ivAdvert3);
            ImageView ivAdvert4 = ButterKnife.findById(MainActivity.this, R.id.ivAdvert4);
            ImageView ivAdvert5 = ButterKnife.findById(MainActivity.this, R.id.ivAdvert5);


            Glide.with(MainActivity.this).load(list.get(0).getUrl()).fitCenter().into(new GlideDrawableViewBackgroundTarget(ivAdvert1));
            Glide.with(MainActivity.this).load(list.get(1).getUrl()).fitCenter().into(new GlideDrawableViewBackgroundTarget(ivAdvert2));
            Glide.with(MainActivity.this).load(list.get(2).getUrl()).fitCenter().into(new GlideDrawableViewBackgroundTarget(ivAdvert3));
            Glide.with(MainActivity.this).load(list.get(3).getUrl()).fitCenter().into(new GlideDrawableViewBackgroundTarget(ivAdvert4));
            Glide.with(MainActivity.this).load(list.get(4).getUrl()).fitCenter().into(new GlideDrawableViewBackgroundTarget(ivAdvert5));

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_catalogue) {
            // Handle the camera action


            Intent intent = new Intent(MainActivity.this, CatalogueActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_order) {




                           // Intent intent1 = new Intent(MainActivity.this,OrderActivity.class);
                           // startActivity(intent1);












        } else if (id == R.id.nav_feedback) {

        }else if(id == R.id.nav_store) {

            MainActivity.super.requestAppPermissions(new
                            String[]{ACCESS_FINE_LOCATION,
                    }, R.string.runtime_permissions_txt, REQUEST_PERMISSIONS);


                Intent intent = new Intent(MainActivity.this,NearMapStoreActivity.class);
                startActivity(intent);

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public abstract class ViewBackgroundTarget<Z> extends ViewTarget<View, Z> implements GlideAnimation.ViewAdapter {
        public ViewBackgroundTarget(View view) {
            super(view);
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {
            setBackground(placeholder);
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            setBackground(placeholder);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            setBackground(errorDrawable);
        }

        @Override
        public void onResourceReady(Z resource, GlideAnimation<? super Z> glideAnimation) {
            if (glideAnimation == null || !glideAnimation.animate(resource, this)) {
                setResource(resource);
            }
        }

        @Override
        public void setDrawable(Drawable drawable) {
            setBackground(drawable);
        }

        @Override
        public Drawable getCurrentDrawable() {
            return view.getBackground();
        }

        @SuppressWarnings("deprecation")
        protected void setBackground(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }

        protected abstract void setResource(Z resource);
    }


    public class BitmapViewBackgroundTarget extends ViewBackgroundTarget<Bitmap> {
        public BitmapViewBackgroundTarget(View view) {
            super(view);
        }

        @Override
        protected void setResource(Bitmap resource) {
            setBackground(new BitmapDrawable(view.getResources(), resource));
        }
    }


    public class DrawableViewBackgroundTarget extends ViewBackgroundTarget<Drawable> {
        public DrawableViewBackgroundTarget(View view) {
            super(view);
        }

        @Override
        protected void setResource(Drawable resource) {
            setBackground(resource);
        }
    }


    public class GlideDrawableViewBackgroundTarget extends ViewBackgroundTarget<GlideDrawable> {
        private int maxLoopCount;
        private GlideDrawable resource;

        public GlideDrawableViewBackgroundTarget(ImageView view) {
            this(view, GlideDrawable.LOOP_FOREVER);
        }

        public GlideDrawableViewBackgroundTarget(ImageView view, int maxLoopCount) {
            super(view);
            this.maxLoopCount = maxLoopCount;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
            super.onResourceReady(resource, animation);
            this.resource = resource;
            resource.setLoopCount(maxLoopCount);
            resource.start();
        }

        @Override
        protected void setResource(GlideDrawable resource) {
            setBackground(resource);
        }

        @Override
        public void onStart() {
            if (resource != null) {
                resource.start();
            }
        }

        @Override
        public void onStop() {
            if (resource != null) {
                resource.stop();
            }
        }
    }











}
