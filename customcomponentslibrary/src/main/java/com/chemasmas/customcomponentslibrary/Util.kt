package com.chemasmas.customcomponentslibrary

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.widget.Toast

class Util{

    companion object{
        fun health() {
            Log.d("AUTHOR","CHEMASMAS")
        }


        fun getPixelsFormDP(dp:Int,density:Float): Int{
            return (dp * density).toInt()
        }
    }
}

fun Context.DIPtoPX(value:Float): Int {
return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,value, resources.displayMetrics ).toInt()
}

fun Context.DPtoPX(value:Float): Int {
return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, resources.displayMetrics ).toInt()
}


fun Int.toDPF(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun Int.toDP():Int {
    return this.toDPF().toInt()
}