package za.co.codetribe.shoptodrink;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.classes.Payment;

public class OrderReviewActivity extends AppCompatActivity {

    private Order order;
    private Button btnCheckout;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    @BindView(R.id.tvDeliveryFee) TextView tvDeliveryFee;
    @BindView(R.id.tvDestinationLocation) TextView tvDestinationLocation;
    @BindView(R.id.tvPickupLocation) TextView tvPickUpLocation;
    @BindView(R.id.tvTotItemPrice) TextView tvTotItemPrice;
    @BindView(R.id.tvItemsNo) TextView tvItemsNo;
    @BindView(R.id.tvDistance) TextView tvDistance;
    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.tvTotPrice) TextView tvTotPrice;
    private double totAmnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();

        order = (Order) intent.getSerializableExtra(Constants.ORDER);
        totAmnt = totItemPrice(order.getItems());


      initialize();
    }

    private void initialize()
    {

        order.setTotItemPrice(totAmnt);
        double distanceKM = (Double.parseDouble(order.getCounter().getDistanceInMiles().trim().replace("mi","").trim()) *Constants.MILES_TO_KN);
        double deliveryFee = Double.parseDouble(String.format("%.2f",distanceKM *Constants.COST_PER_KILOMETER));
        tvDeliveryFee.setText("R"+ String.format("%.2f",distanceKM *Constants.COST_PER_KILOMETER));
       tvDestinationLocation.setText(order.getBuyer().getArea().getArea());
        tvPickUpLocation.setText(order.getUserStore().getStore().getArea().getArea());
        tvTotItemPrice.setText("R"+order.getTotItemPrice());
       tvItemsNo.setText(String.valueOf(order.getItems().size()));
        tvDistance.setText(String.format("%.2f",distanceKM) +" KM");
        tvTime.setText(order.getCounter().getDurationInMinutes());
        order.setDeliveryFee(Double.parseDouble(String.format("%.2f",distanceKM *Constants.COST_PER_KILOMETER)));
       tvTotPrice.setText("R"+ String.format("%.2f",(order.getDeliveryFee() + order.getTotItemPrice())));


    }

    private double totItemPrice(List<Item> items)
    {

        double amnt =0;


        for(Item item: items)
        {

            amnt+=item.getPrice();

        }

        return  amnt;
    }


    public void onCheckOut(View view)
    {


        final Payment payment = new Payment();
        payment.setAmountDue(totAmnt);
        payment.setAmountPaid(0);



        AlertDialog.Builder builder = new AlertDialog.Builder(OrderReviewActivity.this);
        builder.setTitle("Payment Options");
        builder.setMessage("Pick Payment Option");
        builder.setPositiveButton("Card", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                payment.setPaymentType("Card");
                order.setPayment(payment);
                Toast.makeText(OrderReviewActivity.this,"Order request has been made",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderReviewActivity.this,DeliveryActivity.class);
                startActivity(intent);

            }
        })
                .setNegativeButton("Cash", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        payment.setPaymentType("Cash");
                        order.setPayment(payment);
                        db.child("Order").child("OrderList").child(mAuth.getCurrentUser().getUid()).push().setValue(order);
                        Toast.makeText(OrderReviewActivity.this,"Order request has been made",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderReviewActivity.this,DeliveryActivity.class);
                        startActivity(intent);



                    }
                });

        Dialog dialog = builder.create();
        dialog.show();
    }
}
