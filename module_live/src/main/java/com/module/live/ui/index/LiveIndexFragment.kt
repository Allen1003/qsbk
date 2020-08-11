package com.module.live.ui.index

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.base.base.fragment.BaseFragment
import com.module.live.R

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 19:19
 * desc   :
 */
@Route(path = RouterHub.ROUTER_LIVE_INDEX_FRAGMENT)
class LiveIndexFragment : BaseFragment() {
    override fun getLayoutResID(): Int = R.layout.live_fragment_index
}