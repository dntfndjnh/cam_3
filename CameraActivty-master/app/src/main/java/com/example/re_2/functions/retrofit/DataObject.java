package com.example.re_2.functions.retrofit;

public class DataObject {
    private String bitmap;


    // Getter & Setter
    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }





    // toString (for debugging)
    @Override
    public String toString() {
        return "bitmap="+bitmap;
    }
}
