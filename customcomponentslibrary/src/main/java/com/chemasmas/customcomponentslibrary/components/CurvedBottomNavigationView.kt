package com.chemasmas.customcomponentslibrary.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import com.chemasmas.customcomponentslibrary.R
import com.chemasmas.customcomponentslibrary.toDPF
import com.google.android.material.bottomnavigation.BottomNavigationView

class CurvedBottomNavigationView :
    BottomNavigationView {

    companion object{
        const val CURVE_CIRCLE_RADIUS = 56
    }


    constructor(context: Context) : super(context){
        init(null, null)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int?) {

        var ta:TypedArray = if(defStyle== null){
            context.obtainStyledAttributes(
                attrs,R.styleable.CurvedBottomNavigationView
            )
        }else{
            context.obtainStyledAttributes(
                attrs, R.styleable.CurvedBottomNavigationView, defStyle!!, 0
            )
        }

        nRadius = ta.getDimension(R.styleable.CurvedBottomNavigationView_radioSize,56.toDPF() ).toInt()

        ta.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        //attrs.getAttribute
        init(attrs, null)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(attrs,defStyleAttr)
    }

    private var nRadius: Int = CURVE_CIRCLE_RADIUS
    private var mPath: Path = Path()
    private val mFirstCurveStartPoint: Point = Point()
    private val mFirstCurveEndPoint: Point = Point()
    private val mFirstCurveControlPoint1: Point = Point()
    private val mFirstCurveControlPoint2: Point = Point()

    private var mSecondCurveStartPoint: Point = Point()
    private var mSecondCurveEndPoint: Point = Point()
    private var mSecondCurveControlPoint1: Point = Point()
    private var mSecondCurveControlPoint2: Point = Point()


    private var mNavigationBarHeight: Int = 0
    private var mNavigationBarWidth: Int = 0

    //rivate var mPath
    private var mPaint:Paint = Paint()

    init {
/*val a = context.obtainStyledAttributes(
            attrs, R.styleable.ValueSlider, defStyle, 0
        )*/

        //mPaint.style = Paint.Style.FILL_AND_STROKE
        //mPaint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mNavigationBarWidth = width
        mNavigationBarHeight = height

        mFirstCurveStartPoint.set(
            //( mNavigationBarWidth/2 ) - (CURVE_CIRCLE_RADIUS * 2 ) - (CURVE_CIRCLE_RADIUS/3),
            ( mNavigationBarWidth/2 ) - nRadius,
            0
        )

        mFirstCurveEndPoint.set(
            mNavigationBarWidth / 2,
            //nRadius + (nRadius / 4)
            nRadius
        )

        mSecondCurveStartPoint  = mFirstCurveEndPoint
        mSecondCurveEndPoint.set(
            //(mNavigationBarWidth / 2) + (nRadius * 2) + (nRadius / 3)
            ( mNavigationBarWidth/2 ) + nRadius,
             0
        )


        mFirstCurveControlPoint1.set(
            //mFirstCurveStartPoint.x + nRadius + (nRadius / 4),
            //mFirstCurveStartPoint.x + nRadius ,
            mFirstCurveStartPoint.x,
            //mFirstCurveStartPoint.y
            mFirstCurveStartPoint.y + nRadius/2
        )

        mFirstCurveControlPoint2.set(
            //mFirstCurveEndPoint.x - (nRadius * 2) + nRadius,
            mFirstCurveEndPoint.x - nRadius / 2,
            mFirstCurveEndPoint.y
        )

        mSecondCurveControlPoint1.set(
            //mSecondCurveStartPoint.x + (nRadius * 2) - nRadius,
            mSecondCurveStartPoint.x + nRadius / 2,
            mSecondCurveStartPoint.y
        )
        mSecondCurveControlPoint2.set(
            //mSecondCurveEndPoint.x - (nRadius + (nRadius / 4)),
            //mSecondCurveEndPoint.x - nRadius ,
            mSecondCurveEndPoint.x ,
            //mSecondCurveEndPoint.y
            //mSecondCurveEndPoint.y + nRadius
            mSecondCurveEndPoint.y + nRadius / 2
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPath.reset();
        mPath.moveTo(0f, 0f);
        mPath.lineTo(
            mFirstCurveStartPoint.x.toFloat(),
            mFirstCurveStartPoint.y.toFloat()
        )


        mPath.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(),
            mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(),
            mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(),
            mFirstCurveEndPoint.y.toFloat()
        )

        mPath.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(),
            mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(),
            mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(),
            mSecondCurveEndPoint.y.toFloat()
        )




        mPath.lineTo(
            mNavigationBarWidth.toFloat(),
            0f
        )
        mPath.lineTo(
            mNavigationBarWidth.toFloat(),
            mNavigationBarHeight.toFloat()
        )
        mPath.lineTo(
            0f,
            mNavigationBarHeight.toFloat()
        )
        mPath.close();

        canvas?.drawPath(mPath, mPaint);
    }
}