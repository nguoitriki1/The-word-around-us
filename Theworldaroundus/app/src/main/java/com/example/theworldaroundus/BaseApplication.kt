package com.example.theworldaroundus

import android.app.Application
import com.example.theworldaroundus.api.ApiService
import com.example.theworldaroundus.api.RetrofitClient

class BaseApplication : Application() {

    private var apiService: ApiService? = null

    override fun onCreate() {
        super.onCreate()
        apiService = RetrofitClient.client?.create(ApiService::class.java)
    }

}