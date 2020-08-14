package com.module.content.ui.info.adapter

import com.allen.app.data.bean.InfoBean
import com.allen.app.data.bean.ProviderBean
import com.chad.library.adapter.base.BaseProviderMultiAdapter

/**
 * author : Allen
 * date   : 2020/08/13
 * time   : 17:31
 * desc   :
 */
class InfoAdapter : BaseProviderMultiAdapter<InfoBean>() {
    init {
        addItemProvider(InfoWordAdapter())
        addItemProvider(InfoImageAdapter())
        addItemProvider(InfoVideoAdapter())
    }

    override fun getItemType(data: List<InfoBean>, position: Int): Int {
        return when (data[position].format) {
            "word" -> ProviderBean.PROVIDER_INFO_WORD
            "image" -> ProviderBean.PROVIDER_INFO_IMAGE
            "video" -> ProviderBean.PROVIDER_INFO_VIDEO
            else -> ProviderBean.PROVIDER_INFO_WORD
        }
    }
}