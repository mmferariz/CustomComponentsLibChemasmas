package com.chemasmas.customcomponentslibrary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot

class TimeScheduleLineAdapter<T>(
    val items: ArrayList<ColumnData<T>>,
    val headerHeigth: Float,
    val iniNormal: Int,
    val finNormal: Int,
    val tick: Int,
    val cellHeigth: Float
) : RecyclerView.Adapter<TimeScheduleLineAdapter.ViewHolder>() {


    private var context: Context? = null
    //private val timeSlots:ArrayList<Pair<Int,String?>> = arrayListOf()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titulo:TextView = itemView.findViewById(R.id.titulo)
        val rvTimeslots:RecyclerView = itemView.findViewById(R.id.rv_timeslots)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(
            inflater.inflate(
                R.layout.layout_schedule_line,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder){
            titulo.height = context?.DIPtoPX(headerHeigth)!!
            for ( x in iniNormal..(finNormal+tick) step (tick/2) ){
                val t = TimeSlot(x,centenasToHours(x)!!,false)
                item.timeSlots.add(t)
            }
            rvTimeslots.adapter =TimeSlotsAdapter(item.timeSlots,cellHeigth)
        }
    }

    override fun getItemCount(): Int =  items.size

    private fun centenasToHours(num: Int): String? {
        val horas = num / 100
        val min = inFixTick(num %100)
        return context?.resources?.getString(R.string.hour_format,horas,min)
    }

    private fun inFixTick(tick: Int): Int {
        return (tick * 60) / 100
    }

}