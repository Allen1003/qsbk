package com.module.content.ui.dynamic

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.base.base.fragment.BaseFragment
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 19:19
 * desc   :
 */
@Route(path = RouterHub.ROUTER_DYNAMIC_FRAGMENT)
class DynamicFragment : BaseFragment() {
    override fun getLayoutResID(): Int = R.layout.info_fragment_dynamic
}