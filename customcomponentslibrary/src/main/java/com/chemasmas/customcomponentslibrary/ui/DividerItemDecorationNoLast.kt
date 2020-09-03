package com.chemasmas.customcomponentslibrary.ui

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.chemasmas.customcomponentslibrary.R

class DividerItemDecorationNoLast(
    val context: Context?,
    orientation: Int,
    @DrawableRes val drawable: Int = R.drawable.line_divider
) : DividerItemDecoration(context, orientation) {

    //TODO evitar que genere el ultimo
    init {
        context?.let { ContextCompat.getDrawable(it, drawable)?.let { setDrawable(it) } }
    }

}