package com.chemasmas.customcomponentslibrary.components

import android.content.Context
import android.graphics.*
import android.graphics.drawable.PaintDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.Util.Companion.getPixelsFormDP
import kotlinx.android.synthetic.main.layout_calendar_day.view.*


class CalendarDay: LinearLayout  {


    private var _mes: String = "Enero"
    private var _dia: String = "01\n"
    var mPaint: Paint? = null
    var mRect: Rect? = null
    var mRectF: RectF? = null
    var mDrawable: PaintDrawable = PaintDrawable()
    //private var _fondo: Int? = null

    val density = context.resources.displayMetrics.density

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.layout_calendar_day, this);

        /* mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
         mRect = Rect()
         mRectF = RectF()*/

        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CalendarDay, defStyle, 0
        )

        /* Propiedades*/

        val tMes = a.getString( R.styleable.CalendarDay_mes_string )
        if(tMes != null){
            _mes = tMes
        }
        val tDia = a.getString( R.styleable.CalendarDay_dia_string )
        if( tDia != null){
            _dia = tDia + '\n'
        }



        /*
        this.maxWidth = getPixelsFormDP(40,density)
        this.maxHeight = getPixelsFormDP(60,density)
        this.height = getPixelsFormDP(60,density)
        this.width = getPixelsFormDP(40,density)
        this.layoutParams = LinearLayout.LayoutParams(this.width,this.height)
        this.requestLayout()*/

        /*Drawabe de Fondo*/
        val _fondo = a.getColor(R.styleable.CalendarDay_fondo_card, context.getColor( android.R.color.white ) )
        mDrawable.paint.color = _fondo
        mDrawable.setCornerRadius(25f)
        this.background = mDrawable

        /* Paddings */
        //val paddingDp = a.getDimensionPixelSize( R.styleable.CalendarDay_card_padding,10 )

        /*Alineacion del TExto*/
        fecha.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        a.recycle()
        /*TExto Spanneable*/
        spannealbeInfo()
    }

    private fun spannealbeInfo() {
        val spanneable = SpannableString( _dia + _mes )
        spanneable.setSpan( RelativeSizeSpan(1.5f),0,_dia.length , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanneable.setSpan( StyleSpan(Typeface.BOLD),0,_dia.length , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanneable.setSpan( RelativeSizeSpan(0.8f),_dia.length,_dia.length + _mes.length , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        fecha.text = spanneable
    }
}