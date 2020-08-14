package com.module.content.ui.info.adapter

import com.allen.app.data.bean.InfoBean
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 18:57
 * desc   :
 */
class InfoUserConvert(helper: BaseViewHolder, item: InfoBean) {
    init {
        helper.apply {
            setText(R.id.tvUserName, item.user?.login)
        }
    }
}