package com.allen.base.widget.round

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.allen.base.R


/**
 * RoundView代理类，处理实现逻辑
 * 参考：https://github.com/H07000223/FlycoRoundView
 */
class RoundViewDelegate(private val view: View, private val context: Context, attrs: AttributeSet?) {

    private val gd_background = GradientDrawable()
    private val gd_background_press = GradientDrawable()

    //背景颜色
    private var backgroundColor: Int = 0

    //背景顔色渐变
    private var backgroundColors = intArrayOf()

    //按下去的背景颜色
    private var backgroundPressColor: Int = 0

    //按下去的背景顔色渐变
    private var backgroundPressColors = intArrayOf()

    //圆角弧度 4个角
    private var cornerRadius: Int = 0

    //圆角弧度上左
    private var cornerRadius_TL: Int = 0

    //圆角弧度上右
    private var cornerRadius_TR: Int = 0

    //圆角弧度下左
    private var cornerRadius_BL: Int = 0

    //圆角弧度下右
    private var cornerRadius_BR: Int = 0

    //描边线条宽度
    private var strokeWidth: Int = 0

    //描边颜色
    private var strokeColor: Int = 0

    //按下去的效果 描边颜色
    private var strokePressColor: Int = 0

    //按下去的文本颜色
    private var textPressColor: Int = 0
    private var isRadiusHalfHeight: Boolean = false
    private var isWidthHeightEqual: Boolean = false
    private var isRippleEnable: Boolean = false
    private val radiusArr = FloatArray(8)

