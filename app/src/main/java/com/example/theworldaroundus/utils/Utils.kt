package com.example.theworldaroundus.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

const val ACTION_BAR_SIZE_DP = 56

enum class ScreenState {
    IDE, LOADING, SUCCESS, EMPTY, ERROR
}

@Suppress("DEPRECATION")
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return if (activeNetwork != null) {
            !(activeNetwork.type != ConnectivityManager.TYPE_WIFI && activeNetwork.type != ConnectivityManager.TYPE_MOBILE)
        } else {
            false
        }
    }
}