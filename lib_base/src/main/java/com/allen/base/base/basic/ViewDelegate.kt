package com.allen.base.base.basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.allen.base.R
import com.allen.base.utils.AppUtils.getString
import com.allen.base.utils.ToastUtils
import com.allen.base.utils.ViewUtils
import com.allen.base.widget.PlaceholderView

class ViewDelegate(private val view: View?) : IView {

    override fun getContext(): Context? = view?.context

    override fun <T : View> findView(id: Int): T {
        if (view == null) throw NullPointerException("ViewDelegate：rootView not be null.")
        val resultView = view.findViewById<T?>(id)
        if (resultView == null) {
            throw NullPointerException("ViewDelegate：findView result is null. please check the layoutRes")
        } else {
            return resultView
        }
    }

    override fun setVisibility(id: Int, visibility: Int) {
        setVisibility(findView<View>(id), visibility)
    }

    override fun setVisibility(view: View?, visibility: Int) {
        view?.visibility = visibility
    }

    override fun setVisibility(id: Int, isVisible: Boolean) {
        setVisibility(findView<View>(id), isVisible)
    }

    override fun setVisibility(view: View?, isVisible: Boolean) {
        if (isVisible) {
            setVisible(view)
        } else {
            setGone(view)
        }
    }

    override fun setVisible(id: Int) {
        setVisible(findView<View>(id))
    }

    override fun setVisible(view: View?) {
        if (view?.visibility != View.VISIBLE) {
            view?.visibility = View.VISIBLE
        }
    }

    override fun setGone(id: Int) {
        setGone(findView<View>(id))
    }

    override fun setGone(view: View?) {
        if (view?.visibility != View.GONE) {
            view?.visibility = View.GONE
        }
    }

    override fun setText(id: Int, text: CharSequence?) {
        setText(findView<TextView>(id), text)
    }

    override fun setText(id: Int, resId: Int) {
        setText(id, getString(resId))
    }

    override fun setText(textView: TextView?, text: CharSequence?) {
        textView?.text = text
    }

    override fun setText(textView: TextView?, resId: Int) {
        setText(textView, getString(resId))
    }

    override fun setTextColor(textView: TextView?, color: Int) {
        textView?.setTextColor(color)
    }

    override fun setTextColor(id: Int, color: Int) {
        setTextColor(findView<TextView>(id), color)
    }

    override fun setTextSize(id: Int, size: Float) {
        setTextSize(findView<TextView>(id), size)
    }

    override fun setTextSize(textView: TextView?, size: Float) {
        textView?.textSize = size
    }

    override fun setImageResource(imageView: ImageView?, resId: Int) {
        imageView?.setImageResource(resId)
    }

    override fun setImageBitmap(imageView: ImageView?, bitmap: Bitmap?) {
        imageView?.setImageBitmap(bitmap)
    }

    override fun setImageDrawable(imageView: ImageView?, drawable: Drawable?) {
        imageView?.setImageDrawable(drawable)
    }

    override fun showToast(content: String?) {
        ToastUtils.showToast(content)
    }

    override fun showToast(resId: Int) {
        ToastUtils.showToast(resId)
    }

    override fun showCustomToast(content: String?) {
    }

    override fun showCustomToast(resId: Int) {
    }

    override fun showLongToast(content: String?) {
        ToastUtils.showLong(content)
    }

    override fun showLongToast(resId: Int) {
        ToastUtils.showLong(resId)
    }

    override fun showDialogToast(resId: Int) {
    }

    override fun showDialogToast(content: String?) {
    }

    override fun showDialogToast(content: String?, duration: Long) {
    }

    fun showPageLoading(placeholderView: PlaceholderView?) {
        placeholderView?.showLoading()
    }

    fun showPageEmpty(placeholderView: PlaceholderView?, msg: String?) {
        placeholderView?.showEmpty(msg)
    }


    fun showPageError(placeholderView: PlaceholderView?, msg: String?) {
        placeholderView?.showError(msg)
    }

    fun setPageErrorRetryListener(placeholderView: PlaceholderView?, listener: () -> Unit) {
        placeholderView?.setOnReloadWhenErrorOrEmpty(listener)
    }

    fun showPageContent(placeholderView: PlaceholderView?) {
        placeholderView?.showContent()
    }

    fun setBackIcon(
        toolbar: Toolbar?,
        backIcon: Boolean, color: Int = 0, backClick: () -> Unit
    ) {
        if (backIcon && toolbar != null) {
            toolbar.navigationIcon = ViewUtils.getImageThemeDrawable(
                toolbar.context,
                R.mipmap.ic_back_operation, color
            )
            toolbar.setNavigationOnClickListener {
                backClick.invoke()
            }
        }
    }
}