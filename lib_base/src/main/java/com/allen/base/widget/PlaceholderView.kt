package com.allen.base.widget

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.allen.base.R
import java.lang.ref.WeakReference
import java.util.*

/**
 * 加载占位控件
 */
class PlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var loadingView: View? = null
    private var emptyView: View? = null
    private var errorView: View? = null
    private var loadingLayout = loadingId
    private var emptyLayout = emptyId
    private var errorLayout = errorId
    private var onReload: (() -> Unit)? = null
    private var currentState = 0

    private val views = ArrayList<View>()


    private val mHandler = Handler(WeakReference(Handler.Callback {
        when (it.what) {
            0 -> setViewState(LOADING_STATE)
            1 -> setViewState(EMPTY_STATE)
            2 -> setViewState(ERROR_STATE)
            4 -> setViewState(CONTENT_STATE)
        }
        true
    }).get())


    interface DefaultLayoutCreator {
        fun createLoadingView(): Int
        fun createErrorView(): Int
        fun createEmptyView(): Int
    }

    init {
        init(context)
    }


    private fun init(context: Context) {
        setBackgroundColor(Color.TRANSPARENT)
        creator?.let {
            loadingLayout = if (it.createLoadingView() <= 0) loadingId else it.createLoadingView()
            emptyLayout = if (it.createEmptyView() <= 0) emptyId else it.createEmptyView()
            errorLayout = if (it.createErrorView() <= 0) errorId else it.createErrorView()
        }

        loadingView = LayoutInflater.from(context).inflate(loadingLayout, null)
        loadingView?.layoutParams = DEFAULT_LAYOUT_PARAMS
        loadingView?.let { views.add(it) }

        emptyView = LayoutInflater.from(context).inflate(emptyLayout, null)
        emptyView?.layoutParams = DEFAULT_LAYOUT_PARAMS
        emptyView?.let { views.add(it) }

        errorView = LayoutInflater.from(context).inflate(errorLayout, null)
        errorView?.layoutParams = DEFAULT_LAYOUT_PARAMS
        errorView?.let { views.add(it) }

        loadingView?.isClickable = true
        loadingView?.setOnClickListener { }

        errorView?.isClickable = true
        errorView?.setOnClickListener {
            if (currentState == ERROR_STATE) {
                mHandler.sendEmptyMessage(0)
                onReload?.invoke()
            }
        }

        emptyView?.setOnClickListener {
            if (currentState == EMPTY_STATE) {
                mHandler.sendEmptyMessage(0)
                onReload?.invoke()
            }
        }
        addView(errorView, DEFAULT_LAYOUT_PARAMS)
        addView(emptyView, DEFAULT_LAYOUT_PARAMS)
        addView(loadingView, DEFAULT_LAYOUT_PARAMS)
        setViewState(CONTENT_STATE)
    }


    fun setOnReloadWhenErrorOrEmpty(onReload: () -> Unit) {
        this.onReload = onReload
    }

    fun showLoading() {
        mHandler.sendEmptyMessage(0)
    }

    fun showEmpty(msg: String? = null) {
        mHandler.sendEmptyMessage(1)
    }

    fun showError(msg: String? = null) {
        mHandler.sendEmptyMessage(2)
    }

    fun showContent() {
        mHandler.sendEmptyMessage(4)
    }


    private fun setViewState(state: Int) {
        this.currentState = state
        if (CONTENT_STATE == state) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
        for (i in views.indices) {
            if (state == i) {
                views[i].visibility = View.VISIBLE
            } else {
                views[i].visibility = View.GONE
            }
        }
    }

    companion object {

        private val loadingId = R.layout.base_smart_loading_layout
        private val emptyId = R.layout.base_smart_empty_layout
        private val errorId = R.layout.base_smart_error_layout
        const val LOADING_STATE = 0
        const val EMPTY_STATE = 1
        const val ERROR_STATE = 2
        const val CONTENT_STATE = 4


        private var creator: DefaultLayoutCreator? = null
        fun setDefaultLayoutCreator(defaultLayoutCreator: DefaultLayoutCreator) {
            creator = defaultLayoutCreator
        }

        private val DEFAULT_LAYOUT_PARAMS =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }


}
