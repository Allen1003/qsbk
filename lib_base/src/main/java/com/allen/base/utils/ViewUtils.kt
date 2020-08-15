package com.allen.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.View.NO_ID
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allen.base.base.basic.extras.isNoEmpty
import com.allen.base.utils.ScreenUtils.getNavigationBarHeightIfRoom
import kotlin.math.abs


/**
 * 与View相关的工具类
 */
object ViewUtils {

    private val TAG: String = ViewUtils::class.java.simpleName

    /**
     * 获取全局的Context
     */
    fun getContext(): Context {
        return AppUtils.getContext()
    }

    /**
     * [TextView]
     * set TextView content. view is not null and content is not null.
     */
    fun setText(view: TextView?, content: String?) {
        view?.text = if(content.isNoEmpty()) content else ""
    }

    /**
     * [View.VISIBLE]
     * safe set view visible.
     */
    fun setVisible(view: View?) {
        if (view != null) {
            if (view.visibility != View.VISIBLE) {
                setVisibility(view, View.VISIBLE)
            }
        } else {
            Log.e(TAG, "setVisible Warning： View is null ")
        }
    }

    /**
     * [View.GONE]
     * safe set view gone.
     */
    fun setGone(view: View?) {
        if (view != null) {
            if (view.visibility != View.GONE) {
                setVisibility(view, View.GONE)
            }
        } else {
            Log.e(TAG, "setGone Warning： View is null ")
        }
    }

    /**
     * [View.INVISIBLE]
     * safe set view invisible.
     */
    fun setInvisible(view: View?) {
        if (view != null) {
            setVisibility(view, View.INVISIBLE)
        } else {
            Log.e(TAG, "setInvisible Warning： View is null ")
        }
    }

    /**
     * safe set view visible,gone or invisible.
     */
    private fun setVisibility(view: View?, visibility: Int) {
        if (view != null) {
            if (visibility == View.VISIBLE) {
                if (view.visibility != View.VISIBLE) {
                    view.visibility = visibility
                }
            } else if (visibility == View.GONE) {
                if (view.visibility != View.GONE) {
                    view.visibility = visibility
                }
            } else {
                view.visibility = visibility
            }
        } else {
            Log.e(TAG, "setVisibility Warning： View is null ")
        }
    }

    fun isVisible(view: View?): Boolean {
        return view != null && view.visibility == View.VISIBLE
    }


    /**
     * set view visible or gone
     */
    fun setVisible(view: View?, isVisible: Boolean) {
        if (isVisible) {
            setVisible(view)
        } else {
            setGone(view)
        }
    }


    /**
     * set TextView content with CharSequence.
     */
    fun setText(view: TextView?, text: CharSequence) {
        if (view != null) {
            view.text = text
        } else {
            Log.e(TAG, "setText Warning： TextView is null ")
        }
    }

    /**
     * set TextView content with resId.
     */
    fun setText(view: TextView?, @StringRes resId: Int) {
        if (view != null && view.context != null) {
            view.text = view.context.getString(resId)
        } else {
            Log.e(TAG, "setText Warning： TextView is null ")
        }
    }

    /**
     * view is enabled.
     */
    fun isEnabled(view: View?): Boolean {
        return view != null && view.isEnabled
    }

    /**
     * view is selected.
     */
    fun isSelected(view: View?): Boolean {
        return view != null && view.isSelected
    }

    /**
     * view is not enabled.
     */
    fun isNotEnable(view: View?): Boolean {
        return view != null && !view.isEnabled
    }

    /**
     * set view is enabled or not isEnable.
     */
    fun setEnabled(view: View?, isEnable: Boolean) {
        if (view != null) {
            view.isEnabled = isEnable
        }
    }

