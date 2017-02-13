package za.co.codetribe.shoptodrink.ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.OrderActivity;
import za.co.codetribe.shoptodrink.R;
import za.co.codetribe.shoptodrink.classes.Item;

/**
 * Created by ProJava on 12/1/2016.
 */

public class FragmentWine extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView rvAlcohol;
    private DatabaseReference db;
    private FirebaseRecyclerAdapter<Item,WineViewHolder> adapter;
    private boolean valid = false;
    private String key;


    public FragmentWine() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_alcohol, container, false);

            db = FirebaseDatabase.getInstance().getReference().child("Store").child("StoreList").child(getKey()).child("items");



        Query query = db.orderByChild("type").equalTo("Wine");

        progressBar = ButterKnife.findById(view,R.id.progress);

        rvAlcohol = ButterKnife.findById(view,R.id.rvAlcohol);
        rvAlcohol.setLayoutManager(new GridLayoutManager(getActivity(),2));

        adapter = new FirebaseRecyclerAdapter<Item, WineViewHolder>(Item.class,R.layout.catalogue_view,WineViewHolder.class,query) {
            @Override
            protected void populateViewHolder(WineViewHolder viewHolder, final Item model, int position) {

                progressBar.setVisibility(View.GONE);
                final CardView cardView = viewHolder.getCardView();

                viewHolder.setImage(model.getImageUrl());
                viewHolder.setPrice(model.getPrice());
                viewHolder.tvItemName(model.getName());


                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.order_view,null);

                        Uri uri = Uri.parse(model.getImageUrl());
                        SimpleDraweeView draweeView = ButterKnife.findById(layout,R.id.ivCatalogue);
                        draweeView.setImageURI(uri);
                        TextView tvDescription = ButterKnife.findById(layout,R.id.tvDescription);



                        tvDescription.setText(model.getDescription());


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setView(layout);

                        builder.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                OrderActivity.items.add(model);

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

        rvAlcohol.setAdapter(adapter);






        // Inflate the layout for this fragment
        return view;
    }



    private static  class WineViewHolder extends  RecyclerView.ViewHolder {

        private View mView;

        public WineViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImage(String url)
        {

            SimpleDraweeView ivItem = ButterKnife.findById(mView,R.id.ivCatalogue);
            Uri uri = Uri.parse(url);
            ivItem.setImageURI(uri);

        }



        public void tvItemName(String name) {
            TextView tvItemName = ButterKnife.findById(mView, R.id.tvItemName);
            tvItemName.setText(name);

        }


        public void setPrice(Double price) {


            TextView tvPrice = ButterKnife.findById(mView, R.id.tvItemPrice);
            tvPrice.setText(NumberFormat.getCurrencyInstance().format(price));
        }


        public CardView getCardView() {

            CardView card = ButterKnife.findById(mView, R.id.cdCatalogue);
            setFadeAnimation(card);
            return card;

        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(2000);
            view.startAnimation(anim);
        }


    }

    public void setValid(boolean valid)
    {

        this.valid = valid;
    }

    public boolean isValid()
    {
        return  this.valid;
    }

    public void setKey(String key)
    {

        this.key = key;
    }


    public String getKey()
    {

        return  this.key;
    }
}
