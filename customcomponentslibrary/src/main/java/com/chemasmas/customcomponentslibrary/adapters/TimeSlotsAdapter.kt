package com.chemasmas.customcomponentslibrary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot

class TimeSlotsAdapter(val timeSlots: ArrayList<TimeSlot>, val cellHeigth: Float) : RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder>() {


    private var context: Context? = null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val fondo: CheckedTextView = itemView.findViewById(R.id.fondo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(
            inflater.inflate(
                R.layout.item_timeslot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            fondo.text = timeSlots[position].tag
            fondo.height = context!!.DIPtoPX(cellHeigth)


            val status = 1
            val drawable = when(status){
                1 -> R.drawable.item_unselected_timeslot
                2 -> R.drawable.item_selected_timeslot
                3 -> R.drawable.item_blocked_timeslot
                else -> R.drawable.item_unselected_timeslot
            }
        }
    }

    override fun getItemCount(): Int = timeSlots.size

    private fun centenasToHours(num: Int): String? {
        val horas = num / 100
        val min = inFixTick(num %100)
        return context?.resources?.getString(R.string.hour_format,horas,min)
    }

    private fun inFixTick(tick: Int): Int {
        return (tick * 60) / 100
    }

}