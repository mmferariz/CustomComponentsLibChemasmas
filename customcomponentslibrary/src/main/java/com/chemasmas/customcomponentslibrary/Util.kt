package com.chemasmas.customcomponentslibrary

import android.util.Log
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
