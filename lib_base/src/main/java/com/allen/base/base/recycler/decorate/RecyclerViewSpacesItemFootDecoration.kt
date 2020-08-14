package com.allen.base.base.recycler.decorate

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.allen.base.utils.ViewUtils

/**
 * 给列表头尾的item添加间距
 */
class RecyclerViewSpacesItemFootDecoration(var left: Int = 0, var right: Int = 0, var top: Int = ViewUtils.dp2px(8), var bottom: Int = ViewUtils.dp2px(8)) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildLayoutPosition(view)  //当前条目的position
        val itemCount = state.itemCount - 1 //最后一条的postion
        if (pos == 0) {
            outRect.top = 0
        }else{
            outRect.top = -top
        }
        outRect.left = left
        outRect.right = right
    }
}
