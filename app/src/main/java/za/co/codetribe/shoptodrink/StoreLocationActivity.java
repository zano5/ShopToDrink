package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Distance;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.classes.Store;

public class StoreLocationActivity extends AppCompatActivity {

    private List<Store> storeList;
    private Order order;
    private Distance distance;
    private int marker;
    private double length;
    private boolean[] valids;
    private int count=0;
    private int storeNumber = -1;
    @BindView(R.id.tvDelivery) TextView tvDelivery;
    @BindView(R.id.tvItemPrice) TextView tvItemPrice;
    @BindView(R.id.tvStoreDistance) TextView tvStoreDistance;
    @BindView(R.id.tvTotal) TextView tvTotal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location);


        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);


        //fh = new FirebaseHelper();
        storeList = new ArrayList<Store>();





        new StoreTask().execute();





    }





    public boolean[] itemMatch(List<Item> orders,List<Item> stores)
    {



       boolean[] valid= new boolean[orders.size()];


        for(int x=0; x< orders.size(); x++)
        {
            Item order = orders.get(x);



            for(int z =0; z < stores.size(); z++)
            {
                Item store = stores.get(x);

                if(order.getName().equals(store.getName()))
                {
                    valid[x] = true;


                }else
                {

                    valid[x] = false;

                }

            }


        }

        return  valid;

    }


    public double shortestDistance(List<Store> storeList)
    {
        double length =0;


        double userLatitude = order.getBuyer().getArea().getLatitude();
        double userLongitude = order.getBuyer().getArea().getLongitude();
        LatLng userLat = new LatLng(userLatitude,userLongitude);


        LatLng storeLat = new LatLng(storeList.get(0).getArea().getLatitude(),storeList.get(0).getArea().getLongitude());

        distance = new Distance(userLat,storeLat);
        double shortestDistance = distance.CalculationByDistance();



        for(int x=0; x< storeList.size(); x++)
        {

            storeLat = new LatLng(storeList.get(x).getArea().getLatitude(),storeList.get(x).getArea().getLongitude());

            distance = new Distance(userLat,storeLat);

            if(shortestDistance > distance.CalculationByDistance())
            {

                shortestDistance = distance.CalculationByDistance();

                marker = x;


            }

        }



        return  length;
    }






    public void onSubmit(View view)
    {



        if(storeNumber  != -1)
        {



        }else
        {



        }


    }


    private class StoreTask extends AsyncTask<Void,Void,Integer>
    {


        @Override
        protected Integer doInBackground(Void... voids) {



            int store =-1;

            while(count != order.getItems().size()) {


                count = 0;

                length = shortestDistance(storeList);
                valids = itemMatch(order.getItems(), storeList.get(marker).getItems());


                for (int x = 0; x < valids.length; x++) {


                    if (valids[x] == true) {

                        count++;
                    } else {

                        break;
                    }

                    storeList.remove(marker);
                }






            }


            if(count == order.getItems().size())
            {


                store = marker;

            }

            return store;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            storeNumber = integer;



            if(storeNumber== -1)
            {

                    tvDelivery.setText("No avaliable store with items");
                    tvItemPrice.setText("No avaliable store with items");
                    tvStoreDistance.setText("No avaliable store with items");
                    tvTotal.setText("No avaliable store with items");


            }else
            {


            }
        }
    }


}
