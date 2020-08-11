package com.allen.base.widget.btm_nav_view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout

abstract class BtmNavItemView : FrameLayout{

    var itemId : Int = 0
    var enableSameClickAnimation : Boolean = false
    var isSameClickAnimation : Boolean = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    protected fun init(context: Context, attrs: AttributeSet?){
        layoutParams = LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT, 1.0f)
        isClickable = true
    }

    open fun isCanClick() : Boolean{
        return true
    }

    open fun setItemSelect(isSelected : Boolean){
        setSelected(isSelected)
    }

    open fun startSameClickAnimation(){
        isSameClickAnimation = true
    }

    open fun endSameClickAnimation(){
        isSameClickAnimation = false
    }

}