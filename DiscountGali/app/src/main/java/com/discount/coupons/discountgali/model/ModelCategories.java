package com.discount.coupons.discountgali.model;

import java.io.Serializable;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class ModelCategories implements Serializable {
    private String CategoryLogo = "";
    private String CategoryName = "";
    private int CategoryId = 0;
    private boolean check = false;
    private boolean CategoryStatus = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCategoryLogo() {
        return CategoryLogo;
    }

    public void setCategoryLogo(String categoryLogo) {
        CategoryLogo = categoryLogo;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public boolean isCategoryStatus() {
        return CategoryStatus;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        CategoryStatus = categoryStatus;
    }
}
