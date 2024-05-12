package com.example.theworldaroundus.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.theworldaroundus.BaseApplication
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.data.CountryDb
import com.example.theworldaroundus.utils.ScreenState
import com.example.theworldaroundus.utils.isInternetAvailable
import com.example.theworldaroundus.utils.toCountry
import com.example.theworldaroundus.utils.toCountryDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel : ViewModel() {
    private val retrofitClient = BaseApplication.apiService

    val screenState = MutableStateFlow(ScreenState.IDE)
    val itemCountries = MutableStateFlow<List<Country>?>(null)

    init {
        loadCountries()
    }


    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            screenState.value = ScreenState.LOADING
            var items: List<Country>?
            if (isInternetAvailable(BaseApplication.application)) {
                items = withTimeout(10000) {
                    callApiGetCountries()
                }
                dataSynchronizationWithDatabase(items)
            } else {
                items = getLocaleCountries()
            }

            if (!items.isNullOrEmpty()) {
                items = items.sortedBy {
                    it.name?.common
                }
            }

            setStateFromData(items)
        }

    }

    private suspend fun dataSynchronizationWithDatabase(items: List<Country>?) {
        val countries = items ?: return

        val countCountry = BaseApplication.database?.countryDao()?.getCountCountry()

        if (countCountry == null || countCountry == 0L) {
            BaseApplication.database?.countryDao()?.insertCountries(countries.map {
                it.toCountryDb()
            })
        } else {
            if (countries.size.toLong() != countCountry) {
                BaseApplication.database?.countryDao()?.deleteAllCountries()
                BaseApplication.database?.countryDao()?.insertCountries(countries.map {
                    it.toCountryDb()
                })
            }
        }
    }

    private suspend fun getLocaleCountries(): List<Country>? {
        return BaseApplication.database?.countryDao()?.getAllCountry()?.map { it.toCountry() }
    }

    private suspend fun callApiGetCountries() =
        suspendCancellableCoroutine { continuation ->
            val listCountries = retrofitClient?.getListCountries()
            listCountries?.enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>,
                    response: Response<List<Country>>
                ) {
                    if (response.isSuccessful) {
                        if (continuation.isActive) {
                            continuation.resume(response.body())
                        }
                    } else {
                        if (continuation.isActive) {
                            continuation.resume(null)
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Country>>, p1: Throwable) {
                    if (continuation.isActive) {
                        continuation.resume(null)
                    }
                }

            })
        }

    private fun setStateFromData(items: List<Country>?) {
        if (items == null) {
            itemCountries.value = null
            screenState.value = ScreenState.ERROR
        } else if (items.isEmpty()) {
            itemCountries.value = null
            screenState.value = ScreenState.EMPTY
        } else {
            itemCountries.value = items
            screenState.value = ScreenState.SUCCESS
        }
    }
}