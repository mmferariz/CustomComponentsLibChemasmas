package com.chemasmas.customcomponentslibrary.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.chemasmas.customcomponentslibrary.adapters.SlotPicked
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.layout_schedule_range.view.*
import kotlin.math.max
import kotlin.math.min

/**
 *
 * Los valores para el tiempo se datan en el siguiente formato
 * 0 ... 2400
 *
 */
class CustomTimeSchedule<T>  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout( context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_schedule_range,this)
        iniciar(context, attrs)
    }

    private var selectedColor: ColorStateList? = null
    private var lockedColor: ColorStateList? = null
    private var headerColor: ColorStateList? = null

    //    private var selectedColor: Int = Color.WHITE
//    private var lockedColor: Int = Color.WHITE
//    private var headerColor: Int = Color.BLACK
    private var dividerHeight: Float = 0f
    private var headerHeigth: Float = 0f
    private var iniNormal: Int = 0
    private var finNormal: Int = 0
    private var cellHeigth: Float = 0f
    private var tick100: Int = 0
    //private var items:ArrayList<ColumnData<T>> = arrayListOf()

    //Componentes

    private fun iniciar(context: Context, attrs: AttributeSet?){

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTimeSchedule)
        val inicio = typedArray.getInt(R.styleable.CustomTimeSchedule_iniTime,0)
        val fin = typedArray.getInt(R.styleable.CustomTimeSchedule_finTime,2400)
        val tick = typedArray.getInt(R.styleable.CustomTimeSchedule_tickTime,60)
        headerHeigth = typedArray.getDimension(R.styleable.CustomTimeSchedule_headerHeight,24f)
        cellHeigth = typedArray.getDimension(R.styleable.CustomTimeSchedule_cellHeight,32f)
        dividerHeight = typedArray.getDimension(R.styleable.CustomTimeSchedule_dividerHeight,1f)

        headerColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontHeaderColor)
        lockedColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontLockedColor)
        selectedColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontSelectedColor)

        val inicioFix = fixInicio(inicio,fin)
        val finFix = fixFin(inicio,fin)
        tick100 = fixTick(tick)
        iniNormal = normalTime(inicioFix,tick100)
        finNormal = normalTime(finFix,tick100)

        initTimeline()

//        items_schedule.addItemDecoration(
//            DividerItemDecorationNoLast(context, LinearLayoutManager.HORIZONTAL)
//        )



        typedArray.recycle()
    }


    fun addTimeLines(items:ArrayList<ColumnData<T>>,lambda:SlotPicked<T>,lockedLambda:SlotPicked<T>){
        initTimeItem(items,lambda,lockedLambda)
    }

    private fun initTimeItem(
        items: ArrayList<ColumnData<T>>,
        lambda: SlotPicked<T>,
        lockedLambda: SlotPicked<T>
    ) {
        //TODO get datos del arreglo

        //items_schedule.adapter = TimeScheduleLineAdapter(items,headerHeigth,iniNormal,finNormal,tick100,cellHeigth,dividerHeight,lambda,lockedLambda)

        raiz.removeAllViews()
        for ( columnData in items ){
            val columna =  inflate(
                context,
                R.layout.layout_schedule_line,
                null
            )

            columna.findViewById<TextView>(R.id.titulo).apply {
                text = columnData.title
                height = context?.DIPtoPX(headerHeigth)!!
                //setBackgroundColor(headerColor)
                headerColor?.let {
                    setTextColor(headerColor)
                }

            }

            val baseLinea = columna.findViewById<LinearLayout>(R.id.base_linea)

            //Generador de TimeSlots
            for ( x in iniNormal..(finNormal + tick100/2) step (tick100/2) ){
                val t = TimeSlot(x,"")
                if( !columnData.timeSlots.contains(t) ){

                    columnData.timeSlots.add(t)
                }
            }
            columnData.timeSlots.sortBy { slot -> slot.x }



            //Slots efectivos
            var accum = 1
            var lastSlot:TimeSlot? = null

            for ( slot in columnData.timeSlots ){

                when(lastSlot?.status){
                    null -> {
                        lastSlot = slot
                    }
                    TimeSlot.UNSELECTED -> {
                        addSlot(baseLinea,lastSlot,columnData,1,lambda,lockedLambda)
                        lastSlot = slot
                    }
                    TimeSlot.SELECTED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
                                accum +=1
                                lastSlot = slot
                            }
                            TimeSlot.LOCKED -> {
                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                        }
                    }
                    TimeSlot.LOCKED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
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
            addSlot(baseLinea,lastSlot!!,columnData,accum,lambda,lockedLambda)



            raiz.addView(
                columna
            )


        }

    }

    private fun addSlot(
        ll: LinearLayout,
        slot: TimeSlot,
        item: ColumnData<T>,
        factor: Int,
        lambda: SlotPicked<T>,
        lockedLambda: SlotPicked<T>
    ) {
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

            when( slot.status){
                TimeSlot.UNSELECTED -> {}
                TimeSlot.SELECTED -> selectedColor?.let{ setTextColor( selectedColor )}
                TimeSlot.LOCKED -> lockedColor?.let{ setTextColor( lockedColor )}
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

    private fun initTimeline(
    ) {

        timeline.addView( TextView(context).apply {
            height = context.DIPtoPX(headerHeigth)

        } )

        for ( x in iniNormal..finNormal step tick100){
            timeline.addView( TextView(context).apply {
                //l t r b
                setPadding(
                    context.DIPtoPX(16f),
                    context.DIPtoPX(0f),
                    context.DIPtoPX(16f),
                    context.DIPtoPX(0f)
                )


                text = centenasToHours(x)
//                height = context.DIPtoPX(cellHeigth+dividerHeight) * 2
                height = context.DIPtoPX(cellHeigth) * 2
                background = ContextCompat.getDrawable(context,R.drawable.time_tv_divider)
            } )
        }


    }

    private fun normalTime(inicioFix: Int, tick100: Int): Int {
        return inicioFix - (inicioFix % tick100)
    }

    private fun fixTick(tick: Int): Int {
        val tTick = when {
            tick < 1 -> 1
            tick > 60 -> tick % 60
            else -> tick
        }

        return (tTick * 100) / 60
    }

    private fun inFixTick(tick: Int): Int {
        return (tick * 60) / 100
    }

    private fun centenasToHours(num: Int): String {
        val horas = num / 100
        val min = inFixTick(num %100)
        return resources.getString(R.string.hour_format,horas,min)
    }

    private fun fixFin(inicio: Int, fin: Int): Int {
        val tFin = max(inicio,fin)
        return if(tFin>2400)
            2400
        else
            tFin
    }

    private fun fixInicio(inicio: Int, fin: Int): Int {
        val tIni = min(inicio,fin)
        return if(tIni<0)
            0
        else
            tIni
    }

}