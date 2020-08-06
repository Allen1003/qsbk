package com.allen.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.allen.base.R
import com.allen.base.utils.LayoutManagerUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import kotlinx.android.synthetic.main.base_recycler_view.view.*

class IRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * 列表分割线
     */
    private var mItemDivider: RecyclerView.ItemDecoration? = null

    /**
     * 布局管理器
     */
    private var mLayoutManager: RecyclerView.LayoutManager =
        LayoutManagerUtil.getVerticalLinearLayoutManager(context)

    /**
     * 刷新头部控件
     */
    private var mRefreshHeader: RefreshHeader? = null

    /**
     * 尾部刷新控件
     */
    private var mRefreshFooter: RefreshFooter? = null


    /**
     * 是否可以自动加载更多，默认可以
     */
    private var isEnableAutoLoadMore = true

    /**
     * 是否可以加载更多，默认可以
     */
    private var isEnableLoadMore = true

    /**
     * 是否可以刷新，默认可以
     */
    private var isEnableRefresh = true

    /**
     * 是否是纯净模式，不展示刷新头和底部，默认false
     */
    private var isEnablePureScrollMode = false

    /**
     * 刷新时是否可以越界回弹
     */
    private var isEnableOverScrollBounce = false

    /**
     * 是否可拖拽
     */
    private var isEnableOverScrollDrag = false

    /**
     * 是否线上头部阴影
     */
    private var isShowShadowView = true


    /**
     * 起始页
     */
    private var startPage = 1

    private var mPage = getStartPage()

    /**
     * 刷新监听
     */
    private var onRefreshListener: (() -> Unit)? = null
    private var onLoadMoreListener: (() -> Unit)? = null

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.base_recycler_view, this)
    }

    /**
     * 初始化参数（必须）
     */
    fun initViewParams() {
        //初始化刷新控件
        if (!isEnablePureScrollMode) {
            getRefreshHeader()?.let { smartRefreshLayout?.setRefreshHeader(it) }
            getRefreshFooter()?.let { smartRefreshLayout?.setRefreshFooter(it) }
        }
        setSmartLayoutAttrs()
    }

    /**
     * 设置SmartRefreshLayout相关属性
     */
    private fun setSmartLayoutAttrs() {
        smartRefreshLayout?.setEnableAutoLoadMore(isEnableAutoLoadMore)
        smartRefreshLayout?.setEnableLoadMore(isEnableLoadMore)
        smartRefreshLayout?.setEnableRefresh(isEnableRefresh)
        smartRefreshLayout?.setEnableOverScrollBounce(isEnableOverScrollBounce)
        smartRefreshLayout?.setEnablePureScrollMode(isEnablePureScrollMode)
        smartRefreshLayout?.setEnableOverScrollDrag(isEnableOverScrollDrag)
        if (isEnableOverScrollDrag) {
            smartRefreshLayout?.setEnablePureScrollMode(true)
        }
        smartRefreshLayout?.setOnRefreshListener {
            mPage = getStartPage()
            onRefreshListener?.invoke()
        }
        smartRefreshLayout?.setOnLoadMoreListener {
            mPage += 1
            onLoadMoreListener?.invoke()
        }
        mRecyclerView?.apply {
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.isVerticalScrollBarEnabled = this@IRecyclerView.isVerticalScrollBarEnabled
            this.isHorizontalScrollBarEnabled = this@IRecyclerView.isHorizontalScrollBarEnabled
            this.layoutManager = this@IRecyclerView.getLayoutManager()
            this.setHasFixedSize(true)
            getItemDivider()?.let {
                addItemDecoration(it)
            }
            val viewPool = RecyclerView.RecycledViewPool()
            setRecycledViewPool(viewPool)
            viewPool.setMaxRecycledViews(0, 10)
        }
        if (isShowShadowView) {
            shadowIv?.visibility = View.VISIBLE
        } else {
            shadowIv?.visibility = View.GONE
        }
    }

    fun getPage(): Int = mPage

    /**
     * 设置下拉刷新监听
     */
    fun setOnRefreshListener(listener: (() -> Unit)?) {
        onRefreshListener = listener
    }

    /**
     * 设置加载更多监听
     */
    fun setOnLoadMoreListener(listener: () -> Unit) {
        onLoadMoreListener = listener
    }

    /**
     * 获取开始加载的起始页，不一定是从0开始，默认从1开始
     */
    fun getStartPage(): Int = startPage

    fun setStartPage(startPage: Int) {
        this.startPage = startPage
    }

    fun <VH : RecyclerView.ViewHolder> setAdapter(mAdapter: RecyclerView.Adapter<VH>?) {
        mRecyclerView?.adapter = mAdapter
    }

    /**
     * 生成分割线
     */
    private fun getItemDivider(): RecyclerView.ItemDecoration? = mItemDivider

    /**
     * 子类生成布局管理器
     */
    private fun getLayoutManager(): RecyclerView.LayoutManager = mLayoutManager

    /**
     * 结束刷新
     */
    fun hideRefreshing(hasMore: Boolean = true) {
        smartRefreshLayout?.finishRefresh()
        if (hasMore) {
            smartRefreshLayout?.finishLoadMore()
        } else {
            smartRefreshLayout?.finishLoadMoreWithNoMoreData()
        }
    }

    /**
     * 获取下拉刷新的头部
     */
    private fun getRefreshHeader(): RefreshHeader? {
        if (mRefreshHeader == null) {
            return MaterialHeader(context)
        }
        return mRefreshHeader
    }

    /**
     * 获取刷新底部
     */
    private fun getRefreshFooter(): RefreshFooter? {
        if (mRefreshFooter == null) {
            return ClassicsFooter(context)
        }
        return mRefreshFooter
    }

    private fun getRecyclerView(): RecyclerView? {
        return mRecyclerView
    }

    fun setItemDivider(mItemDivider: RecyclerView.ItemDecoration?) {
        this.mItemDivider = mItemDivider
    }

    fun setLayoutManager(mLayoutManager: RecyclerView.LayoutManager) {
        this.mLayoutManager = mLayoutManager
    }

    fun setRefreshHeader(mRefreshHeader: RefreshHeader?) {
        this.mRefreshHeader = mRefreshHeader
    }

    fun setRefreshFooter(mRefreshFooter: RefreshFooter?) {
        this.mRefreshFooter = mRefreshFooter
    }

    fun setIsEnableAutoLoadMore(isEnableAutoLoadMore: Boolean) {
        this.isEnableAutoLoadMore = isEnableAutoLoadMore
    }

    fun setIsEnableLoadMore(isEnableLoadMore: Boolean) {
        this.isEnableLoadMore = isEnableLoadMore
    }

    fun setIsEnableRefresh(isEnableRefresh: Boolean) {
        this.isEnableRefresh = isEnableRefresh
    }

    fun setIsEnablePureScrollMode(isEnablePureScrollMode: Boolean) {
        this.isEnablePureScrollMode = isEnablePureScrollMode
    }

    fun setIsEnableOverScrollBounce(isEnableOverScrollBounce: Boolean) {
        this.isEnableOverScrollBounce = isEnableOverScrollBounce
    }

    fun setIsShowShadowView(isShowShadowView: Boolean) {
        this.isShowShadowView = isShowShadowView
    }
}