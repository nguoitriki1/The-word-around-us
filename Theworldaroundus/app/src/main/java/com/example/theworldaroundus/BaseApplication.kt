package com.example.theworldaroundus

import android.app.Application
import com.example.theworldaroundus.api.ApiService
import com.example.theworldaroundus.api.RetrofitClient

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        apiService = RetrofitClient.client?.create(ApiService::class.java)
    }

    companion object {
        lateinit var application: Application
        var apiService: ApiService? = null
    }

}