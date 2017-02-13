package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import  butterknife.BindView;

public class OrderRequestActivity extends AppCompatActivity {

    private Order order;
    @BindView(R.id.tvStoreDistance) TextView tvStoreDistance;
    @BindView(R.id.tvTotItemPrice) TextView tvTotItemPrice;
    @BindView(R.id.tvDelivery) TextView tvDelivery;
    @BindView(R.id.tvTotal) TextView tvTotal;
    @BindView(R.id.btnSubmit) Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        order= (Order) intent.getSerializableExtra(Constants.ORDER);




    }


    public void itemsPrice(List<Item> items)
    {


    }


    public void deliveryFee(double distance)
    {

    }





    public void onSubmit(View view)
    {


    }
}
