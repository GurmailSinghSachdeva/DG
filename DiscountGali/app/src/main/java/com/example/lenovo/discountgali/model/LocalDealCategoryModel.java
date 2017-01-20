package com.example.lenovo.discountgali.model;

import java.io.Serializable;

/**
 * Created by lenovo on 19-01-2017.
 */

public class LocalDealCategoryModel implements Serializable {
    private String CategoryLogo = "";
    private String CategoryName = "";
    private int CategoryId = 0;

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
}
