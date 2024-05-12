package com.example.theworldaroundus

import android.app.Application
import com.example.theworldaroundus.api.ApiService
import com.example.theworldaroundus.api.RetrofitClient
import com.example.theworldaroundus.database.AppDatabase

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        apiService = RetrofitClient.client?.create(ApiService::class.java)
        database = AppDatabase.create(application)
    }

    companion object {
        lateinit var application: Application
        var apiService: ApiService? = null
        var database: AppDatabase? = null
    }

}