package com.example.productpage;

import com.example.productpage.model.product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
   @GET ("products")
    Call<List<product>> getproduct();
}
