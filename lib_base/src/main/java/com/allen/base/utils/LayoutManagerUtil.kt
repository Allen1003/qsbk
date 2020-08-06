package com.allen.base.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object LayoutManagerUtil {
    @JvmStatic
    val staggeredGridLayoutManager: StaggeredGridLayoutManager
        get() = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    @JvmStatic
    fun getVerticalLinearLayoutManager(context: Context?): WrapContentLinearLayoutManager {
        return WrapContentLinearLayoutManager(context)
    }

    @JvmStatic
    fun getHorizontalLinearLayoutManager(context: Context?): WrapContentLinearLayoutManager {
        return WrapContentLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    @JvmStatic
    fun getGridLayoutManager(context: Context?, spanCount: Int): GridLayoutManager {
        return GridLayoutManager(context, spanCount)
    }
}
