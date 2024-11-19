package com.example.re_2.functions.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retrofit_interface {
    @GET("image")
    Call<data_model> test_api_get(
            @Query("bitmap") String bitmap);
}
