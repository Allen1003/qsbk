package com.module.content.ui.info

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.app.data.bean.InfoBean
import com.allen.base.base.fragment.BaseRecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 17:02
 * desc   :
 */
@Route(path = RouterHub.ROUTER_INFO_FRAGMENT)
class InfoFragment : BaseRecyclerFragment<InfoViewModel, InfoBean>() {
    override fun getAdapter(): BaseQuickAdapter<InfoBean, BaseViewHolder> = InfoAdapter()
}