package za.co.codetribe.shoptodrink.classes;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by ProJava on 12/11/2016.
 */

public class Distance implements Serializable {

    private LatLng StartP;
    private LatLng EndP;


    public Distance()
    {
        super();
    }


    public Distance(LatLng startP, LatLng endP) {
        StartP = startP;
        EndP = endP;
    }

    public double CalculationByDistance() {
        int Radius=6371;//radius of earth in Km
        double lat1 = getStartP().latitude;
        double lat2 = getEndP().latitude;
        double lon1 = getStartP().longitude;
        double lon2 = getEndP().longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }


    public LatLng getEndP() {
        return EndP;
    }

    public void setEndP(LatLng endP) {
        EndP = endP;
    }


    public void setStartP(LatLng startP) {
        StartP = startP;
    }

    public LatLng getStartP() {
        return StartP;
    }



}
