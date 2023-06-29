package com.assignment.mapdevDigital.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.mapdevDigital.databinding.FragmentDashboardBinding
import com.assignment.mapdevDigital.ui.adapter.LocationRecyclerViewAdapter

class LocationFragment : Fragment() {

    private var _binding:FragmentDashboardBinding?=null
    private val binding get() = _binding!!

    private lateinit var cardListAdapter: LocationRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(layoutInflater)
//        val cardList: List<LocationList> = it.message?.cardProdlist
//        cardListAdapter = LocationRecyclerViewAdapter(cardList, requireActivity())
        binding.rvCardList.adapter = cardListAdapter
        binding.rvCardList.layoutManager =
            LinearLayoutManager(requireActivity())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}