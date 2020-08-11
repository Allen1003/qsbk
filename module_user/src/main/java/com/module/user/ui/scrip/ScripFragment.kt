package com.module.user.ui.scrip

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.base.base.fragment.BaseFragment
import com.module.user.R

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 19:19
 * desc   :
 */
@Route(path = RouterHub.ROUTER_SCRIP_FRAGMENT)
class ScripFragment : BaseFragment() {
    override fun getLayoutResID(): Int = R.layout.user_fragment_scrip
}