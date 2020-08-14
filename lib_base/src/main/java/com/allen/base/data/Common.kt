package com.allen.base.data

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 10:27
 * desc   :
 */
open class Common<T> {
    val hasMore: Boolean = false
    open var items: T? = null
    val message: String? = null
}

open class ListCommon<T> : Common<List<T>>() {
    override var items: List<T>?
        get() = super.items
        set(value) {}
}