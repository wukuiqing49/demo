package com.wu.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View



/**
 *
 * 作者:吴奎庆
 *
 * 时间:2020/9/1
 *
 * 用途:
 */
class CustomTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    //文字
    var mText:String?=""
    var mTextColor:Int?=Color.BLACK
    var mTextSize:Float?=15f
    var mPaint:Paint?=null

    init {

//        初始化画笔

        //初始化自定义属性
        var typedArray=context.obtainStyledAttributes(attrs,R.styleable.CustomTextViewStyle)
        mTextSize=typedArray.getDimensionPixelSize(R.styleable.CustomTextViewStyle_customTextSize,
            spTopx(15f)
        ) .toFloat()
        mText=typedArray.getString(R.styleable.CustomTextViewStyle_customText)
        mTextColor=typedArray.getColor(R.styleable.CustomTextViewStyle_customTextColor,Color.BLACK)
        typedArray.recycle()

        mPaint=Paint()
        mPaint?.isAntiAlias=true
        mPaint?.textSize= this!!.mTextSize!!
        mPaint?.color= mTextColor as Int

    }

    fun spTopx(sp :Float) :Int {
        var dis=context.getResources().getDisplayMetrics().scaledDensity
        return (sp*dis + 0.5f).toInt()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthMode=MeasureSpec.getMode(widthMeasureSpec)
        var hightMode=MeasureSpec.getMode(heightMeasureSpec)

        var widthSize=MeasureSpec.getSize(widthMeasureSpec)
        if(widthMode==MeasureSpec.AT_MOST){
            var ract= Rect()
            mPaint!!.getTextBounds(mText,0,mText!!.length,ract)
            widthSize= ract.width()
        }
        var hightSize=MeasureSpec.getSize(heightMeasureSpec)
        if(hightMode==MeasureSpec.AT_MOST){
            var ract= Rect()
            mPaint!!.getTextBounds(mText,0,mText!!.length,ract)
            hightSize= ract.height()
        }

        setMeasuredDimension(widthSize,hightSize)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawText(mText!!, 0f ,getBaseline(mPaint!!),mPaint!!)
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     *
     * @param p
     * @param centerY
     * @return 基线和centerY的距离
     */
    fun getBaseline(p: Paint): Float {
        val fontMetrics: Paint.FontMetrics = p.fontMetrics
        return height/2+((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}



