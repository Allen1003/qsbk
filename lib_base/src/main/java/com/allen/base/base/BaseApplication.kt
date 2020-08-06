package com.allen.base.base

import android.app.Application

open class BaseApplication : Application() {

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

}