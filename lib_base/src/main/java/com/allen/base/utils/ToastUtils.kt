package com.allen.base.utils

import android.widget.Toast
import androidx.annotation.StringRes

/**
 * author : Allen
 * date   : 2020/08/03
 * time   : 20:07
 * desc   : toast工具类
 */
object ToastUtils {
    /**
     * 展示一个吐司，短时间的吐司
     */
    fun showToast(msg: String?) {
        if (msg != null) {
            val toast = Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_SHORT)
            toast.setText(msg)
            toast.show()
        }
    }

    /**
     * 展示一个吐司
     */
    fun showToast(@StringRes strId: Int) {
        val msg = AppUtils.getString(strId)
        showToast(msg)
    }


    /**
     * 长时间展示的吐司
     */
    fun showLong(msg: String?) {
        if (msg != null) {
            Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 长时间展示的吐司
     */
    fun showLong(@StringRes strId: Int) {
        val msg = AppUtils.getString(strId)
        Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_LONG).show()
    }

}