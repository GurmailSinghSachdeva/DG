package com.example.lenovo.discountgali.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lenovo on 10-01-2017.
 */

public class ServerResponse<T> implements Serializable {

    public BaseModel baseModel = new BaseModel();
    public ArrayList<T>  data = new ArrayList<>();
    public int totalCount = 0;
}
