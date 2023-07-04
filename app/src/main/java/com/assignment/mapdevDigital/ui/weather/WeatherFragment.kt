package com.assignment.mapdevDigital.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.assignment.mapdevDigital.BaseFragment
import com.assignment.mapdevDigital.databinding.FragmentHomeBinding
import com.assignment.mapdevDigital.models.ConstantModel.Companion.lattitude
import com.assignment.mapdevDigital.models.ConstantModel.Companion.longitude
import com.assignment.mapdevDigital.ui.weather.viewModel.WeatherViewModel
import com.assignment.mapdevDigital.ui.weather.viewModel.NetworkResult
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONObject

class WeatherFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    val viewModel = WeatherViewModel()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
////        val textView: TextView = binding.textNotifications
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        callWheatherAPI(lattitude, longitude,"fae7190d7e6433ec3a45285ffcf55c86")
        return root
    }

    private fun callWheatherAPI(lattitude: String, longitude: String, APIKEY: String) {
        showLoader()
        val url:String="http://api.openweathermap.org/data/2.5/weather?lat=$lattitude&lon=$longitude&appid=$APIKEY".toString()

        viewModel.getWheather(url)
        lifecycleScope.launchWhenStarted {
            viewModel.whwatherData.collectLatest { networkResult ->

//                dialogBinding.loaderanim.isVisible = false
                when (networkResult) {
                    is NetworkResult.Error -> {
                        hideLoader()
                        Toast.makeText(
                            activity,
                            networkResult.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is NetworkResult.Loading -> {

                        showLoader()

                    }
                    is NetworkResult.Success -> {
                        hideLoader()
                        networkResult.data?.let {
                            val responseString=it.string()
                            val jsonObject=JSONObject(responseString)
                            val main = jsonObject.getJSONObject("main")
                            val weather = jsonObject.getJSONArray("weather")
                            val wind = jsonObject.getJSONObject("wind")
                            val temperature:Double=main.getDouble("temp")
                            val humidity = main.getString("humidity")
                            val rainChances=weather.getJSONObject(0).getString("description")
                            val  windSpeed=wind.getString("speed")
//                            val temperature:Float= (it.main?.temp )!!
//                            val humidity= it.main?.humidity
//                            val rainChances= it.weather?.get(0)?.description
//                            val windSpeed= it.wind?.speed
                            val celcious:Double=kelvinToCelcious(temperature.toFloat())
                            binding.humiditytv.text= humidity.toString()
                            binding.rainTv.text= rainChances.toString()
                            binding.windTv.text= windSpeed.toString()
                            binding.temp.text= celcious.toString().substring(0,5)

//                            val status: String = it.status.toString()

                        }

                    }
                }
            }
        }

    }
    fun kelvinToCelcious(temperature:Float): Double {
//        val kelvin: Float = temperature.toFloat()
        // Kelvin to Degree Celsius Conversion
        // Kelvin to Degree Celsius Conversion
        val celsius = temperature - 273.15f
        return celsius.toDouble()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}