package com.chemasmas.customcomponentslibrary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.chemasmas.customcomponentslibrary.TimeSlot.Companion.SELECTED
import com.chemasmas.customcomponentslibrary.TimeSlot.Companion.UNSELECTED
import com.jakewharton.rxbinding3.view.clicks


class TimeSlotsAdapter<T>(
    val timeSlots: ArrayList<TimeSlot>,
    val cellHeigth: Float,
    val lambda: SlotPicked<T>,
    val line: ColumnData<T>
) : RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var context: Context? = null
    var tracker: SelectionTracker<Long>? = null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val fondo: TextView = itemView.findViewById(R.id.fondo)

        /*
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
        }

        fun bind(value: TimeSlot, isActivated: Boolean = false) {
            itemView.isActivated = isActivated
            /*
            value.status = when( value.status){
                UNSELECTED -> if(isActivated) SELECTED else UNSELECTED
                SELECTED -> if(isActivated) UNSELECTED else SELECTED
                LOCKED -> LOCKED
                else -> UNSELECTED
            }*/
        }

         */
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

        val item = timeSlots[position]
        with(holder){
            //fondo.text = timeSlots[position].tag
            fondo.height = context!!.DIPtoPX(cellHeigth)

            fondo.clicks().subscribe {
                lambda.selectSlot(item,line)
            }
        }




        /*
        tracker?.let {
            holder.bind(item, it.isSelected(position.toLong()))
        }

         */
    }

    private fun slotBloqueado() {

    }

    private fun deseleccionarSlot(item: TimeSlot) {
        item.status = UNSELECTED
        notifyDataSetChanged()
    }

    private fun seleccionarSlot(item: TimeSlot) {
        item.status = SELECTED
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = timeSlots.size

    private fun centenasToHours(num: Int): String? {
        val horas = num / 100
        val min = inFixTick(num % 100)
        return context?.resources?.getString(R.string.hour_format, horas, min)
    }

    private fun inFixTick(tick: Int): Int {
        return (tick * 60) / 100
    }

    override fun getItemId(position: Int): Long = position.toLong()
}


fun interface SlotPicked<T>{
    fun selectSlot(item: TimeSlot, line: ColumnData<T>)
}