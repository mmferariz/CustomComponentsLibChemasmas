package com.chemasmas.customcomponentslibrary

import java.io.Serializable
import kotlin.random.Random

class TimeSlot(val x:Int,val tag:String, var status: Int = UNSELECTED ):Serializable{


    fun dummyStatus() {
        status = Random.nextInt(UNSELECTED, LOCKED+1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TimeSlot

        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + tag.hashCode()
        result = 31 * result + status
        return result
    }


    companion object{
        const val UNSELECTED = 0
        const val SELECTED = 1
        const val LOCKED = 2
    }



}
class ColumnData<T>(val title:String,val data:T,val timeSlots:ArrayList<TimeSlot> = arrayListOf() ) : Serializable