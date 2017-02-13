package za.co.codetribe.shoptodrink;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.ui.FragmentBeer;
import za.co.codetribe.shoptodrink.ui.FragmentVodka;
import za.co.codetribe.shoptodrink.ui.FragmentWine;

public class OrderActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<Item> items = new ArrayList<>();
    private String key;
    private Order order;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        statusCheck();
        ButterKnife.bind(this);

        Intent intent = getIntent();

        key = intent.getStringExtra(Constants.KEY);
        order =(Order) intent.getSerializableExtra(Constants.ORDER);




       viewPager = (ViewPager) findViewById(R.id.viewpager);
       setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());




            FragmentBeer beer = new FragmentBeer();
            beer.setKey(key);

            FragmentVodka vodka = new FragmentVodka();
            vodka.setKey(key);

            FragmentWine wine = new FragmentWine();
            wine.setKey(key);


            adapter.addFragment(beer, "Beer");
            adapter.addFragment(vodka, "Vodka");
            adapter.addFragment(wine, "Wine");


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_menu, menu);


        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserStoreCatalogueActivity.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.options_catalogue_array));
        // spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_catalogue_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



               if(i == 1)
                {


                    Intent intent = new Intent(OrderActivity.this,UserStoreCatalogueActivity.class);
                    intent.putExtra(Constants.ORDER,order);
                    intent.putExtra(Constants.KEY,key);
                    startActivity(intent);
                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

                return true;
            }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        order.setRequest(true);
        order.setItems(items);



        // User user  = fh.getUSer(mFirebaseUser.getUid());


        int id = item.getItemId();


        if (id == R.id.action_cart) {


            Intent intent = new Intent(OrderActivity.this,UserStoreCartActivity.class);
            intent.putExtra(Constants.ORDER,order);
            startActivity(intent);

        }







        return super.onOptionsItemSelected(item);
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }




}
