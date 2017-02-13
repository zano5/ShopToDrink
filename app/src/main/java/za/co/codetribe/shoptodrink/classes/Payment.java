package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/23/2017.
 */

public class Payment implements Serializable {


    private String paymentType;
    private double amountDue;
    private double amountPaid;



    public Payment() {
        super();
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


}
