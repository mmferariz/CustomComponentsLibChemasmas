    package com.chemasmas.customcomponentslibrary.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chemasmas.customcomponentslibrary.*
import com.chemasmas.customcomponentslibrary.adapters.SlotPicked
import com.chemasmas.customcomponentslibrary.util.*
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.layout_schedule_range.view.*
import kotlin.math.max
import kotlin.math.min

/**
 *
 * Los valores para el tiempo se datan en el siguiente formato
 * 0 ... 2400
 *
 */
class CustomTimeSchedule<T>  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout( context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_schedule_range,this)
        iniciar(context, attrs)
    }



    private var timeSlot: GenerateTimeSlot<T>? = null
    private var selectedColor: ColorStateList? = null
    private var lockedColor: ColorStateList? = null
    private var headerColor: ColorStateList? = null
    private var headerItemsColor: ColorStateList? = null
    private var gridColor: ColorStateList? = null

    private var selectedColorBG: ColorStateList? = null
    private var lockedColorBG: ColorStateList? = null
    private var headerColorBG: ColorStateList? = null
    private var headerItemsColorBG: ColorStateList? = null

    //    private var selectedColor: Int = Color.WHITE
//    private var lockedColor: Int = Color.WHITE
//    private var headerColor: Int = Color.BLACK
    private var dividerHeight: Float = 0f
    private var headerHeigth: Float = 0f
    private var iniNormal: Int = 0
    private var finNormal: Int = 0
    private var cellHeigth: Float = 0f
    private var tick100: Int = 0

    private var showSlotTitle:Boolean = false
    //private var items:ArrayList<ColumnData<T>> = arrayListOf()

    //Componentes
    private var headerItems: GenerateItemsHeader? = null
    private var headerSAM: GenerateHeader? = null
    private var timelineTile: GenerateTimelineTile? = null
    private var headerColumn: GenerateHeader? = null


    private fun iniciar(context: Context, attrs: AttributeSet?){

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTimeSchedule)
        val inicio = typedArray.getInt(R.styleable.CustomTimeSchedule_iniTime,0)
        val fin = typedArray.getInt(R.styleable.CustomTimeSchedule_finTime,2400)
        val tick = typedArray.getInt(R.styleable.CustomTimeSchedule_tickTime,60)
        headerHeigth = typedArray.getDimension(R.styleable.CustomTimeSchedule_headerHeight,24f)
        cellHeigth = typedArray.getDimension(R.styleable.CustomTimeSchedule_cellHeight,32f)
        dividerHeight = typedArray.getDimension(R.styleable.CustomTimeSchedule_dividerHeight,1f)

        headerColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontHeaderColor)
        headerItemsColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontHeaderItemsColor)
        lockedColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontLockedColor)
        selectedColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_fontSelectedColor)

        headerColorBG = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_backgroundHeaderColor)
        headerItemsColorBG = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_backgroundHeaderItemsColor)
        lockedColorBG = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_backgroundLockedColor)
        selectedColorBG = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_backgroundSelectedColor)

        gridColor = typedArray.getColorStateList(R.styleable.CustomTimeSchedule_gridColor)

        showSlotTitle = typedArray.getBoolean(R.styleable.CustomTimeSchedule_showSlotTitle,true)

        val inicioFix = fixInicio(inicio,fin)
        val finFix = fixFin(inicio,fin)
        tick100 = fixTick(tick)
        iniNormal = normalTime(inicioFix,tick100)
        finNormal = normalTime(finFix,tick100)

        initTimeline()

        typedArray.recycle()
    }


    fun addTimeLines(items:ArrayList<ColumnData<T>>, headerItems:ArrayList<HeaderSlot>,lambda:SlotPicked<T>,lockedLambda:SlotPicked<T>){
//        initTimeItem(items,lambda,lockedLambda)
        //TODO get datos del arreglo

        //items_schedule.adapter = TimeScheduleLineAdapter(items,headerHeigth,iniNormal,finNormal,tick100,cellHeigth,dividerHeight,lambda,lockedLambda)

        raiz.removeAllViews()
        grid_body.removeAllViews()

        for (h in headerItems) {
            this.headerItems?.build(context, grid_body, headerHeigth, h.name, h.weight)
        }
//        grid_body.addView(
//            header
//        )

        for ( columnData in items ){
            val columna:LinearLayout = inflate(
                context,
                R.layout.layout_schedule_line,
                null
            ) as LinearLayout


//            headerSAM?.build(context,
//                columna.findViewById<TextView>(R.id.titulo),headerHeigth)
            headerColumn?.build(context,columna,headerHeigth,columnData.title)
//            columna.findViewById<TextView>(R.id.titulo).apply {
//                text = columnData.title
//                height = context?.DIPtoPX(headerHeigth)!!
//                //setBackgroundColor(headerColor)
//                headerColor?.let {
//                    setTextColor(headerColor)
//                }
//
//            }

            val baseLinea = columna.findViewById<LinearLayout>(R.id.base_linea)

            //Generador de TimeSlots
            for ( x in iniNormal..(finNormal + tick100/2) step (tick100/2) ){
                val t = TimeSlot(x.toString(), x,"")
                if( !columnData.timeSlots.contains(t) ){

                    columnData.timeSlots.add(t)
                }
            }
            columnData.timeSlots.sortBy { slot -> slot.x }



            //Slots efectivos
            var accum = 1
            var lastSlot:TimeSlot? = null

            for ( slot in columnData.timeSlots ){

                when(lastSlot?.status){
                    null -> {
                        lastSlot = slot
                    }
                    TimeSlot.UNSELECTED -> {
//                        addSlot(baseLinea,lastSlot,columnData,1,lambda,lockedLambda)
                        timeSlot?.build(baseLinea,lastSlot,columnData,1,lambda,lockedLambda)
                        lastSlot = slot
                    }


                    TimeSlot.SELECTED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
//                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
                                //Verificar Igualdad
                                if(slot.tag == lastSlot.tag){
                                    accum +=1
                                    lastSlot = slot
                                }else{
                                    timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                    accum = 1
                                    lastSlot = slot
                                }
//                                accum +=1
//                                lastSlot = slot
                            }
                            TimeSlot.LOCKED -> {
//                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                        }
                    }
                    TimeSlot.LOCKED -> {
                        when(slot.status){
                            TimeSlot.UNSELECTED -> {
//                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.SELECTED -> {
//                                addSlot(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                accum = 1
                                lastSlot = slot
                            }
                            TimeSlot.LOCKED -> {
                                if(slot.tag == lastSlot.tag){
                                    accum +=1
                                    lastSlot = slot
                                }else{
                                    timeSlot?.build(baseLinea,lastSlot,columnData,accum,lambda,lockedLambda)
                                    accum = 1
                                    lastSlot = slot
                                }
                            }
                        }
                    }
                }
            }
//            addSlot(baseLinea,lastSlot!!,columnData,accum,lambda,lockedLambda)
            timeSlot?.build(baseLinea,lastSlot!!,columnData,accum,lambda,lockedLambda)

            raiz.addView(
                columna
            )


        }

    }

