package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import za.co.codetribe.shoptodrink.Adapters.CatalogueAdapter;
import za.co.codetribe.shoptodrink.Adapters.SpacesItemDecoration;
import za.co.codetribe.shoptodrink.classes.Buyer;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.classes.UserStore;

public class UserStoreCatalogueActivity extends AppCompatActivity {

    private UserStore userStore;
    private RecyclerView rvCatalogue;
    private CatalogueAdapter adapter;
    private DatabaseReference db;
    private String key;
    private List<Item> itemList = new ArrayList<Item>();
    private Buyer buyer;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private String mEmail;
    private Order order;
    public static List<Item> cart = new ArrayList<Item>();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_catalogue);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Intent intent = getIntent();



       order = (Order)  intent.getSerializableExtra(Constants.ORDER);
        key = intent.getStringExtra(Constants.KEY);
        getSupportActionBar().setTitle(order.getUserStore().getStore().getName());
        rvCatalogue = (RecyclerView) findViewById(R.id.rvCatalogue);
        userStore = (UserStore) intent.getSerializableExtra(Constants.CATALOGUE);
        ///dapter = new CatalogueAdapter(this,userStore.getStore().getItems());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvCatalogue.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvCatalogue.setLayoutManager(new GridLayoutManager(this,2));


        db = FirebaseDatabase.getInstance().getReference().child("Store").child("StoreList").child(key).child("items");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot data: dataSnapshot.getChildren())
                {


                    Item item = data.getValue(Item.class);
                    Log.v("Items",data.toString());
                    itemList.add(item);


                }

                adapter = new CatalogueAdapter(UserStoreCatalogueActivity.this,itemList,true);
                Toast.makeText(UserStoreCatalogueActivity.this,String.valueOf(itemList.size()),Toast.LENGTH_SHORT).show();
                rvCatalogue.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       // rvCatalogue.setAdapter(adapter);








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_store_catalogue,menu);

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



             if(i == 2)
             {


                 Intent intent = new Intent(UserStoreCatalogueActivity.this,OrderActivity.class);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {


            order.setRequest(true);
            order.setItems(OrderActivity.items);

            Toast.makeText(UserStoreCatalogueActivity.this,String.valueOf(adapter.getCatalogue().size()),Toast.LENGTH_SHORT).show();
           // Transmission transmission = new Transmission();

           // transmission.setObject(adapter.getCatalogue());
            //transmission.setDecision("Cart");


           // order.setItems();
            Intent intent = new Intent(UserStoreCatalogueActivity.this,UserStoreCartActivity.class);
            intent.putExtra(Constants.ORDER,order);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
