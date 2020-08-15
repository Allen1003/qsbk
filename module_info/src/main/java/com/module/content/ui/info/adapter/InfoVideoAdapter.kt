package com.module.content.ui.info.adapter

import com.allen.app.data.bean.InfoBean
import com.allen.app.data.bean.ProviderBean
import com.allen.base.base.basic.extras.isNoEmpty
import com.allen.base.base.basic.extras.visible
import com.allen.base.widget.CollapsibleTextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 13:41
 * desc   : 糗事 视频适配器
 */
class InfoVideoAdapter : InfoBaseAdapter() {

    override val itemViewType: Int = ProviderBean.PROVIDER_INFO_VIDEO

    override fun getContentView(): Int = R.layout.info_adapter_info_video

    override fun convertChild(helper: BaseViewHolder, item: InfoBean) {

    }
}