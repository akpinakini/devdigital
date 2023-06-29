package com.assignment.mapdevDigital.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.mapdevDigital.databinding.FragmentLocationListBinding
import com.assignment.mapdevDigital.models.LocationList

class LocationRecyclerViewAdapter(
    private val cardList: List<LocationList?>?,
    private val context: Context,
) : RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: FragmentLocationListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var selectedCardIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            if (cardList?.isNotEmpty() == true) {
                with(holder) {


                    binding.text.text = cardList[position]!!.Locations


                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return cardList!!.size
    }
}