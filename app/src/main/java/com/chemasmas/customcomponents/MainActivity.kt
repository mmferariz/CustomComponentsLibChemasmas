package com.chemasmas.customcomponents

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chemasmas.customcomponentslibrary.ColumnData
import com.chemasmas.customcomponentslibrary.TimeSlot
import com.chemasmas.customcomponentslibrary.Util
import com.chemasmas.customcomponentslibrary.adapters.SlotPicked
import com.chemasmas.customcomponentslibrary.components.CustomTimeSchedule
import kotlinx.android.synthetic.main.activity_main.*

//import com.chemasmas.customcomponentslibrary.Util

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.health()
        (customC as CustomTimeSchedule<String>).addTimeLines(
            arrayListOf<ColumnData<String>>(
                ColumnData<String>(
                    "Miyana","DAta",
                    arrayListOf(
                        //Restricciones
                        TimeSlot(800,"",TimeSlot.LOCKED),
                        TimeSlot(850,"",TimeSlot.LOCKED),
                        TimeSlot(900,"",TimeSlot.LOCKED)
                    )
                ),ColumnData<String>(
                    "Tonala","Test"
                    ,arrayListOf(
                        //Restricciones
                        TimeSlot(1200,"",TimeSlot.LOCKED),
                        TimeSlot(1250,"",TimeSlot.LOCKED),
                        TimeSlot(1500,"",TimeSlot.LOCKED)
                    )
                ),
                ColumnData<String>(
                    "Chema","Test"
                    ,arrayListOf(
                        //Restricciones
                        TimeSlot(1900,"",TimeSlot.LOCKED),
                        TimeSlot(1950,"",TimeSlot.LOCKED),
                        TimeSlot(2000,"",TimeSlot.LOCKED)
                    )
                ),
                ColumnData<String>(
                    "Chema","Test"
                )
            )
        , SlotPicked{ item, line ->
                Log.d("Lambda",item.tag)
                Log.d("Lambda",line.data.toString())
                Log.d("Lambda",line.title)
                Toast.makeText(this,"seleccionado",Toast.LENGTH_SHORT).show()
            },
            SlotPicked{ item, line ->
                Log.d("Lambda",item.tag)
                Log.d("Lambda",line.data.toString())
                Log.d("Lambda",line.title)
                Toast.makeText(this,"Esta Bloqueado",Toast.LENGTH_SHORT).show()
            })
    }
}
