package com.allen.app.base

import com.allen.app.utils.BaseAppConfig
import com.allen.base.base.BaseApplication
import com.umeng.commonsdk.UMConfigure


/**
 * 业务application
 */
abstract class IApplication : BaseApplication() {

    override fun initMainProcess() {
        initUm()
    }

    private fun initUm() {
        UMConfigure.init(
            this,
            BaseAppConfig.UM_APP_ID,
            "default",
            UMConfigure.DEVICE_TYPE_PHONE,
            null
        )
    }
}