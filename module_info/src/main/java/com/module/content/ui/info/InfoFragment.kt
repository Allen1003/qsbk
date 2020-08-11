package com.module.content.ui.info

import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.app.core.RouterHub
import com.allen.base.base.fragment.BaseFragment
import com.module.content.R

/**
 * author : Allen
 * date   : 2020/08/10
 * time   : 17:02
 * desc   :
 */
@Route(path = RouterHub.ROUTER_INFO_FRAGMENT)
class InfoFragment : BaseFragment() {

    override fun getLayoutResID(): Int = R.layout.info_fragment_content
}