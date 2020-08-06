package com.allen.base.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import java.lang.reflect.Field

object ScreenUtils {
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var sbar = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            sbar = AppUtils.getContext().resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return sbar
    }

    /**
     * 获取虚拟按键的高度
     * 1. 全面屏下
     * 1.1 开启全面屏开关-返回0
     * 1.2 关闭全面屏开关-执行非全面屏下处理方式
     * 2. 非全面屏下
     * 2.1 没有虚拟键-返回0
     * 2.1 虚拟键隐藏-返回0
     * 2.2 虚拟键存在且未隐藏-返回虚拟键实际高度
     */
    fun getNavigationBarHeightIfRoom(context: Context?): Int {
        return if (navigationGestureEnabled(context)) {
            0
        } else getCurrentNavigationBarHeight(context as Activity?)
    }

    /**
     * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
     *
     * @param context
     * @return
     */
    private fun navigationGestureEnabled(context: Context?): Boolean {
        val `val` = Settings.Global.getInt(context?.contentResolver, getDeviceInfo(), 0)
        return `val` != 0
    }

    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
     *
     * @return
     */
    private fun getDeviceInfo(): String {
        val brand = Build.BRAND
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min"

        return if (brand.equals("HUAWEI", ignoreCase = true)) {
            "navigationbar_is_min"
        } else if (brand.equals("XIAOMI", ignoreCase = true)) {
            "force_fsg_nav_bar"
        } else if (brand.equals("VIVO", ignoreCase = true)) {
            "navigation_gesture_on"
        } else if (brand.equals("OPPO", ignoreCase = true)) {
            "navigation_gesture_on"
        } else {
            "navigationbar_is_min"
        }
    }

    /**
     * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
     * @param activity
     * @return
     */
    private fun getCurrentNavigationBarHeight(activity: Activity?): Int {
        return if (isNavigationBarShown(activity)) {
            getNavigationBarHeight(activity)
        } else {
            0
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     * @param activity
     * @return
     */
    private fun isNavigationBarShown(activity: Activity?): Boolean {
        //虚拟键的view,为空或者不可见时是隐藏状态
        val view = activity?.findViewById<View>(android.R.id.navigationBarBackground)
                ?: return false
        val visible = view.visibility
        return !(visible == View.GONE || visible == View.INVISIBLE)
    }

    /**
     * 非全面屏下 虚拟键高度(无论是否隐藏)
     * @param context
     * @return
     */
    fun getNavigationBarHeight(context: Context?): Int {
        var result = 0
        val resourceId = context?.resources?.getIdentifier("navigation_bar_height", "dimen", "android") ?: 0
        if (resourceId > 0) {
            result = context?.resources?.getDimensionPixelSize(resourceId) ?: 0
        }
        return result
    }
}