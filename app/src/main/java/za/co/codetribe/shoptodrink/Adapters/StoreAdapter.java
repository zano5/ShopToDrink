package za.co.codetribe.shoptodrink.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.codetribe.shoptodrink.R;
import za.co.codetribe.shoptodrink.classes.UserStore;

/**
 * Created by ProJava on 12/17/2016.
 */

public class  StoreAdapter extends ArrayAdapter<UserStore> {


    private int resource;
    private LayoutInflater inflater;
    private ArrayList<UserStore> userStores;
    public StoreAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserStore> objects) {
        super(context, resource, objects);


        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.userStores = (ArrayList<UserStore>) objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        PlaceHolder holder;


        if(convertView == null)
        {

            holder = new PlaceHolder();
            view = inflater.inflate(this.resource,parent,false);


            holder.tvStoreDistance = ButterKnife.findById(view,R.id.tvStoreDistance);
            holder.tvStoreName = ButterKnife.findById(view,R.id.tvStoreName);

            view.setTag(holder);

        }else
        {

            view = convertView;
            holder =(PlaceHolder) view.getTag();
        }


        UserStore userStore = userStores.get(position);

        holder.tvStoreDistance.setText(String.valueOf(Math.round(userStore.getDistance())) + "KM");
        holder.tvStoreName.setText(userStore.getStore().getName());
        return view;
    }


    private class PlaceHolder
    {


        TextView tvStoreName, tvStoreDistance;
    }
}
