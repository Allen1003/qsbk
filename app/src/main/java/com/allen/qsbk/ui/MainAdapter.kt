package com.allen.qsbk.ui

import com.allen.qsbk.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * author : Allen
 * date   : 2020/08/06
 * time   : 10:37
 * desc   :
 */
class MainAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.main_test) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.testTv, item)
    }
}