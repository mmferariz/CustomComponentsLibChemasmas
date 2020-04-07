package com.chemasmas.customcomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chemasmas.customcomponentslibrary.Util

//import com.chemasmas.customcomponentslibrary.Util

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.health()
    }
}
