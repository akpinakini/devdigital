package com.assignment.mapdevDigital.ui.weather.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.mapdevDigital.ui.weather.repo.WeatherRepo

class WeatherViewFactory(private val repo: WeatherRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel() as T
    }
}