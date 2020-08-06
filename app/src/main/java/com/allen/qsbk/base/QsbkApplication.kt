package com.allen.qsbk.base

import com.allen.base.base.BaseApplication
import me.jessyan.autosize.AutoSize

/**
 * author : Allen
 * date   : 2020/08/04
 * time   : 18:36
 * desc   : Application
 */
class QsbkApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        AutoSize.initCompatMultiProcess(this)
    }
}