package com.chemasmas.customcomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.Util
import com.chemasmas.customcomponentslibrary.components.CustomTimeSchedule
import kotlinx.android.synthetic.main.activity_main.*

//import com.chemasmas.customcomponentslibrary.Util

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.health()
        (customC as CustomTimeSchedule<String> ).addTimeLines(
            arrayListOf<ColumnData<String>>(
                ColumnData<String>(
                    "Test2","Test"
                ),ColumnData<String>(
                    "Test3","Test"
                )
            )
        )
    }
}
