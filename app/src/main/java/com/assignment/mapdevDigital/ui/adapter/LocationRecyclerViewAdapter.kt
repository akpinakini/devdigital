package com.assignment.mapdevDigital.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.mapdevDigital.databinding.FragmentLocationListBinding
import com.assignment.mapdevDigital.ui.map.LocationHistoryManager

class LocationRecyclerViewAdapter(
    private var cardList: List<String?>?,
    private val context: Context,
    private val locationHistoryManager: LocationHistoryManager,
    private val onCLick: OnCLick,
    private  val lattitudeList: List<String>,
    private val longitudeList: List<String>,
) : RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: FragmentLocationListBinding) :
        RecyclerView.ViewHolder(binding.root)


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
                    binding.text.text = cardList!![position]
                    binding.imageView.setOnClickListener {

                        onCLick.funOnCLick(cardList!![position])
                    }
                    binding.text.setOnClickListener {
                        onCLick.goWheather(lattitudeList[position],longitudeList[position])
                    }


                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return cardList!!.size
    }

    fun filterList(filteredList: ArrayList<String>) {
        cardList = filteredList
        notifyDataSetChanged()
    }


    fun makenotifyDataSeetCHanged(newlist: List<String?>?) {
        cardList=newlist
        this.notifyDataSetChanged()
    }

    interface OnCLick {
        fun funOnCLick(s: String?)
        fun goWheather(s: String?, s1: String)
    }



}