package com.module.content.ui.info

import com.allen.app.data.bean.InfoBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/13
 * time   : 17:31
 * desc   :
 */
class InfoAdapter : BaseQuickAdapter<InfoBean, BaseViewHolder>(R.layout.info_adapter_content) {

    override fun convert(holder: BaseViewHolder, item: InfoBean) {
        holder.apply {
            setText(R.id.testTv, item.content)
        }
    }
}