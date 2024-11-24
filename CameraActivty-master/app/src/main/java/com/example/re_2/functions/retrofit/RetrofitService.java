package com.example.re_2.functions.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("/api/data")
    Call<DataObject> sendData(@Body DataObject dataObject);
}
