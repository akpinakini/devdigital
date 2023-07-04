package com.assignment.mapdevDigital.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class LocationHistoryManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("CalculationHistory", Context.MODE_PRIVATE)
    }

    fun saveLocation(address: String, longitude: Double, latitude: Double) {
        val history = getLocationHistory().toMutableList()
        history.add(address)
        val lattitude = getlattitudeHistory().toMutableList()
        lattitude.add(latitude.toString())
        val longgitude = getlongitudeHistory().toMutableList()
        longgitude.add(longitude.toString())

        val editor = sharedPreferences.edit()
        editor.putStringSet("history", history.toSet())
        editor.putStringSet("latitude", lattitude.toSet())
        editor.putStringSet("longitude", longgitude.toSet())
        editor.apply()
    }

    fun getLocationHistory(): List<String> {
        return sharedPreferences.getStringSet("history", emptySet())?.toList() ?: emptyList()
    }

    fun getlattitudeHistory(): List<String> {
        return sharedPreferences.getStringSet("latitude", emptySet())?.toList() ?: emptyList()
    }

    fun getlongitudeHistory(): List<String> {
        return sharedPreferences.getStringSet("longitude", emptySet())?.toList() ?: emptyList()
    }

    @SuppressLint("SuspiciousIndentation")
    fun deleteLocation(item: String) {
        val history = getLocationHistory().toMutableList()

        history.remove(item)

        val editor = sharedPreferences.edit()
        editor.putStringSet("history", history.toSet())
        editor.putStringSet("latitude", history.toSet())
        editor.putStringSet("longitude", history.toSet())
        editor.apply()
        getLocationHistory()
        getlattitudeHistory()
        getlongitudeHistory()
    }

    fun clearLocationHistory() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
