package com.wu.demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2020/9/2
 *
 * 用途:
 */


class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mPaint: Paint? = null
    var mInnerPain: Paint? = null
    var mOutterPaint: Paint? = null

    var mCenterText: String? = ""
    var mMaxProgress: Int = 0
    var mCurProgress: Int = 0

    var mCenterTextSize: Int = 0
    var mCenterTextColor: Int = 0
    var mInnerColor: Int = 0
    var mOuterColor: Int = 0
    var mArcWidth: Int = 0

    init {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBarStyle)

        mInnerColor = typeArray.getColor(R.styleable.CustomProgressBarStyle_innerColor, Color.BLACK)
        mOuterColor = typeArray.getColor(R.styleable.CustomProgressBarStyle_outerColor, Color.WHITE)

        mCenterTextColor =
            typeArray.getColor(R.styleable.CustomProgressBarStyle_centerTextColor, Color.BLACK)
        mCenterTextSize = typeArray.getDimensionPixelSize(
            R.styleable.CustomTextViewStyle_customTextSize,
            spTopx(15f)
        )
        mArcWidth = typeArray.getDimensionPixelSize(R.styleable.CustomProgressBarStyle_arcWidth, 15)
        mCenterText = typeArray.getString(R.styleable.CustomProgressBarStyle_centerText)

        mMaxProgress = typeArray.getInt(R.styleable.CustomProgressBarStyle_maxProgress, 100)
        mCurProgress = typeArray.getInt(R.styleable.CustomProgressBarStyle_curProgress, 0)

        typeArray.recycle()

        mInnerPain = Paint()
        mInnerPain!!.isAntiAlias = true
        mInnerPain!!.color = mInnerColor
        mInnerPain!!.style = Paint.Style.STROKE
        mInnerPain!!.strokeCap = Paint.Cap.ROUND
        mInnerPain!!.strokeWidth = mArcWidth.toFloat()


        mOutterPaint = Paint()
        mOutterPaint!!.isAntiAlias = true
        mOutterPaint!!.color = mOuterColor
        mOutterPaint!!.style = Paint.Style.STROKE
        mOutterPaint!!.strokeCap = Paint.Cap.ROUND
        mOutterPaint!!.strokeWidth = mArcWidth.toFloat()


        mPaint = Paint();
        mPaint!!.isAntiAlias = true
        mPaint!!.textSize = mCenterTextSize.toFloat()
        mPaint!!.color = mCenterTextColor

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSize < heightSize) {
            heightSize = widthSize
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var rectF = RectF(
            mArcWidth / 2.toFloat(),
            mArcWidth / 2.toFloat(),
            (width - mArcWidth / 2).toFloat(),
            (width - mArcWidth / 2).toFloat()
        )
        canvas?.drawArc(rectF, 135f, 270f, false, mInnerPain!!)

        if (mCurProgress == 0) return
        Log.e("大小mCurProgress:", "" + mCurProgress)
        Log.e("大小mMaxProgress:", "" + mMaxProgress)

        var b = (mCurProgress.toFloat() / mMaxProgress.toFloat())
        Log.e("大小比例:", "" + 270 * b)
        canvas?.drawArc(rectF, 135f, (270 * b), false, mOutterPaint!!)

        var content = mCurProgress.toString() + " 步数"
        var rect = Rect()

        mPaint!!.getTextBounds(content, 0, content.length, rect)
        canvas!!.drawText(
            content,
            (width - rect.width()) / 2.toFloat(),
            getBaseline(mPaint!!),
            mPaint!!
        )

    }

    fun setMaxProgress(max: Int) {
        mMaxProgress = max
    }

    fun setCurProgress(cur: Int) {
        mCurProgress = cur
        invalidate()
    }

    fun spTopx(sp: Float): Int {
        var dis = context.getResources().getDisplayMetrics().scaledDensity
        return (sp * dis + 0.5f).toInt()
    }

    fun dpTopx(sp: Float): Int {
        var dis = context.getResources().getDisplayMetrics().scaledDensity
        return (sp * dis + 0.5f).toInt()
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
        return height / 2 + ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent)
    }

}