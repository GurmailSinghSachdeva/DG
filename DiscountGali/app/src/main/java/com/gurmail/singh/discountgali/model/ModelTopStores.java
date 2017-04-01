package com.gurmail.singh.discountgali.model;

import java.io.Serializable;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class ModelTopStores implements Serializable {

    private int id;
    private String icon = "";

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
