package com.example.re_2.functions.retrofit;

import com.google.gson.annotations.SerializedName;

public class Post {

    /** example)
     * {
     *     "bitmap": SFDSFFSTSGSGSSGSSGSDGSD
     *
     * },
     */


    @SerializedName("bitmap")
    private String bitmap;

    public Post(String bitmap) {
        this.bitmap = bitmap;

    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public String getBitmap() {
        return bitmap;
    }

}
