package com.chemasmas.customcomponentslibrary.components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.DIPtoPX
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.adapters.SlotPicked
import com.chemasmas.customcomponentslibrary.adapters.TimeScheduleLineAdapter
import com.chemasmas.customcomponentslibrary.ui.DividerItemDecorationNoLast
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

        val inicioFix = fixInicio(inicio,fin)
        val finFix = fixFin(inicio,fin)
        tick100 = fixTick(tick)
        iniNormal = normalTime(inicioFix,tick100)
        finNormal = normalTime(finFix,tick100)

        initTimeline()

        items_schedule.addItemDecoration(
            DividerItemDecorationNoLast(context, LinearLayoutManager.HORIZONTAL)
        )



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
        items_schedule.adapter = TimeScheduleLineAdapter(items,headerHeigth,iniNormal,finNormal,tick100,cellHeigth,dividerHeight,lambda,lockedLambda)
    }

    private fun initTimeline(
    ) {

        timeline.addView( TextView(context).apply {
            height = context.DIPtoPX(headerHeigth)
        } )

        for ( x in iniNormal..finNormal step tick100){
            timeline.addView( TextView(context).apply {
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