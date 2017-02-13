package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ProJava on 12/11/2016.
 */

public class Store implements Serializable {


    private String email;
    private String name;
    private Area area;
    private String cell;
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }


    @Override
    public String toString() {
        return getName();

    }
}
