package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ProJava on 11/17/2016.
 */

public class Order implements Serializable {



    private List<Item> items;
    private Buyer buyer;
    private boolean request;
    private Driver driver;
    private User user;
    private double deliveryFee;
    private UserStore userStore;
    private DistanceCounter counter;
    private double totItemPrice;
    private Payment payment;



    public Order() {
        super();
    }


    public Order(List<Item> items, Buyer buyer,boolean request, Driver driver) {

        this.items = items;
        this.buyer = buyer;
        this.request = request;
        this.driver = driver;
    }



    public Order( List<Item> items, Buyer buyer,boolean request, Driver driver,User user, double deliveryFee,UserStore userStore) {

        this.items = items;
        this.buyer = buyer;
        this.request = request;
        this.driver = driver;
        this.user = user;
        this.deliveryFee = deliveryFee;
        this.userStore = userStore;
    }


    public Order(List<Item> items, Buyer  buyer) {

        this.items = items;
        this.buyer= buyer;
    }





    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }



    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }


    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public DistanceCounter getCounter() {
        return counter;
    }

    public void setCounter(DistanceCounter counter) {
        this.counter = counter;
    }


    public double getTotItemPrice() {
        return totItemPrice;
    }

    public void setTotItemPrice(double totItemPrice) {
        this.totItemPrice = totItemPrice;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
