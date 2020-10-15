package com.chemasmas.customcomponentslibrary.adapters
/*
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.jakewharton.rxbinding3.view.clicks

class TimeScheduleLineAdapter<T>(
    val items: ArrayList<ColumnData<T>>,
    val headerHeigth: Float,
    val iniNormal: Int,
    val finNormal: Int,
    val tick: Int,
    val cellHeigth: Float,
    val dividerHeight: Float,
    val lambda:SlotPicked<T>,
    val lockedLambda:SlotPicked<T>

) : RecyclerView.Adapter<TimeScheduleLineAdapter.ViewHolder>() {


//    private lateinit var tsAdapter: TimeSlotsAdapter<T>
    private var context: Context? = null
    //var tracker: SelectionTracker<Long>? = null
    //private val timeSlots:ArrayList<Pair<Int,String?>> = arrayListOf()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titulo:TextView = itemView.findViewById(R.id.titulo)
        val baseLinea:LinearLayout = itemView.findViewById(R.id.base_linea)
        //val rvTimeslots:RecyclerView = itemView.findViewById(R.id.rv_timeslots)
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
//            for ( x in iniNormal..(finNormal + tick) step (tick/2) ){
            for ( x in iniNormal..(finNormal + tick/2) step (tick/2) ){

//                val t = TimeSlot(x,centenasToHours(x)!!)
                val t = TimeSlot(x,"")
                if( !item.timeSlots.contains(t) ){

                    item.timeSlots.add(t)
                }
            }
            item.timeSlots.sortBy { slot -> slot.x }

            var accum = 1
            var lastSlot:TimeSlot? = null



            for ( slot in item.timeSlots ){

                when(lastSlot?.status){
                    null -> {
                        lastSlot = slot
                    }
                    TimeSlot.UNSELECTED -> {
                        addSlot(baseLinea,lastSlot,item,1)
                        lastSlot = slot
//                        when(slot.status){
//                            TimeSlot.UNSELECTED -> {
//                                addSlot(baseLinea,lastSlot,item,1)
//                                lastSlot = slot
//                            }
//                            TimeSlot.SELECTED -> {
//                                addSlot(baseLinea,lastSlot,item,1)
//                                lastSlot = slot
//                            }
//                            TimeSlot.LOCKED -> {
//                                addSlot(baseLinea,lastSlot,item,1)
//                                lastSlot = slot
//                            }
//                        }
                    }
                    TimeSlot.SELECTED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
                                addSlot(baseLinea,lastSlot,item,accum)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
                                accum +=1
                                lastSlot = slot
                            }
                            TimeSlot.LOCKED -> {
                                addSlot(baseLinea,lastSlot,item,accum)
                                accum = 1
                                lastSlot = slot
                            }
                        }
                    }
                    TimeSlot.LOCKED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
                                addSlot(baseLinea,lastSlot,item,accum)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
                                addSlot(baseLinea,lastSlot,item,accum)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.LOCKED -> {
                                 accum +=1
                                lastSlot = slot
                            }
                        }
                    }
                }
            }
            addSlot(baseLinea,lastSlot!!,item,accum)



        }
    }

    private fun addSlot(ll: LinearLayout, slot: TimeSlot, item: ColumnData<T>, factor: Int) {
        ll.addView(TextView(context).apply {
            text = slot.tag
            gravity = Gravity.CENTER
            //height = context.DIPtoPX((cellHeigth+dividerHeight) * factor )
            height = context.DIPtoPX((cellHeigth) * factor )
//            height = context.DIPtoPX((cellHeigth) * factor )
            background = ContextCompat.getDrawable(context,R.drawable.item_calendar_drawable)
            //Estilos de el status
            when( slot.status){
                TimeSlot.UNSELECTED -> {}
                TimeSlot.SELECTED -> isSelected = true
                TimeSlot.LOCKED -> isEnabled = false
                else -> TimeSlot.UNSELECTED
            }

            //Clicks
            clicks().subscribe {
                when( slot.status){
                    TimeSlot.UNSELECTED -> lambda.selectSlot(slot,item)
                    TimeSlot.SELECTED -> lockedLambda.selectSlot(slot,item)
                    TimeSlot.LOCKED -> lockedLambda.selectSlot(slot,item) //Este no deberia de pasar
                    else -> lockedLambda.selectSlot(slot,item)
                }
            }
        })
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
*/

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