    /**
     * 获取String资源集合
     */
    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return AppUtils.getStringArray(id)
    }

    /**
     * 获取String资源集合
     */
    fun getString(@StringRes id: Int): String {
        return AppUtils.getString(id) ?: ""
    }

    /**
     * 获取Drawable对象
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return AppUtils.getDrawable(id)
    }

    /**
     * paste string to edit
     */
    fun pasteText(): String? {
        val clipboard = getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).coerceToText(getContext()).toString()
        } else null
    }

    /**
     * determine Activity is Port.
     */
    fun isPort(): Boolean {
        return getContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    fun isLand(): Boolean {
        return getContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * dp to px
     */
    @JvmStatic
    fun dp2px(dipValue: Int): Int {
        val scale = getContext().resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun dp2px(dipValue: Float): Float {
        val scale = getContext().resources.displayMetrics.density
        return dipValue * scale + 0.5f
    }

    /**
     * px to dip
     */
    fun px2dp(pxValue: Int): Int {
        val scale = getContext().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Float {
        val scale = getContext().resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }

    /**
     * px to sp
     */
    fun px2sp(pxValue: Int): Int {
        val fontScale = getContext().resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Float {
        val fontScale = getContext().resources.displayMetrics.scaledDensity
        return pxValue / fontScale + 0.5f
    }

    /**
     * sp to px
     */
    fun sp2px(spValue: Int): Int {
        val fontScale = getContext().resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Float {
        val fontScale = getContext().resources.displayMetrics.scaledDensity
        return spValue * fontScale + 0.5f
    }


    /**
     * 获取颜色
     */
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(getContext(), id)
    }

    /**
     * screen height
     */
    fun getScreenHeight(): Int {
        val height: Int
        val dm = DisplayMetrics()
        val windowMgr = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowMgr.defaultDisplay.getRealMetrics(dm)
        height = dm.heightPixels
        return height
    }

    /**
     * screen width
     */
    fun getScreenWidth(context: Context? = null): Int {
        val width: Int
        val dm = DisplayMetrics()
        var mContext: Context? = context
        if (mContext == null) mContext = getContext()
        val windowMgr = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowMgr.defaultDisplay.getRealMetrics(dm)
        width = dm.widthPixels
        return width
    }

    fun isFullScreen(): Boolean {
        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        return screenWidth > screenHeight
    }

    /**
     * 设置字体样式
     *
     * @param textView
     * @param path
     */
    fun setTypeFace(textView: TextView, path: String) {
        val typeface = Typeface.createFromAsset(textView.context.assets, path)
        textView.typeface = typeface
    }

    /**
     * 获取状态栏的高度
     */
    fun getStatusHeight(): Int {
        var statusBarHeight = -1
        val resourceId =
            getContext().resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = getContext().resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 获取底部导航栏高度
     */
    fun getNavigationBarHeight(window: Window? = null): Int {
        return if (checkDeviceHasNavigationBar() && isSystemUiVisible(window)[1]) {
            val resources = getContext().resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            resources.getDimensionPixelSize(resourceId);
        } else 0
    }

    /**
     * 检查手机是否有导航栏
     */
    fun checkDeviceHasNavigationBar(): Boolean {
        var hasNavigationBar = false
        val rs = getContext().resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
            LogUtils.e(e)
        }
        return hasNavigationBar
    }

    /**
     * 不用内容高度和屏幕真实高度作对比来判断。
     * 这里只适用于21以后的版本，方法是从DecorView源码中来的，
     * 测试了模拟器21版本，和我自己手机Android 8.1.0都是有效的
     * api min is 21 version
     * 0:statusbar is visible
     * 1:navigation is visible
     *
     * @return statusbar, navigation是否可见
     */
    fun isSystemUiVisible(window: Window?): BooleanArray {
        val result = booleanArrayOf(true, true)
        if (window == null) {
            return result
        }
        val attributes = window.attributes
        if (attributes != null) {
            result[0] =
                attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN
            //
            val decorView = window.decorView as ViewGroup
            result[1] =
                attributes.systemUiVisibility or decorView.windowSystemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION === 0 && attributes.flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0
        }
        //
        val decorViewObj = window.decorView
        val clazz = decorViewObj.javaClass
        var mLastBottomInset = 0
        var mLastRightInset = 0
        var mLastLeftInset = 0
        try {
            val mLastBottomInsetField = clazz.getDeclaredField("mLastBottomInset")
            mLastBottomInsetField.isAccessible = true
            mLastBottomInset = mLastBottomInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val mLastRightInsetField = clazz.getDeclaredField("mLastRightInset")
            mLastRightInsetField.isAccessible = true
            mLastRightInset = mLastRightInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val mLastLeftInsetField = clazz.getDeclaredField("mLastLeftInset")
            mLastLeftInsetField.isAccessible = true
            mLastLeftInset = mLastLeftInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val isNavBarToRightEdge = mLastBottomInset == 0 && mLastRightInset > 0
        val size =
            if (isNavBarToRightEdge) mLastRightInset else if (mLastBottomInset == 0 && mLastLeftInset > 0) mLastLeftInset else mLastBottomInset
        result[1] = result[1] && size > 0
        return result
    }
    // ---------------------- view的多次点击 --------------------

    private const val MIN_DELAY_TIME = 1000
    private var lastClickTime = 0L

    /**
     * 是不是第一次点击，屏蔽多次点击
     */
    fun isFastClick(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }


    fun getRootView(activity: Activity?): ViewGroup? {
        return activity?.findViewById(android.R.id.content)
    }

    fun initRecyclerView(recyclerView: RecyclerView?) {
        recyclerView?.isNestedScrollingEnabled = false
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setItemViewCacheSize(200)
        val recycledViewPool = RecyclerView.RecycledViewPool()
        recyclerView?.setRecycledViewPool(recycledViewPool)
    }

    /**
     * 判断点击事件是否在某个控件内
     */
    fun inRangeOfView(view: View, ev: MotionEvent, topMargin: Int = 60): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1] - dp2px(topMargin) //修正边缘高度
        return !(ev.x < x || ev.x > x + view.width || ev.y < y || ev.y > y + view.height)
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    fun backgroundAlpha(context: Activity?, bgAlpha: Float) {
        if (context != null) {
            var lp = context.window.attributes
            lp.alpha = bgAlpha //0.0-1.0
            context.window.attributes = lp
        }
    }

    /**
     *  设置EditText文本，并把光标移到最后
     * */
    fun setEditText(view: EditText, data: String) {
        if (TextUtils.isEmpty(data)) {
            view.setText("")
            return
        }

        view.setText(data)
        view.setSelection(data.length)
    }

    /**
     * 注意：
     * getDimension()方法并不是直接拿到dimens.xml文件中的dp或sp值
     * 而是將dimens.xml文件中的dp或sp值乘以屏幕密度（density）来换算成px值
     */
    fun getScreenAdapterDimenPx(dimenResId: Int): Float {
        return AppUtils.getContext().resources.getDimension(dimenResId)
    }

    fun getFontHeight(paint: Paint): Float {
        return abs(paint.ascent() + paint.descent())
    }

    fun getFontHeight1(paint: Paint): Float {
        val fm = paint.fontMetrics
        return (Math.ceil((fm.descent - fm.top).toDouble()) + 2).toFloat()
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setRecycleViewMutilClick(recyclerView: RecyclerView?, flag: Boolean) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            recyclerView?.isMotionEventSplittingEnabled = flag
        }
    }

    fun setTextViewStartIcon(view: TextView?, iconId: Int) {
        if (null == view)
            return
        val drawable = view.context.getDrawable(iconId)
        drawable?.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight())
        view.setCompoundDrawables(drawable, null, null, null)
    }

    fun setTextViewEndIcon(view: TextView?, iconId: Int) {
        if (null == view)
            return
        val drawable = view.context.getDrawable(iconId)
        drawable?.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight())
        view.setCompoundDrawables(null, null, drawable, null)
    }

    fun setTextViewNoIcon(view: TextView?) {
        view?.let {
            it.setCompoundDrawables(null, null, null, null)
        }
    }

    //px
    fun getPortRoomHeight(context: Context): Int {
        val width = getScreenWidth()
        val height = getScreenHeight()
        val portVideoHeight = 9 * width / 16
        return height - getStatusHeight() - portVideoHeight - getNavigationBarHeightIfRoom(context)
    }

    fun setTextViewBold(textView: TextView?, isBold: Boolean = true) {
        textView?.paint?.isFakeBoldText = isBold
    }

    fun setTextMaxLength(textView: TextView?, str: String?, max: Int) {
        textView?.let {
            if (null == str) {
                it.text = ""
                return@let
            }
            if (str.length > max) {
                it.text = "${str.substring(0, max)}..."
            } else {
                it.text = str
            }
        }
    }

    /**
     * 获取RecyclerView列表第一个显示的下标
     */
    fun getFirstVisibleItemPosition(recyclerView: RecyclerView?): Int {
        var layoutManager: LinearLayoutManager? = null
        recyclerView?.layoutManager?.let {
            layoutManager = it as LinearLayoutManager?
        }
        return layoutManager?.findFirstVisibleItemPosition() ?: 0
    }


    fun isNavigationBarExist(activity: Activity?): Boolean {
        if (activity == null) {
            return false
        }

        val vp: ViewGroup? = activity.window?.decorView as ViewGroup?

        if (vp != null) {
            for (i in 0..vp.childCount) {
                if (vp.getChildAt(i).id != NO_ID && "navigationBarBackground" == activity.resources.getResourceEntryName(
                        vp.getChildAt(i).id
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 是否是刘海屏
     */
    fun isCutScreen(activity: Activity?): Boolean {
        if (activity == null) {
            return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val windowInsets: WindowInsets? = activity.window?.decorView?.rootWindowInsets
            if (windowInsets != null) {
                val displayCutout: DisplayCutout? = windowInsets.displayCutout
                if (displayCutout != null) {
                    val rectList: List<Rect> = displayCutout.boundingRects
                    if (rectList.isNotEmpty()) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 获取主题颜色的Drawable
     */
    fun getImageThemeDrawable(context: Context?, resId: Int, color: Int): Drawable? {
        var drawable = getDrawable(resId)?.mutate()
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable)
            context?.let {
                if (color != 0) {
                    DrawableCompat.setTint(drawable, color)
                }
            }
        }
        return drawable
    }
}