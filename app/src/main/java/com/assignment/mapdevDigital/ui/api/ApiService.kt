package com.assignment.mapdevDigital.ui.api


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getWeather(@Url url: String): Response<ResponseBody>

}