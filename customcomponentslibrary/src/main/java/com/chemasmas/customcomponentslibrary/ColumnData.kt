package com.chemasmas.customcomponentslibrary

import java.io.Serializable

class TimeSlot(val x:Int,val tag:String, var checked:Boolean = false ){

}
class ColumnData<T>(val title:String,val data:T,val timeSlots:ArrayList<TimeSlot> = arrayListOf() ) : Serializable