package com.chemasmas.customcomponentslibrary.util

import android.content.Context
import android.widget.LinearLayout
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.chemasmas.customcomponentslibrary.adapters.SlotPicked


fun interface GenerateTimeLine{
    fun build(ll:LinearLayout)
}

fun interface GenerateHeader{
    fun build(context : Context, ll:LinearLayout,headerHeigth:Float,title:String?)
}

fun interface GenerateItemsHeader {
    fun build(context : Context, ll:LinearLayout, headerHeigth:Float,title:String?, weight: Int)
}

fun interface GenerateTimelineTile{
    fun build(context : Context, ll:LinearLayout,value:Int,cellHeigth:Float)
}

fun interface GenerateTimeSlot<T>{
    fun build(ll: LinearLayout,
              slot: TimeSlot,
              item: ColumnData<T>,
              factor: Int,
              lambda: SlotPicked<T>,
              lockedLambda: SlotPicked<T>
    )
}
