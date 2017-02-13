package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 12/1/2016.
 */

public class Area implements Serializable {

    private double latitude;
    private double longitude;
    private String area;
    private String city;
    private String country;


    public Area() {
        super();
    }

    public Area(double latitude, double longitude, String area) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = area;
    }


    public Area(String area, String city, String country, double latitude, double longitude) {
        this.area = area;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
