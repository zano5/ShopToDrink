package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.Adapters.CatalogueAdapter;
import za.co.codetribe.shoptodrink.Adapters.SpacesItemDecoration;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.DistanceCounter;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.ui.GeoTask;

public class UserStoreCartActivity extends AppCompatActivity {

    private CatalogueAdapter adapter;
    private RecyclerView rvCart;
    private Order order;
    private Button btnNext;
    String str_from,str_to;
    private String json_response="";
    private DistanceCounter distanceCounter;
    private double totAmnt;
    private double deliveryFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_cart);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);



       getSupportActionBar().setTitle("Cart");
        adapter = new CatalogueAdapter(this,order.getItems(),false);
        rvCart = ButterKnife.findById(this,R.id.rvCart);
        rvCart.setLayoutManager(new GridLayoutManager(this,2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvCart.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvCart.setAdapter(adapter);


        try {
            json_response =generateMatrix();
            distanceCounter = jsonConvert(json_response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public String generateMatrix() throws ExecutionException, InterruptedException {
        str_from = order.getUserStore().getStore().getArea().getArea().replace(" ","+");
        str_to = order.getBuyer().getArea().getArea().replace(" ","+");
        String url ="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+str_from.trim()+ "&destinations=" +str_to.trim()+"&mode=driving&key=AIzaSyDu6tTCPeF9BWxPGLcyZIXWrW_DnHpIqyk";

       return new GeoTask(UserStoreCartActivity.this).execute(url).get();
    }


    public void onNext(View view)
    {


        order.setCounter(distanceCounter);
        order.setTotItemPrice(totAmnt);


        Intent intent = new Intent(UserStoreCartActivity.this,OrderReviewActivity.class);
        intent.putExtra(Constants.ORDER,order);
        startActivity(intent);
    }




    public DistanceCounter jsonConvert(String respose)
    {

        String distanceText="";
        String durationText="";


        if (respose != null) {
            try {
                JSONObject json_response = new JSONObject(respose);

                Log.v("JSON", json_response.toString());

                if ("OK".equals(json_response.getString("status"))) {

                    JSONArray rows = json_response.getJSONArray("rows");

                    for (int i = 0; i < rows.length(); i++) {

                        JSONObject row = rows.getJSONObject(i);

                        JSONArray elements = row.getJSONArray("elements");

                        JSONObject element = elements.getJSONObject(i);

                        JSONObject distance = element.getJSONObject("distance");

                         distanceText = distance.getString("text");

                        JSONObject duration = element.getJSONObject("duration");

                        durationText = duration.getString("text");

                        Log.v("JSON", durationText.toString());

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(UserStoreCartActivity.this, "Error4!Please Try Again wiht proper values", Toast.LENGTH_SHORT).show();


        return  new DistanceCounter(distanceText,durationText);
    }


}
