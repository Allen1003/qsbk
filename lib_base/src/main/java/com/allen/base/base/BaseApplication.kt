package com.allen.base.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.allen.base.BuildConfig
import com.allen.base.utils.DebugUtils

abstract class BaseApplication : Application() {

    companion object {
        private lateinit var mApplication: BaseApplication
        fun getApplication(): BaseApplication {
            return mApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    /**
     * 父类自己初始化
     * 运行在主线程
     */
    private fun initBaseMainProcess() {
        initEnv()
        initARouter()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {     // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this) // As early as possible, it is recommended to initialize in the Application
    }

    /**
     * 初始化环境
     */
    private fun initEnv() {
        DebugUtils.setEnvLog(isEnvLog())
    }

    /**
     * 是否可以打印日志
     */
    abstract fun isEnvLog(): Boolean

    /**
     * 在主线程初始化子application
     */
    abstract fun initMainProcess()
}