package com.example.theworldaroundus.api

import com.example.theworldaroundus.data.Country
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query


interface ApiService {
    @GET("v3.1/all")
    fun getListCountries(@Query("fields") fields: String = "name,flags"): Call<List<Country>>
}