package com.example.onlineshopapp.Api;

import com.example.onlineshopapp.Model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ProductService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ProductService apiService = new Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductService.class);
    @GET("products")
    Call<List<Product>> getAllProducts();
}
