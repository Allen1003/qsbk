package com.allen.base.base.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.allen.base.base.basic.IView
import com.allen.base.base.basic.ViewDelegate
import com.allen.base.utils.AppUtils
import com.allen.base.widget.PlaceholderView

/**
 * author : Allen
 * date   : 2020/08/03
 * time   : 20:07
 * desc   : 基类 Fragment
 */
abstract class BaseFragment : Fragment(), IView {

    private lateinit var mDelegate: ViewDelegate
    private lateinit var mRootView: View

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getLayoutResID(), container, false)
        mDelegate = ViewDelegate(mRootView)
        return mRootView
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
    }

    open fun initViews() {}

    open fun initEvents() {}

    abstract fun getLayoutResID(): Int

    /**
     * 占位控件
     */
    open fun getPlaceholderView(): PlaceholderView? = null

    protected fun setBackgroundColor(color: Int) {
        mRootView.setBackgroundColor(AppUtils.getColor(color))
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

    protected open fun setErrorRetryListener(listener: () -> Unit) {
        mDelegate.setPageErrorRetryListener(getPlaceholderView(), listener)
    }
}