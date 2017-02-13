package za.co.codetribe.shoptodrink.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.OrderActivity;
import za.co.codetribe.shoptodrink.R;
import za.co.codetribe.shoptodrink.classes.Item;

/**
 * Created by ProJava on 12/1/2016.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {


    private List<Item> catalogue;
    private Context context;
    private boolean valid;
    private List<Item> items;



    public CatalogueAdapter(Context context, List<Item> catalogue, boolean valid)
    {
        this.catalogue = catalogue;
        this.context = context;
        this.valid = valid;

    }

    public CatalogueAdapter(Context context, List<Item> catalogue)
    {
        this.catalogue = catalogue;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= null;

        if(valid == false) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, null);
        }else if(valid== true)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogue_view, null);
        }else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, null);
        }

        ViewHolder vh = new  ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Item item = catalogue.get(position);


        if(valid== false) {
            holder.tvItemPrice.setText(String.valueOf(item.getPrice()));
            Uri uri = Uri.parse(item.getImageUrl());
            holder.draweeView.setImageURI(uri);
            holder.tvItemName.setText(item.getName());
            holder.ivOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CharSequence[] options = {"Update","Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which)
                            {
                                case 0:
                                    break;
                                case 1:
                                    OrderActivity.items.remove(position);
                                    catalogue.remove(position);

                                    notifyDataSetChanged();
                                    break;
                            }

                        }
                    });

                    Dialog dialog = builder.create();
                    dialog.show();

                }
            });

        }else if(valid == true)
        {

            items = new ArrayList<Item>();
            final Item itemInfo = catalogue.get(position);
            Uri uri = Uri.parse(item.getImageUrl());
            holder.draweeView.setImageURI(uri);
            holder.tvItemPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));
            holder.tvItemName.setText(item.getName());
            holder.cdCatalogue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View layout = LayoutInflater.from(context).inflate(R.layout.order_view,null);

                    TextView tvDescription = ButterKnife.findById(layout,R.id.tvDescription);

                    Uri uri = Uri.parse(itemInfo.getImageUrl());
                    SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.ivCatalogue);
                    draweeView.setImageURI(uri);
                    tvDescription.setText(itemInfo.getDescription());




                    builder.setView(layout);

                    builder.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                         OrderActivity.items.add(itemInfo);

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
        }else
        {

            holder.tvItemPrice.setVisibility(View.VISIBLE);
            holder.tvItemPrice.setText(String.valueOf(item.getPrice()));
            Uri uri = Uri.parse(item.getImageUrl());
            holder.draweeView.setImageURI(uri);
            holder.tvItemName.setText(item.getName());
        }


    }

    @Override
    public int getItemCount() {
        return catalogue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cdCatalogue;
        private TextView tvItemName, tvItemPrice;
        private SimpleDraweeView draweeView;
        private ImageView ivOptions;


        public ViewHolder(View view) {
            super(view);

            cdCatalogue = ButterKnife.findById(view,R.id.cdCatalogue);
            tvItemName= ButterKnife.findById(view,R.id.tvItemName);
            tvItemPrice = ButterKnife.findById(view,R.id.tvItemPrice);
           draweeView=(SimpleDraweeView) view.findViewById(R.id.ivCatalogue);
            ivOptions = ButterKnife.findById(view,R.id.ivOptions);


        }
    }





    public List<Item> getCatalogue()
    {

        return this.items;
    }



}
