package com.chemasmas.customcomponentslibrary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.chemasmas.customcomponentslibrary.ui.DividerItemDecorationNoLast

class TimeScheduleLineAdapter<T>(
    val items: ArrayList<ColumnData<T>>,
    val headerHeigth: Float,
    val iniNormal: Int,
    val finNormal: Int,
    val tick: Int,
    val cellHeigth: Float,
    val lambda:SlotPicked<T>,
    val lockedLambda:SlotPicked<T>

) : RecyclerView.Adapter<TimeScheduleLineAdapter.ViewHolder>() {


    private lateinit var tsAdapter: TimeSlotsAdapter<T>
    private var context: Context? = null
    //var tracker: SelectionTracker<Long>? = null
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
            titulo.text = item.title
            for ( x in iniNormal..(finNormal + tick/2) step (tick/2) ){
                val t = TimeSlot(x,centenasToHours(x)!!)
                if( !item.timeSlots.contains(t) ){

                    item.timeSlots.add(t)
                }
            }
            item.timeSlots.sortBy { slot -> slot.x }

            tsAdapter = TimeSlotsAdapter(item.timeSlots,cellHeigth,lambda,lockedLambda,item)
            rvTimeslots.adapter = tsAdapter

            /*
            val tracker = SelectionTracker.Builder<Long>(
                "seleccion",
                rvTimeslots,
                MyItemKeyProvider(rvTimeslots),
                MyItemDetailsLookup(rvTimeslots),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            )
            .build()

            tsAdapter.tracker = tracker
             */

            /*
            tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        val items = tracker?.selection!!.size()
                    }
                }
            )
*/
            rvTimeslots.addItemDecoration(
                DividerItemDecorationNoLast(context,LinearLayoutManager.VERTICAL)
            )
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


/*
class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        view?.let {
            return (recyclerView.getChildViewHolder(view) as TimeSlotsAdapter.ViewHolder)
                .getItemDetails()
        }
        return null
    }
}

class MyItemKeyProvider(private val recyclerView: RecyclerView) :
    ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int): Long? {
        return recyclerView.adapter?.getItemId(position)
    }

    override fun getPosition(key: Long): Int {
        val viewHolder = recyclerView.findViewHolderForItemId(key)
        return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
    }
}*/
