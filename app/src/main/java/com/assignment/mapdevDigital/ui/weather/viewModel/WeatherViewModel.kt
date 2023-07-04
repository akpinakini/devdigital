package com.assignment.mapdevDigital.ui.weather.viewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.mapdevDigital.ui.weather.repo.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class WeatherViewModel : ViewModel(), LifecycleObserver {
    private var wheatherRepo: WeatherRepo? = null

    init {
        wheatherRepo = WeatherRepo()

    }

    val whwatherData: SharedFlow<NetworkResult<ResponseBody>>
        get() = wheatherRepo?.wheather!!


    fun getWheather(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            wheatherRepo?.getWheather(url)
        }
    }


}