package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 12/17/2016.
 */

public class UserStore implements Serializable {

    private Store store;
    private double distance;


    public UserStore() {
        super();
    }

    public UserStore(Store store, double distance) {
        this.store = store;
        this.distance = distance;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }



}
