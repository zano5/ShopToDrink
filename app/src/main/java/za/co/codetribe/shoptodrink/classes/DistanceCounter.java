package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 2/6/2017.
 */

public class DistanceCounter implements Serializable {

    private String distanceInMiles;
    private String durationInMinutes;


    public DistanceCounter()
    {
        super();
    }
    public DistanceCounter(String distanceInMiles, String durationInMinutes) {
        this.distanceInMiles = distanceInMiles;
        this.durationInMinutes = durationInMinutes;
    }


    public String getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(String distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public String getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(String durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
