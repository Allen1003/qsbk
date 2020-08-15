package com.module.content.ui.info.adapter

import android.widget.ImageView
import com.allen.app.data.bean.InfoBean
import com.allen.app.data.bean.ProviderBean
import com.allen.base.base.basic.extras.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 13:41
 * desc   : 糗事 图片适配器
 */
class InfoImageAdapter : InfoBaseAdapter() {

    override val itemViewType: Int = ProviderBean.PROVIDER_INFO_IMAGE

    override fun getContentView(): Int = R.layout.info_adapter_info_image

    override fun convertChild(helper: BaseViewHolder, item: InfoBean) {
        helper.getView<ImageView>(R.id.ivCover).load(item.high_url)
    }
}