package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import za.co.codetribe.shoptodrink.Adapters.StoreAdapter;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Transmission;
import za.co.codetribe.shoptodrink.classes.UserStore;

public class StoreActivity extends AppCompatActivity {

    private Transmission transmission;
    private StoreAdapter adapter;
    private List<UserStore> userStoreList;
    private ListView lvStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        Intent intent = getIntent();

        lvStores = (ListView) findViewById(R.id.lvStores);
        transmission = (Transmission) intent.getSerializableExtra(Constants.TRANSMISSION);

        userStoreList = (List<UserStore>) transmission.getObject();

        adapter = new StoreAdapter(this,R.layout.store_view,userStoreList);

        lvStores.setAdapter(adapter);

        lvStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserStore userStore = userStoreList.get(i);

                Intent intent = new Intent(StoreActivity.this,UserStoreCatalogueActivity.class);
                intent.putExtra(Constants.CATALOGUE,userStore);
            }
        });
    }
}
