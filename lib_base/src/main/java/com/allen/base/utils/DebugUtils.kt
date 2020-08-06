package com.allen.base.utils

import android.content.Context
import android.content.pm.ApplicationInfo


/**
 * app的环境控制
 */
object DebugUtils {


    /**
     * 是否是内部使用的一些东西，发布到外界需要设置为false
     */
    private var isEnvLog = false

    /**
     * @return 是否可以打印日志
     */
    fun isEnvLog(): Boolean {
        return isEnvLog
    }

    /**
     * 设置是否可以打印日志
     */
    fun setEnvLog(isEnvLog: Boolean) {
        this.isEnvLog = isEnvLog
    }

    fun isApkInDebug(context: Context): Boolean {
        try {
            val info: ApplicationInfo = context.applicationInfo
            val result: Int = info.flags or ApplicationInfo.FLAG_DEBUGGABLE
            return result != 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}