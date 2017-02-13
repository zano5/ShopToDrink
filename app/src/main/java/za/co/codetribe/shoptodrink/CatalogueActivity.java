package za.co.codetribe.shoptodrink;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.classes.Constants;
import za.co.codetribe.shoptodrink.classes.Item;
import za.co.codetribe.shoptodrink.classes.Order;
import za.co.codetribe.shoptodrink.classes.User;
import za.co.codetribe.shoptodrink.ui.RuntimePermissionsActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CatalogueActivity extends RuntimePermissionsActivity {

    private DatabaseReference db;
    private FirebaseRecyclerAdapter<Item,ItemViewHolder> adapter;
    private List<Item> catalogue;
   // @BindView(R.id.progress) ProgressBar progressBar;
    private ProgressBar progressBar;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private static final int REQUEST_PERMISSIONS = 20;

   // @BindView(R.id.rvCatalogue) RecyclerView rvCatalogue;
    RecyclerView rvCatalogue;
    private  List<Item> cart = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);



        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        progressBar = (ProgressBar) findViewById(R.id.progress);

        db = FirebaseDatabase.getInstance().getReference().child("Catalogue").child("CatalogueList");
       ButterKnife.bind(this);


       /* catalogue = new ArrayList<Item>();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    Item item = dataSnapshot1.getValue(Item.class);

                    catalogue.add(item);

                    Log.v("zano",dataSnapshot1.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if(catalogue.size() != 0)
        {

            progressBar.setVisibility(View.GONE);

        }*/

        rvCatalogue = (RecyclerView) findViewById(R.id.rvCatalogue);

        rvCatalogue.setLayoutManager(new GridLayoutManager(CatalogueActivity.this,2));


        //rvCatalogue.setAdapter(catalogueAdapter);



       adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(Item.class,R.layout.catalogue_view,ItemViewHolder.class,db) {
            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, final Item model, int position) {


                progressBar.setVisibility(View.GONE);
                final CardView cardView = viewHolder.getCardView();

              viewHolder.setImage(model.getImageUrl());


                viewHolder.setPrice(model.getPrice());
                viewHolder.tvItemName(model.getName());

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(CatalogueActivity.this);
                        View layout = LayoutInflater.from(CatalogueActivity.this).inflate(R.layout.order_view,null);

                        TextView tvDescription = ButterKnife.findById(layout,R.id.tvDescription);


                        Uri uri = Uri.parse(model.getImageUrl());
                        SimpleDraweeView draweeView = ButterKnife.findById(layout,R.id.ivCatalogue);
                        draweeView.setImageURI(uri);
                        tvDescription.setText(model.getDescription());

                        builder.setView(layout);

                        builder.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                              cart.add(model);

                            }
                        })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });

                        builder.setView(layout);

                        Dialog dialog = builder.create();
                        dialog.show();



                    }
                });




            }
        };

        rvCatalogue.setAdapter(adapter);




    }


    public void setPermissions()
    {


        CatalogueActivity.super.requestAppPermissions(new
                String[]{ACCESS_FINE_LOCATION,
        }, R.string.runtime_permissions_txt, REQUEST_PERMISSIONS);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    private static  class ItemViewHolder extends  RecyclerView.ViewHolder{

        private View mView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setImage(String url)
        {

            Uri uri = Uri.parse(url);
            SimpleDraweeView draweeView= (SimpleDraweeView) mView.findViewById(R.id.ivCatalogue);
            draweeView.setImageURI(uri);

        }


        public void tvItemName(String name)
        {
            TextView tvItemName  = ButterKnife.findById(mView,R.id.tvItemName);
            tvItemName.setText(name);

        }


        public void setPrice(Double price)
        {


            TextView tvPrice = ButterKnife.findById(mView,R.id.tvItemPrice);
            tvPrice.setVisibility(View.GONE);
        }


        public CardView getCardView()
        {

            CardView card =  ButterKnife.findById(mView,R.id.cdCatalogue);
            setFadeAnimation(card);
            return  card;

        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500);
            view.startAnimation(anim);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalogue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        User user = new User();
        Order order = new Order();
        user.setUserID(mFirebaseUser.getUid());
        user.setEmail(mFirebaseUser.getEmail());
        order.setItems(cart);
        order.setUser(user);


        int id = item.getItemId();


        if (id == R.id.action_cart) {


            Intent intent = new Intent(CatalogueActivity.this, ShoppingCartActivity.class);
            intent.putExtra(Constants.ORDER, order);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);


    }





}
