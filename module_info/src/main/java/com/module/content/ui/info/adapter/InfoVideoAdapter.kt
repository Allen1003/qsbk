package com.module.content.ui.info.adapter

import com.allen.app.data.bean.InfoBean
import com.allen.app.data.bean.ProviderBean
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 13:41
 * desc   : 糗事 视频适配器
 */
class InfoVideoAdapter : BaseItemProvider<InfoBean>() {

    override val itemViewType: Int = ProviderBean.PROVIDER_INFO_VIDEO

    override val layoutId: Int = com.module.content.R.layout.info_adapter_info_video

    override fun convert(helper: BaseViewHolder, item: InfoBean) {
        InfoUserConvert(helper, item)
    }
}