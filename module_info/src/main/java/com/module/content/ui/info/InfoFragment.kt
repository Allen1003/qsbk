package com.module.content.ui.info

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.app.data.bean.Info
import com.allen.app.data.bean.InfoBean
import com.allen.base.base.fragment.BaseRecyclerFragment
import com.allen.base.base.recycler.decorate.DividerItemDecoration
import com.allen.base.utils.AppUtils
import com.module.content.R
import com.module.content.ui.info.adapter.InfoAdapter

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 17:02
 * desc   :
 */
@Route(path = RouterHub.ROUTER_INFO_FRAGMENT)
class InfoFragment : BaseRecyclerFragment<InfoViewModel, Info, InfoBean>() {
    override fun getAdapter() = InfoAdapter()
    override fun getItemDivider() = DividerItemDecoration(AppUtils.getColor(R.color.color_F8F8F8), 1, 12, 12)
}