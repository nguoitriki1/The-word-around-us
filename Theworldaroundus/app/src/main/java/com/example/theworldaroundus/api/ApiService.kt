package com.example.theworldaroundus.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query


interface ApiService {
    @GET("v3.1/all")
    suspend fun getListCountries(@Query("fields") fields: String = "name,flags"): Call<List<Any>>
}