package com.allen.app.core

import com.alibaba.android.arouter.facade.annotation.Route

object RouterHub {
    /**
     * 宿主 App 组件
     */
    private const val APP = "/app"

    /**
     * 内容组件
     */
    private const val INFO = "/INFO"

    /**
     * 直播组件
     */
    private const val LIVE = "/LIVE"

    /**
     * 用户模块组件
     */
    private const val USER = "/USER"

    /**
     * ------------------内容模块----------------
     */
    const val ROUTER_INFO_FRAGMENT = "$INFO/InfoFragment"
    const val ROUTER_DYNAMIC_FRAGMENT = "$INFO/DynamicFragment"

    /**
     * ------------------直播模块----------------
     */
    const val ROUTER_LIVE_INDEX_FRAGMENT = "$LIVE/LiveIndexFragment"


    /**
     * ------------------用户模块----------------
     */
    const val ROUTER_SCRIP_FRAGMENT = "$USER/ScripFragment"
    const val ROUTER_ME_FRAGMENT = "$USER/MeFragment"
}