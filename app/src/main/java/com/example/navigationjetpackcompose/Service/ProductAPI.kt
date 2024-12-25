package com.example.navigationjetpackcompose.Service

import com.example.navigationjetpackcompose.Model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {
    @GET("products/")
    fun getData(): Call<ProductResponse>
}
