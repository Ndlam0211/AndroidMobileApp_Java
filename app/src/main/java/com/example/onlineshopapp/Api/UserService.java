package com.example.onlineshopapp.Api;

import com.example.onlineshopapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface UserService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();

    UserService userService = new Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserService.class);
    @GET("users")
    Call<List<User>> getAllUsers();
}
