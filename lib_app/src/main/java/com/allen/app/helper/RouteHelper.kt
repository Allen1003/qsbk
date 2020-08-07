package com.allen.app.helper

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.allen.base.utils.AppUtils
import com.allen.base.utils.Preconditions
import com.allen.base.utils.ToastUtils

/**
 * ARoute工具类
 */
object RouteHelper {

    /**
     * 通过路径进行跳转到新的界面
     */
    fun navigation(path: String) {
        Preconditions.checkNotNull(path, "path不能为空")
        val postcard = ARouter.getInstance().build(path)
        navigation(postcard)
    }

    /**
     * 带参数跳转新页面（拿到Postcard进行传值）
     */
    fun navigation(postcard: Postcard?): Any? {
        if (postcard != null) {
            return postcard.navigation(AppUtils.getContext(), object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                }

                override fun onLost(postcard: Postcard?) {
                    ToastUtils.showToast("页面找不到")
                }
            })
        }
        return null
    }

    /**
     * 带参数跳转新页面（拿到Postcard进行传值）
     */
    fun navigation(postcard: Postcard?, activity: Activity, requestCode: Int): Any? {
        if (postcard != null) {
            return postcard.navigation(activity, requestCode, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                }

                override fun onLost(postcard: Postcard?) {
                    ToastUtils.showToast("页面找不到")
                }
            })
        }
        return null
    }

    /**
     * 带参数跳转新页面（拿到Postcard进行传值）
     */
    fun navigationPostcard(path: String): Postcard {
        Preconditions.checkNotNull(path, "path不能为空")
        return ARouter.getInstance().build(path)
    }

    /**
     * 通过path加载一个fragment
     */
    fun obtainARouterFragment(path: String): Fragment? {
        Preconditions.checkNotNull(path, "Fragment路径不能为空")
        val obj = ARouter.getInstance().build(path).navigation() ?: return null
        Preconditions.checkArgument(obj is Fragment, "加载目标不是BaseFragment")
        return obj as Fragment
    }
}