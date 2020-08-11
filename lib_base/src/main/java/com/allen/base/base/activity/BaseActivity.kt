package com.allen.base.base.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.allen.base.base.basic.IView
import com.allen.base.base.basic.ViewDelegate
import com.allen.base.utils.StatusBarUtils
import com.allen.base.widget.PlaceholderView

/**
 * author : Allen
 * date   : 2020/08/03
 * time   : 20:07
 * desc   : 基类 BaseActivity
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    protected lateinit var mDelegate: ViewDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPre()
        setContentView(getLayoutResID())
        mDelegate = ViewDelegate(this.window.decorView)
        initViews()
        initEvents()
        initData()
    }

    abstract fun getLayoutResID(): Int

    /**
     * 子类初始化View
     */
    protected open fun initViews() {}

    /**
     * 初始化控件的事件
     */
    protected open fun initEvents() {}

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * 设置状态栏文本颜色为黑色
     */
    protected fun isStatusBarForegroundBlack(): Boolean = true

    /**
     * 状态栏是否沉浸
     */
    protected open fun isStatusBarTranslate(): Boolean = false

    /**
     * 占位控件
     */
    open fun getPlaceholderView(): PlaceholderView? = null

    open fun initPre() {
        StatusBarUtils.setStatusBarForegroundColor(this, isStatusBarForegroundBlack())
        // 沉浸式
        if (isStatusBarTranslate()) {
            StatusBarUtils.setStatusBarTranslucent(this)
        } else {
            StatusBarUtils.setStatusBarColor(this, Color.WHITE)
        }
    }

    override fun getContext(): Context? = mDelegate.getContext()

    override fun <T : View> findView(id: Int): T = mDelegate.findView(id)

    override fun setVisibility(id: Int, visibility: Int) {
        mDelegate.setVisibility(id, visibility)
    }

    override fun setVisibility(view: View?, visibility: Int) {
        mDelegate.setVisibility(view, visibility)
    }

    override fun setVisibility(id: Int, isVisible: Boolean) {
        mDelegate.setVisibility(id, isVisible)
    }

    override fun setVisibility(view: View?, isVisible: Boolean) {
        mDelegate.setVisibility(view, isVisible)
    }

    override fun setVisible(id: Int) {
        mDelegate.setVisible(id)
    }

    override fun setVisible(view: View?) {
        mDelegate.setVisible(view)
    }

    override fun setGone(id: Int) {
        mDelegate.setGone(id)
    }

    override fun setGone(view: View?) {
        mDelegate.setGone(view)
    }

    override fun setText(id: Int, text: CharSequence?) {
        mDelegate.setText(id, text)
    }

    override fun setText(id: Int, resId: Int) {
        mDelegate.setText(id, resId)
    }

    override fun setText(textView: TextView?, text: CharSequence?) {
        mDelegate.setText(textView, text)
    }

    override fun setText(textView: TextView?, resId: Int) {
        mDelegate.setText(textView, resId)
    }

    override fun setTextColor(textView: TextView?, color: Int) {
        mDelegate.setTextColor(textView, color)
    }

    override fun setTextColor(id: Int, color: Int) {
        mDelegate.setTextColor(id, color)
    }

    override fun setTextSize(id: Int, size: Float) {
        mDelegate.setTextSize(id, size)
    }

    override fun setTextSize(textView: TextView?, size: Float) {
        mDelegate.setTextSize(textView, size)
    }

    override fun setImageResource(imageView: ImageView?, resId: Int) {
        mDelegate.setImageResource(imageView, resId)
    }

    override fun setImageBitmap(imageView: ImageView?, bitmap: Bitmap?) {
        mDelegate.setImageBitmap(imageView, bitmap)
    }

    override fun setImageDrawable(imageView: ImageView?, drawable: Drawable?) {
        mDelegate.setImageDrawable(imageView, drawable)
    }

    override fun showToast(content: String?) {
        mDelegate.showToast(content)
    }

    override fun showToast(resId: Int) {
        mDelegate.showToast(resId)
    }

    override fun showCustomToast(content: String?) {
        mDelegate.showCustomToast(content)
    }

    override fun showCustomToast(resId: Int) {
        mDelegate.showCustomToast(resId)
    }

    override fun showLongToast(content: String?) {
        mDelegate.showLongToast(content)
    }

    override fun showLongToast(resId: Int) {
        mDelegate.showLongToast(resId)
    }

    override fun showDialogToast(resId: Int) {
        mDelegate.showDialogToast(resId)
    }

    override fun showDialogToast(content: String?) {
        mDelegate.showDialogToast(content)
    }

    override fun showDialogToast(content: String?, duration: Long) {
        mDelegate.showDialogToast(content, duration)
    }

    protected open fun showLoading() {
        mDelegate.showPageLoading(getPlaceholderView())
    }

    protected open fun showEmpty(msg: String? = null) {
        mDelegate.showPageEmpty(getPlaceholderView(), msg)
    }

    protected open fun showError(msg: String? = null) {
        mDelegate.showPageError(getPlaceholderView(), msg)
    }

    protected open fun showContent() {
        mDelegate.showPageContent(getPlaceholderView())
    }

    protected fun setErrorRetryListener(listener: () -> Unit) {
        mDelegate.setPageErrorRetryListener(getPlaceholderView(), listener)
    }

}