//    private fun addSlot(
//        ll: LinearLayout,
//        slot: TimeSlot,
//        item: ColumnData<T>,
//        factor: Int,
//        lambda: SlotPicked<T>,
//        lockedLambda: SlotPicked<T>
//    ) {
//        ll.addView(
//            TextView(context).apply {
//            text = slot.tag
//            gravity = Gravity.CENTER
//            //height = context.DIPtoPX((cellHeigth+dividerHeight) * factor )
//            height = context.DIPtoPX((cellHeigth) * factor )
////            height = context.DIPtoPX((cellHeigth) * factor )
//            background = ContextCompat.getDrawable(context,R.drawable.item_calendar_drawable)
//            //Estilos de el status
//            when( slot.status){
//                TimeSlot.UNSELECTED -> {}
//                TimeSlot.SELECTED -> isSelected = true
//                TimeSlot.LOCKED -> isEnabled = false
//                else -> TimeSlot.UNSELECTED
//            }
//
//            when( slot.status){
//                TimeSlot.UNSELECTED -> {}
//                TimeSlot.SELECTED -> selectedColor?.let{ setTextColor( selectedColor )}
//                TimeSlot.LOCKED -> lockedColor?.let{ setTextColor( lockedColor )}
//                else -> TimeSlot.UNSELECTED
//            }
//
//            //Clicks
//            clicks().subscribe {
//                when( slot.status){
//                    TimeSlot.UNSELECTED -> lambda.selectSlot(slot,item)
//                    TimeSlot.SELECTED -> lockedLambda.selectSlot(slot,item)
//                    TimeSlot.LOCKED -> lockedLambda.selectSlot(slot,item) //Este no deberia de pasar
//                    else -> lockedLambda.selectSlot(slot,item)
//                }
//            }
//        })
//    }

    private fun initTimeline(
    ) {

        initComponents()
        GenerateTimeLine { ll ->
            headerSAM?.build(context,ll,headerHeigth,null)
            headerSAM?.build(context,ll,headerHeigth,null)
            for ( x in iniNormal..finNormal step tick100){
                timelineTile?.build(context,ll,x,cellHeigth)
            }
        }.build(timeline)
    }

    private fun initComponents() {

        //HEADER ITEMS
        headerItems = GenerateItemsHeader { ctx, layout, height, title, weight ->
            layout.apply {
                addView( TextView(ctx).apply {
                    text = title
                    setPadding(
                        ctx.DIPtoPX(16f),
                        ctx.DIPtoPX(0f),
                        ctx.DIPtoPX(16f),
                        ctx.DIPtoPX(0f)
                    )
                    this.height = context.DIPtoPX(height)
                    width = ctx.DPtoPX(weight.toFloat() * 140F)
                    val drawable = ContextCompat.getDrawable(context,R.drawable.header_item_tv_divider)
                    background = drawable
                    gravity = Gravity.CENTER
                    headerItemsColor?.let { setTextColor(it.defaultColor) }
                } )
            }
        }

        //HEADER
        headerSAM = GenerateHeader { ctx, layout, heigth,_ ->
            layout.addView( TextView(ctx).apply {
                height = context.DIPtoPX(heigth)
            } )
        }
        //TIMELINETILE
        timelineTile = GenerateTimelineTile { ctx, ll, value, heigth ->
            ll.addView( TextView(ctx).apply {
                //l t r b
                setPadding(
                    ctx.DIPtoPX(16f),
                    ctx.DIPtoPX(0f),
                    ctx.DIPtoPX(16f),
                    ctx.DIPtoPX(0f)
                )

                text = centenasToHours(value)
//                height = context.DIPtoPX(cellHeigth+dividerHeight) * 2
                height = ctx.DIPtoPX(heigth) * 2
                val drawable = ContextCompat.getDrawable(context,R.drawable.time_tv_divider)
                drawable?.setTintList( gridColor )
                background = drawable
                headerItemsColorBG?.let { setBackgroundColor(it.defaultColor) }
                headerItemsColor?.let { setTextColor(it.defaultColor) }
            } )
        }

        //TIMESOLOT
        timeSlot = GenerateTimeSlot { layout, slot, item, factor, lambda, lockedLambda ->
            layout.addView(
                LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    val drawable = ContextCompat.getDrawable(context,R.drawable.time_tv_divider)
                    drawable?.setTintList( gridColor )
                    background = drawable
                    this.dividerDrawable = drawable

                    addView(
                        TextView(context).apply {
                            if(showSlotTitle){
                                slot.subtitle?.let {
                                    if(factor > 2) {
                                        maxLines = 3
                                        ellipsize = TextUtils.TruncateAt.END

                                        val t = slot.tag + "\n" + it
                                        val ssb = SpannableStringBuilder(t)
                                        val span = RelativeSizeSpan(0.8f)
                                        ssb.setSpan(span, slot.tag.length, t.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                        text = ssb
                                    } else {
                                        isSingleLine = true
                                        maxLines = 1
                                        ellipsize = TextUtils.TruncateAt.END

                                        text = slot.tag + " - " + it
                                    }
                                } ?: run {
                                    text = slot.tag
                                }
                            }

                            gravity = Gravity.CENTER
                            //height = context.DIPtoPX((cellHeigth+dividerHeight) * factor )
                            height = context.DIPtoPX((cellHeigth) * factor )

//            height = context.DIPtoPX((cellHeigth) * factor )
                            val drawable = ContextCompat.getDrawable(context,R.drawable.item_calendar_drawable)
//                    drawable?.setTintList(gridColor)
                            background =  drawable

                            when( slot.status){
                                TimeSlot.UNSELECTED -> {}
                                TimeSlot.SELECTED -> {
                                    isSelected = true
                                    selectedColor?.let { setTextColor(selectedColor) }
                                    slot.color?.let { setBackgroundColor(it) } ?: selectedColorBG?.let{ setBackgroundColor( it.defaultColor )}
                                    slot.border?.let {
                                        when(it){
                                            BorderType.RED -> setBackgroundResource(R.drawable.left_border_red)
                                            BorderType.BLUE -> setBackgroundResource(R.drawable.left_border_blue)
                                            BorderType.YELLOW -> setBackgroundResource(R.drawable.left_border_yellow)
                                        }
                                    }
                                }
                                TimeSlot.LOCKED -> {
                                    slot.color?.let { setBackgroundColor(it) } ?: lockedColorBG?.let{ setBackgroundColor( it.defaultColor )}
                                    lockedColor?.let { setTextColor(lockedColor) }
                                    isEnabled = false
                                    slot.border?.let {
                                        when(it){
                                            BorderType.RED -> setBackgroundResource(R.drawable.left_border_red)
                                            BorderType.BLUE -> setBackgroundResource(R.drawable.left_border_blue)
                                            BorderType.YELLOW -> setBackgroundResource(R.drawable.left_border_yellow)
                                        }
                                    }
                                }
                                else -> TimeSlot.UNSELECTED
                            }
                            //Clicks
                            clicks().subscribe {
                                when( slot.status){
                                    TimeSlot.UNSELECTED -> lambda.selectSlot(slot,item)
                                    TimeSlot.SELECTED -> lockedLambda.selectSlot(slot,item)
                                    TimeSlot.LOCKED -> lockedLambda.selectSlot(slot,item) //Este no deberia de pasar
                                    else -> lockedLambda.selectSlot(slot,item)
                                }
                            }
                        }
                    )
                })
        }
        //Column Header
        headerColumn = GenerateHeader { ctx, layout, heigth,title ->
            layout.findViewById<TextView>(R.id.titulo).apply {
                text = title
                height = ctx.DIPtoPX(heigth)
                //setBackgroundColor(headerColor)
                headerColor?.let {
                    setTextColor(headerColor)
                }
                headerColorBG?.let {
                    setBackgroundColor( it.defaultColor )
                }

            }
        }
    }

    private fun normalTime(inicioFix: Int, tick100: Int): Int {
        return inicioFix - (inicioFix % tick100)
    }

    private fun fixTick(tick: Int): Int {
        val tTick = when {
            tick < 1 -> 1
            tick > 60 -> tick % 60
            else -> tick
        }

        return (tTick * 100) / 60
    }

    private fun inFixTick(tick: Int): Int {
        return (tick * 60) / 100
    }

    private fun centenasToHours(num: Int): String {
        val horas = num / 100
        val min = inFixTick(num %100)
        return resources.getString(R.string.hour_format,horas,min)
    }

    private fun fixFin(inicio: Int, fin: Int): Int {
        val tFin = max(inicio,fin)
        return if(tFin>2400)
            2400
        else
            tFin
    }

    private fun fixInicio(inicio: Int, fin: Int): Int {
        val tIni = min(inicio,fin)
        return if(tIni<0)
            0
        else
            tIni
    }

}