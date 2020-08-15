package com.module.content.ui.info.adapter

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.allen.app.data.bean.InfoBean
import com.allen.base.base.basic.extras.isNoEmpty
import com.allen.base.base.basic.extras.visible
import com.allen.base.widget.CollapsibleTextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.content.R
import com.module.content.ui.info.widget.InfoUserView

/**
 * author : Allen
 * date   : 2020/08/14
 * time   : 13:41
 * desc   : 糗事 适配器基类
 */
abstract class InfoBaseAdapter : BaseItemProvider<InfoBean>() {

    override val layoutId: Int = R.layout.info_adapter_info_content

    final override fun convert(helper: BaseViewHolder, item: InfoBean) {
        helper.apply {
            getView<InfoUserView>(R.id.infoUserView).setData(item.user)
            val tvWord = getView<CollapsibleTextView>(R.id.tvWord)
            if (item.content.isNoEmpty()) {
                tvWord.visible()
                tvWord.text = item.content
            }
            val infoContentLayout = getView<FrameLayout>(R.id.infoContentLayout)
            infoContentLayout.removeAllViews()
            val mView = LayoutInflater.from(context).inflate(getContentView(), infoContentLayout)
            convertChild(BaseViewHolder(mView), item)
        }
    }

    abstract fun convertChild(helper: BaseViewHolder, item: InfoBean)

    abstract fun getContentView(): Int
}