package com.gurmail.singh.discountgali.model;

import java.io.Serializable;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class FeaturedModel implements Serializable {
    public String title = "";
    public String description = "";
    public String icon = "";
    public String redirect_url = "";
    public int dealId = 0;
    public boolean isOnloine = false;

    public FeaturedModel(String title, String description, String icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public FeaturedModel() {
    }
}
