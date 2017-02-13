package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/16/2017.
 */

public class Buyer implements Serializable {


    private Area area;

    public Buyer() {
        super();
    }

    public Buyer(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}



