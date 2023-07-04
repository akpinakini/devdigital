package com.assignment.mapdevDigital.ui.map

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.mapdevDigital.R
import com.assignment.mapdevDigital.databinding.FragmentDashboardBinding
import com.assignment.mapdevDigital.models.ConstantModel.Companion.lattitude
import com.assignment.mapdevDigital.models.ConstantModel.Companion.longitude
import com.assignment.mapdevDigital.ui.adapter.LocationRecyclerViewAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale


class LocationFragment : Fragment(), OnMapReadyCallback, TextWatcher,
    LocationRecyclerViewAdapter.OnCLick {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    private lateinit var cardListAdapter: LocationRecyclerViewAdapter
    private lateinit var mMap: GoogleMap
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var currentLocation: Location? = null
    var currentMarker: Marker? = null
    private lateinit var locationHistoryManager: LocationHistoryManager
    lateinit var cardList: List<String>
    lateinit var lattitudeList: List<String>
    lateinit var longitudeList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(layoutInflater)
//        binding.ll.visibility=View.VISIBLE
//        binding.textView.visibility=View.GONE
//        binding.appCompatEditText.visibility=View.GONE
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        locationHistoryManager = LocationHistoryManager(requireActivity())
        binding.appCompatEditText.addTextChangedListener(this)
        cardList = locationHistoryManager.getLocationHistory()
        lattitudeList = locationHistoryManager.getlattitudeHistory()
        longitudeList = locationHistoryManager.getlongitudeHistory()
        cardListAdapter = LocationRecyclerViewAdapter(
            cardList,
            requireActivity(),
            locationHistoryManager,
            this,
            lattitudeList,
            longitudeList,
        )
        binding.rvCardList.adapter = cardListAdapter
        binding.rvCardList.layoutManager = LinearLayoutManager(requireActivity())

        binding.textView.setOnClickListener {
            showMap()
        }
        binding.imageView.setOnClickListener {
            hideMap()
        }
        floatingButton()
        return binding.root
    }

    private fun floatingButton() {
        var fabVisible = false

        // on below line we are adding on click listener
        // for our add floating action button
        binding.idFABAdd.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                binding.idFABSettings.show()

                // on below line we are setting
                // their visibility to visible.
                binding.idFABSettings.visibility = View.VISIBLE

                // on below line we are checking image icon of add fab
                binding.idFABAdd.setImageDrawable(resources.getDrawable(R.drawable.baseline_close_24))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                binding.idFABSettings.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                binding.idFABSettings.visibility = View.GONE

                // on below line we are changing image source for add fab
                binding.idFABAdd.setImageDrawable(resources.getDrawable(R.drawable.baseline_add_24))

                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }
        binding.idFABSettings.setOnClickListener {
            loadWebView()
        }
    }

    private fun loadWebView() {
        findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_notifications2)


//        cancel!!.setOnClickListener { finish() }

    }

    private fun showMap() {
        binding.ll.visibility = View.VISIBLE
        binding.imageView.visibility = View.VISIBLE
        binding.textView.visibility = View.GONE
        binding.appCompatEditText.visibility = View.GONE
        binding.rvCardList.visibility = View.GONE
        fetchLocation()
    }

    private fun hideMap() {
        binding.ll.visibility = View.GONE
        binding.imageView.visibility = View.GONE
        binding.textView.visibility = View.VISIBLE
        binding.appCompatEditText.visibility = View.VISIBLE
        binding.rvCardList.visibility = View.VISIBLE
        cardList = locationHistoryManager.getLocationHistory()
        lattitudeList = locationHistoryManager.getlattitudeHistory()
        longitudeList = locationHistoryManager.getlongitudeHistory()
        cardListAdapter = LocationRecyclerViewAdapter(
            cardList, requireActivity(), locationHistoryManager, this, lattitudeList, longitudeList
        )
        cardListAdapter.makenotifyDataSeetCHanged(cardList)
        binding.rvCardList.adapter = cardListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000
            )
            return
        }

        val task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if (location != null) {
                this.currentLocation = location
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
                mapFragment.getMapAsync(this)
//                this.currentLocation = location
//
////                findNavController().navigate(R.id.action_navigation_dashboard_to_mapFragment)
//                val mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
////                val mapFragment =  SupportMapFragment()
////                val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
////                ft.replace(R.id.ll, SupportMapFragment())
////                ft.commit()
//                mapFragment.getMapAsync(this)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }

        }
    }

    override fun onMapReady(goggleMap: GoogleMap) {
        mMap = goggleMap
        val latlong = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        drawMarker(latlong)

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if (currentMarker != null) {
                    currentMarker?.remove()
                    val newLatLng = LatLng(p0.position.latitude, p0.position.latitude)
                    drawMarker(newLatLng)
                }

            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })
    }

    private fun drawMarker(latlong: LatLng) {
        val markerOption = MarkerOptions().position(latlong).title("I am here").snippet(
            getAddress(latlong.latitude, latlong.longitude).toString()
        ).draggable(true)

        locationHistoryManager.saveLocation(
            getAddress(
                latlong.latitude, latlong.longitude
            ), latlong.longitude, latlong.latitude
        )
        Log.e(TAG, "locationHistoryManger: ${locationHistoryManager.getLocationHistory()}")
        Log.e(TAG, "longitudeHistoryManger: ${locationHistoryManager.getlongitudeHistory()}")
        Log.e(TAG, "lattitudeHistoryManger: ${locationHistoryManager.getlattitudeHistory()}")
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlong))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15f))
        currentMarker = mMap.addMarker(markerOption)
        currentMarker?.showInfoWindow()
    }

    private fun getAddress(lat: Double, lon: Double): String {
        val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
        val addresses = geoCoder.getFromLocation(lat, lon, 1)
        return addresses?.get(0)?.getAddressLine(0).toString()

    }

    private fun filterPlan(plan: String) {
        val filteredList: ArrayList<String> = ArrayList<String>()
        for (item in cardList) {
            if (item.lowercase().contains(plan.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            cardListAdapter.filterList(filteredList)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        filterPlan(binding.appCompatEditText.text.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
    }


    override fun funOnCLick(s: String?) {
        Toast.makeText(requireActivity(), "Item $s removed", Toast.LENGTH_SHORT).show()
        locationHistoryManager.deleteLocation(s!!)
        cardList = locationHistoryManager.getLocationHistory()
        lattitudeList = locationHistoryManager.getlattitudeHistory()
        longitudeList = locationHistoryManager.getlongitudeHistory()
        cardListAdapter = LocationRecyclerViewAdapter(
            cardList,
            requireActivity(),
            locationHistoryManager,
            this,
            lattitudeList,
            longitudeList,
        )
        cardListAdapter.makenotifyDataSeetCHanged(cardList)
        binding.rvCardList.adapter = cardListAdapter

    }

    override fun goWheather(s: String?, s1: String) {
        lattitude = s!!
        longitude = s1
        findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)

    }

    override fun onResume() {
        super.onResume()
        cardListAdapter.notifyDataSetChanged()

    }



}