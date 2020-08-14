package com.module.content.ui.info.adapter

import com.allen.app.data.bean.InfoBean
import com.allen.app.data.bean.ProviderBean
import com.allen.base.widget.CollapsibleTextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 13:41
 * desc   : 糗事 文本适配器
 */
class InfoWordAdapter : BaseItemProvider<InfoBean>() {

    override val itemViewType: Int = ProviderBean.PROVIDER_INFO_WORD

    override val layoutId: Int = R.layout.info_adapter_info_word

    override fun convert(helper: BaseViewHolder, item: InfoBean) {
        InfoUserConvert(helper, item)
        helper.getView<CollapsibleTextView>(R.id.tvWord).fullString = item.content
    }
}