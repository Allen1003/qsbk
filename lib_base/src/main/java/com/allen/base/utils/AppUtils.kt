package com.allen.base.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.allen.base.base.BaseApplication
import java.io.*
import java.util.*


/**
 * @author Pinger
 * @since 3/26/18 10:03 PM
 * App级别的工具类，提供系统的Context和常用的工具类
 */
object AppUtils {

    /**
     *  全局的Handler，发出了消息一定要自己手动关闭掉
     */
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private val mActivityList = LinkedList<Activity>()

    /**
     * 获取全局的Application
     * @return Application
     */
    fun getApplication(): Application {
        return BaseApplication.getApplication()
    }

    /**
     * 获取全局的Context
     * @return Context
     */
    fun getContext(): Context {
        return getApplication().applicationContext
    }

    /**
     * 获取String资源集合
     */
    fun getString(@StringRes id: Int): String {
        return getContext().resources?.getString(id) ?: ""
    }

    /**
     * 获取color资源
     */
    fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(getContext(), color)
    }

    fun getColor(context: Context, @ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    @JvmStatic
    fun getDimension(@DimenRes id: Int): Float {
        getContext().resources?.getDimension(id)?.let { return it }
        return 0F
    }

    /**
     * 获取Drawable对象
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(getContext(), id)
    }

    /**
     * 获取String资源集合
     */
    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return getContext().resources.getStringArray(id)
    }

    /**
     * 获取版本号
     */
    @Suppress("DEPRECATION")
    fun getVersionCode(): Int {
        return getPackageInfo().versionCode
    }

    /**
     * 获取版本名
     */
    fun getVersionName(): String {
        return getPackageInfo().versionName
    }

    /**
     * 获取应用程序名称
     */
    fun getAppName(): String {
        val labelRes = getPackageInfo().applicationInfo.labelRes
        return getContext().resources.getString(labelRes)
    }


    /**
     * 获取当前应用的包名
     */
    fun getPackageName(): String {
        return getContext().packageName
    }


    /**
     * 获取包信息
     */
    fun getPackageInfo(): PackageInfo {
        return getContext().packageManager.getPackageInfo(getContext().packageName, 0)
    }

    /**
     * 手机设置厂商
     */
    fun getDeviceModel(): String {
        return Build.MODEL
    }

    fun getDeviceBrand(): String {
        return Build.BRAND
    }


    /**
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    fun getIMEI(slotId: Int): String {
        return try {
            val manager =
                getContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val method = manager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType!!)
            method.invoke(manager, slotId) as String
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 手机系统版本号，Android 6.0
     */
    fun getOsVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 判断系统是否root
     */
    fun isSystemRoot(): Boolean {
        val binPath = "/system/bin/su"
        val xBinPath = "/system/xbin/su"

        if (File(binPath).exists() && isCanExecute(binPath)) {
            return true
        }
        return File(xBinPath).exists() && isCanExecute(xBinPath)
    }

    private fun isCanExecute(filePath: String): Boolean {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec("ls -l $filePath")
            val bufferReader = BufferedReader(InputStreamReader(process.inputStream))
            val str: String = bufferReader.readLine()
            if (str.length >= 4) {
                var flag: Char = str[3]
                if (flag == 's' || flag == 'x')
                    return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return false
    }

    /**
     * 将activity 移除
     */
    fun removeTopActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity)
        }
    }

    /**
     * 设置栈顶Activity
     */
    fun setTopActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {
            if (mActivityList.last != activity) {
                mActivityList.remove(activity)
                mActivityList.addLast(activity)
            }
        } else {
            mActivityList.addLast(activity)
        }
    }

    /**
     * 获取所有启动过的Activity
     */
    fun getActivityList(): LinkedList<Activity> {
        return mActivityList
    }

    /**
     * Post一个Runnable
     */
    fun post(runnable: Runnable) {
        mHandler.removeCallbacks(null)
        mHandler.post(runnable)
    }

    /**
     * 延时执行一个Runnable
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        mHandler.removeCallbacks(null)
        mHandler.postDelayed(runnable, delayMillis)
    }

    /**
     * 移除之前发起的Post
     */
    fun removeCallbacks() {
        mHandler.removeCallbacks(null)
    }

    /**
     * 跳转Activity，不带任何参数
     */
    fun startActivity(context: Context?, clazz: Class<*>) {
        startActivity(context ?: getContext(), Intent(context ?: getContext(), clazz))
    }

    /**
     * 跳转Activity
     */
    fun startActivity(context: Context?, intent: Intent) {
        context ?: getContext().startActivity(intent)
    }

    /**
     * 打开浏览器
     */
    fun startBrowser(url: String?) {
        try {
            if (!TextUtils.isEmpty(url)) {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                getContext().startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 打开设置, 设置权限
     */
    fun startSettingPermisssion(activity: Activity?) {
        val intent: Intent = Intent()
        try {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS

            //8.0及以后版本使用这两个extra.  >=API 26
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, getUid())

            //5.0-7.1 使用这两个extra.  <= API 25, >=API 21
            intent.putExtra("app_package", getPackageName())
            intent.putExtra("app_uid", getUid())

            activity?.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()

            //其他低版本或者异常情况，走该节点。进入APP设置界面
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.putExtra("package", getPackageName())

            activity?.startActivity(intent)
        }
    }

    /**
     * 判断当前APP是否在前台运行
     */
    fun isAppForeground(): Boolean {
        val activityManager = getContext().getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            if (appProcess.processName == getPackageName() && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }


    /**
     * 获取当前应用的信息
     */
    fun getAppInfo(): AppInfo? = getAppInfo(getContext().packageName)


    /**
     * 获取指定包名的应用信息
     * @param packageName 包名
     */
    fun getAppInfo(packageName: String): AppInfo? {
        return try {
            val pm = getContext().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            getAppInfo(pm, pi)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }

    }

    /**
     * 获取所有的应用信息
     */
    fun getAppsInfo(): List<AppInfo> {
        val list = ArrayList<AppInfo>()
        val pm = getContext().packageManager
        val installedPackages = pm.getInstalledPackages(0)
        for (pi in installedPackages) {
            val ai = getAppInfo(pm, pi) ?: continue
            list.add(ai)
        }
        return list
    }

    @Suppress("DEPRECATION")
    private fun getAppInfo(pm: PackageManager?, pi: PackageInfo?): AppInfo? {
        if (pm == null || pi == null) return null
        val ai = pi.applicationInfo
        val packageName = pi.packageName
        val name = ai.loadLabel(pm).toString()
        val icon = ai.loadIcon(pm)
        val packagePath = ai.sourceDir
        val versionName = pi.versionName
        val versionCode = pi.versionCode
        val isSystem = ApplicationInfo.FLAG_SYSTEM and ai.flags != 0
        return AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem)
    }

    private fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    private fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    private fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/",
            "/system/xbin/",
            "/sbin/",
            "/system/sd/xbin/",
            "/system/bin/failsafe/",
            "/data/local/xbin/",
            "/data/local/bin/",
            "/data/local/"
        )
        for (location in locations) {
            if (File(location + su).exists()) {
                return true
            }
        }
        return false
    }

    /**
     * The application's information.
     */
    class AppInfo(
        packageName: String, name: String, icon: Drawable, packagePath: String,
        versionName: String, versionCode: Int, isSystem: Boolean
    ) {

        var packageName: String? = null
        var name: String? = null
        var icon: Drawable? = null
        var packagePath: String? = null
        var versionName: String? = null
        var versionCode: Int = 0
        var isSystem: Boolean = false

        init {
            this.name = name
            this.icon = icon
            this.packageName = packageName
            this.packagePath = packagePath
            this.versionName = versionName
            this.versionCode = versionCode
            this.isSystem = isSystem
        }

        override fun toString(): String {
            return "pkg name: " + packageName +
                    "\napp icon: " + icon +
                    "\napp name: " + name +
                    "\napp path: " + packagePath +
                    "\napp v name: " + versionName +
                    "\napp v code: " + versionCode +
                    "\nis system: " + isSystem
        }
    }

    fun getUid(): String {
        var uid = ""
        try {
            val pm = getContext().packageManager
            val ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
            uid = ai.uid.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return uid
    }

    fun toString(value: Any?): String {
        return value?.toString() ?: ""
    }

    fun toBoolean(value: Any?): Boolean {
        return value?.toString()?.toBoolean() ?: false
    }

    fun toInt(value: Any?): Int {
        try {
            return value?.toString()?.toDouble()?.toInt() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun toFloat(value: Any?): Float {
        try {
            return value?.toString()?.toDouble()?.toFloat() ?: 0F
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0F
    }

    fun toLong(value: Any?): Long {
        try {
            return value?.toString()?.toDouble()?.toLong() ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0L
    }

    fun toDouble(value: Any?): Double {
        try {
            return value?.toString()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    fun isHorizontalScreen(): Boolean {
        val angle =
            (getApplication().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation;
        Log.e("wall", "手机屏幕的 angle 是多少 $angle")
        if (angle == Surface.ROTATION_90 || angle == Surface.ROTATION_270) {
            //如果屏幕旋转90°或者270°是判断为横屏，横屏规避不展示
            return true
        }
        return false
    }
}