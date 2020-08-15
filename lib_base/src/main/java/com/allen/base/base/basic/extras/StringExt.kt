package com.allen.base.base.basic.extras

import android.text.TextUtils

/**
 * author : Allen
 * date   : 2020/08/15
 * time   : 15:41
 * desc   :
 */
fun String?.isEmpty(): Boolean {
    if (TextUtils.isEmpty(this) || this == "null") {
        return true
    }
    return false
}

fun String?.isNoEmpty(): Boolean {
    return !this.isEmpty()
}