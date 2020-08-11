package com.allen.base.widget.btm_nav_view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import java.util.*

class BtmNavView : LinearLayout, View.OnClickListener {

    private var btmNavViewListener: BtmNavViewListener? = null
    private var items: TreeMap<Int, BtmNavItemView>? = null
    private var curId = -1

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    override fun onClick(v: View?) {
        val time = System.currentTimeMillis()
        if (null == v)
            return
        if (v !is BtmNavItemView)
            return

        val view = findView(v.itemId) ?: return

        if (!view.isCanClick())
            return

        if (curId == view.itemId) {
            if (view.enableSameClickAnimation) {
                if (view.isSameClickAnimation) {
                    return
                }
                view.startSameClickAnimation()
            }
            btmNavViewListener?.onBtmNavViewSameClick(view.itemId)
            return
        }

        if (btmNavViewListener != null && (!btmNavViewListener!!.onBtmNavViewPreClick(view.itemId)))
            return

        var oldId = -1
        if (curId != -1) {
            val oldView = findView(curId)
            if (oldView != null) {
                oldId = curId
                oldView.setItemSelect(false)
            }
        }

        curId = view.itemId
        view.setItemSelect(true)
        btmNavViewListener?.onBtmNavViewClick(view.itemId, oldId)
    }

    private fun init() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    private fun findView(itemId: Int): BtmNavItemView? {
        if (items.isNullOrEmpty())
            return null
        return items!![itemId]
    }

    fun create(items: TreeMap<Int, BtmNavItemView>, defaultSelectedId: Int) {
        this.items = items
        if (items.isNullOrEmpty()) {
            removeAllViews()
            return
        }

        for ((key, value) in items) {
            if (value.visibility != View.VISIBLE) {
                continue
            }
            value.setOnClickListener(this)
            addView(value)
        }

        onClick(findView(defaultSelectedId))
    }

    fun getItems(): TreeMap<Int, BtmNavItemView>?{
        return items
    }

    fun select(id: Int) {
        val time = System.currentTimeMillis()
        onClick(findView(id))
    }

    fun endSameClickAnimation(id: Int) {
        val item = findView(id)
        item?.endSameClickAnimation()
    }

    fun setBtmNavViewListener(btmNavViewListener: BtmNavViewListener?) {
        this.btmNavViewListener = btmNavViewListener
    }

}