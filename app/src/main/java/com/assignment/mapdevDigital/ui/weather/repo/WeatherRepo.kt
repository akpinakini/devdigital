package com.assignment.mapdevDigital.ui.weather.repo

import com.assignment.mapdevDigital.ui.api.ApiService
import com.assignment.mapdevDigital.ui.api.RetrofitClient
import com.assignment.mapdevDigital.ui.weather.viewModel.NetworkResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.ResponseBody
import org.json.JSONObject

class WeatherRepo {

    private val _wheather = MutableSharedFlow<NetworkResult<ResponseBody>>()
    var apiService: ApiService = RetrofitClient.NetworkInstance().create(ApiService::class.java)


    val wheather = _wheather.asSharedFlow()
    suspend fun getWheather(url: String) {
        _wheather.emit(NetworkResult.Loading())

        val result = apiService.getWeather(url)
        if (result.isSuccessful && result.body() != null) {
            _wheather.emit(NetworkResult.Success(result.body()!!))
        } else if (result.errorBody() != null) {
            val errorObj = JSONObject(result.errorBody()!!.string())
            _wheather.emit(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _wheather.emit(NetworkResult.Error("Something is Wrong"))
        }


    }


}