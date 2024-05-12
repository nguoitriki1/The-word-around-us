package com.example.theworldaroundus.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.theworldaroundus.BaseApplication
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.data.CountryDb
import com.example.theworldaroundus.data.Flags
import com.example.theworldaroundus.data.Name
import com.example.theworldaroundus.data.NativeName
import com.example.theworldaroundus.data.RON

const val ACTION_BAR_SIZE_DP = 56

enum class ScreenState {
    IDE, LOADING, SUCCESS, EMPTY, ERROR
}

val commonImageLoader =  ImageLoader.Builder(BaseApplication.application).components {
    add(SvgDecoder.Factory())
}.build()

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

fun Country.toCountryDb(): CountryDb {
    return CountryDb(
        iconAlt = flags?.alt,
        iconPng = flags?.png,
        iconSvg = flags?.svg,
        nameCommon = name?.common ?: "unknown",
        nameOfficial = name?.official,
        nameNativeRonCommon = name?.nativeName?.ron?.common ?: "",
        nameNativeRonOfficial = name?.nativeName?.ron?.official ?: ""
    )
}

fun CountryDb.toCountry(): Country {
    return Country(
        flags = Flags(png = iconPng, svg = iconSvg, alt = iconAlt),
        name = Name(
            common = nameCommon,
            official = nameOfficial,
            nativeName = NativeName(RON(nameNativeRonOfficial, nameNativeRonCommon))
        )
    )
}