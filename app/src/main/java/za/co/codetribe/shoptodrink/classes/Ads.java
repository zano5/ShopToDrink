package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 12/5/2016.
 */

public class Ads implements Serializable {


    private String url;


    public  Ads()
    {
        super();
    }


    public Ads(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "url='" + url + '\'' +
                '}';
    }
}