    init {
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView)
        backgroundColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT)
        backgroundPressColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Integer.MAX_VALUE)
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0)
        strokeColor = ta.getColor(R.styleable.RoundTextView_rv_strokeColor, Color.TRANSPARENT)
        strokePressColor = ta.getColor(R.styleable.RoundTextView_rv_strokePressColor, Integer.MAX_VALUE)
        textPressColor = ta.getColor(R.styleable.RoundTextView_rv_textPressColor, Integer.MAX_VALUE)
        isRadiusHalfHeight = ta.getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight, false)
        isWidthHeightEqual = ta.getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual, false)
        cornerRadius_TL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL, 0)
        cornerRadius_TR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR, 0)
        cornerRadius_BL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL, 0)
        cornerRadius_BR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR, 0)
        isRippleEnable = ta.getBoolean(R.styleable.RoundTextView_rv_isRippleEnable, true)
        //渐变背景颜色
        val backgroundColorStart = ta.getColor(R.styleable.RoundTextView_rv_backgroundColorStart, -1)
        val backgroundColorCenter = ta.getColor(R.styleable.RoundTextView_rv_backgroundColorCenter, -1)
        val backgroundColorEnd = ta.getColor(R.styleable.RoundTextView_rv_backgroundColorEnd, -1)
        if (backgroundColor == 0 && backgroundColorStart != -1 && backgroundColorCenter != -1 && backgroundColorEnd != -1) {
            backgroundColors = intArrayOf(backgroundColorStart, backgroundColorCenter, backgroundColorEnd)
        } else if (backgroundColor == 0 && backgroundColorStart != -1 && backgroundColorEnd != -1) {
            backgroundColors = intArrayOf(backgroundColorStart, backgroundColorEnd)
        }
        //按下去的背景颜色
        val backgroundPressColorStart = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColorStart, -1)
        val backgroundPressColorCenter = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColorCenter, -1)
        val backgroundPressColorEnd = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColorEnd, -1)
        if (backgroundPressColor == 0 && backgroundPressColorStart != -1 && backgroundPressColorCenter != -1 && backgroundPressColorEnd != -1) {
            backgroundPressColors = intArrayOf(backgroundPressColorStart, backgroundPressColorCenter, backgroundPressColorEnd)
        } else if (backgroundPressColor == 0 && backgroundPressColorStart != -1 && backgroundPressColorEnd != -1) {
            backgroundPressColors = intArrayOf(backgroundPressColorStart, backgroundPressColorEnd)
        }
        ta.recycle()
    }

    fun setBackgroundColors(backgroundColors: IntArray) {
        this.backgroundColors = backgroundColors
        setBgSelector()
    }

    fun setBackgroundPressColors(backgroundPressColors: IntArray) {
        this.backgroundPressColors = backgroundPressColors
        setBgSelector()
    }

    fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        //清除渐变颜色，否则无效
        backgroundColors = intArrayOf()
        backgroundPressColors = intArrayOf()
        setBgSelector()
    }

    fun setBackgroundPressColor(backgroundPressColor: Int) {
        this.backgroundPressColor = backgroundPressColor
        setBgSelector()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = dp2px(cornerRadius.toFloat())
        setBgSelector()
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = dp2px(strokeWidth.toFloat())
        setBgSelector()
    }

    fun setStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        setBgSelector()
    }

    fun setStrokePressColor(strokePressColor: Int) {
        this.strokePressColor = strokePressColor
        setBgSelector()
    }

    fun setTextPressColor(textPressColor: Int) {
        this.textPressColor = textPressColor
        setBgSelector()
    }

    fun setIsRadiusHalfHeight(isRadiusHalfHeight: Boolean) {
        this.isRadiusHalfHeight = isRadiusHalfHeight
        setBgSelector()
    }

    fun setIsWidthHeightEqual(isWidthHeightEqual: Boolean) {
        this.isWidthHeightEqual = isWidthHeightEqual
        setBgSelector()
    }

    fun setCornerRadius_TL(cornerRadius_TL: Int) {
        this.cornerRadius_TL = cornerRadius_TL
        setBgSelector()
    }

    fun setCornerRadius_TR(cornerRadius_TR: Int) {
        this.cornerRadius_TR = cornerRadius_TR
        setBgSelector()
    }

    fun setCornerRadius_BL(cornerRadius_BL: Int) {
        this.cornerRadius_BL = cornerRadius_BL
        setBgSelector()
    }

    fun setCornerRadius_BR(cornerRadius_BR: Int) {
        this.cornerRadius_BR = cornerRadius_BR
        setBgSelector()
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    fun getBackgroundPressColor(): Int {
        return backgroundPressColor
    }

    fun getCornerRadius(): Int {
        return cornerRadius
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }

    fun getStrokePressColor(): Int {
        return strokePressColor
    }

    fun getTextPressColor(): Int {
        return textPressColor
    }

    fun isRadiusHalfHeight(): Boolean {
        return isRadiusHalfHeight
    }

    fun isWidthHeightEqual(): Boolean {
        return isWidthHeightEqual
    }

    fun getCornerRadius_TL(): Int {
        return cornerRadius_TL
    }

    fun getCornerRadius_TR(): Int {
        return cornerRadius_TR
    }

    fun getCornerRadius_BL(): Int {
        return cornerRadius_BL
    }

    fun getCornerRadius_BR(): Int {
        return cornerRadius_BR
    }

    private fun dp2px(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(sp: Float): Int {
        val scale = this.context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    private fun setDrawable(gd: GradientDrawable, color: Int, colors: IntArray, strokeColor: Int) {
        if (colors.isNotEmpty()) {
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.orientation = GradientDrawable.Orientation.LEFT_RIGHT
            gd.colors = colors
        } else {
            gd.setColor(color)
        }
        if (cornerRadius_TL > 0 || cornerRadius_TR > 0 || cornerRadius_BR > 0 || cornerRadius_BL > 0) {
            radiusArr[0] = cornerRadius_TL.toFloat()
            radiusArr[1] = cornerRadius_TL.toFloat()
            radiusArr[2] = cornerRadius_TR.toFloat()
            radiusArr[3] = cornerRadius_TR.toFloat()
            radiusArr[4] = cornerRadius_BR.toFloat()
            radiusArr[5] = cornerRadius_BR.toFloat()
            radiusArr[6] = cornerRadius_BL.toFloat()
            radiusArr[7] = cornerRadius_BL.toFloat()
            gd.cornerRadii = radiusArr
        } else {
            gd.cornerRadius = cornerRadius.toFloat()
        }

        gd.setStroke(strokeWidth, strokeColor)
    }

    fun setBgSelector() {
        val bg = StateListDrawable()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            setDrawable(gd_background, backgroundColor, backgroundColors, strokeColor)
            val rippleDrawable = RippleDrawable(getPressedColorSelector(backgroundColor, backgroundPressColor), gd_background, null)
            view.background = rippleDrawable
        } else {
            setDrawable(gd_background, backgroundColor, backgroundColors, strokeColor)
            bg.addState(intArrayOf(-android.R.attr.state_pressed), gd_background)
            if (backgroundPressColor != Integer.MAX_VALUE || backgroundPressColors.isNotEmpty() || strokePressColor != Integer.MAX_VALUE) {
                setDrawable(gd_background_press, if (backgroundPressColor == Integer.MAX_VALUE) backgroundColor else backgroundPressColor, backgroundPressColors,
                        if (strokePressColor == Integer.MAX_VALUE) strokeColor else strokePressColor)
                bg.addState(intArrayOf(android.R.attr.state_pressed), gd_background_press)
            }
            view.background = bg
        }

        if (view is TextView) {
            if (textPressColor != Integer.MAX_VALUE) {
                val textColors = view.textColors
                val colorStateList = ColorStateList(
                        arrayOf(intArrayOf(-android.R.attr.state_pressed), intArrayOf(android.R.attr.state_pressed)),
                        intArrayOf(textColors.defaultColor, textPressColor))
                view.setTextColor(colorStateList)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList {
        return ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf(android.R.attr.state_focused), intArrayOf(android.R.attr.state_activated), intArrayOf()),
                intArrayOf(pressedColor, pressedColor, pressedColor, normalColor)
        )
    }
}
