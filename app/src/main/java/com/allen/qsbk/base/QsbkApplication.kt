package com.allen.qsbk.base

import com.allen.app.base.IApplication
import com.allen.qsbk.BuildConfig
import me.jessyan.autosize.AutoSize

/**
 * author : Allen
 * date   : 2020/08/04
 * time   : 18:36
 * desc   : Application
 */
class QsbkApplication : IApplication() {

    override fun isEnvLog(): Boolean = BuildConfig.ENV_LOG

    override fun initMainProcess() {
        super.initMainProcess()
        AutoSize.initCompatMultiProcess(this)
    }
}