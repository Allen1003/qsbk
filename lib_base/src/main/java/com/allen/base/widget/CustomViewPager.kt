package com.allen.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.viewpager.widget.ViewPager

/**
 * 禁用ViewPager左右切换和动画
 */
class CustomViewPager : ViewPager {

    private var result = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    /**
     * 去除页面切换时的滑动翻页效果
     */
    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }

    /**
     * 禁止左右切换
     */
    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (result) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (result) {
            super.onTouchEvent(arg0)
        } else {
            false
        }
    }

    fun setCanScroll(result: Boolean){
        this.result = result
    }
}
