package com.example.re_2.functions.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class data_model {

    @SerializedName("bitmap")
    @Expose
    private String bitmap;


    public String getBitmap() {
        return bitmap;
    }
}