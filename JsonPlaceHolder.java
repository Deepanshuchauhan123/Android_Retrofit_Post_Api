package com.example.retrofit_working_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {

    @GET("?format=json")
    Call<List<post>>getPosts();
}
