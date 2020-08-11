package com.allen.base.base.activity

import androidx.recyclerview.widget.RecyclerView
import com.allen.base.R
import com.allen.base.base.basic.model.ListViewModel
import com.allen.base.utils.LayoutManagerUtil
import com.allen.base.widget.IRecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import kotlinx.android.synthetic.main.base_recyler.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 列表基类
 */
abstract class BaseRecyclerActivity<VM : ListViewModel, T> : BaseVmActivity<VM>() {

    //当前适配器
    var mAdapter: BaseQuickAdapter<T, BaseViewHolder>? = null

    //数据源
    var mData = CopyOnWriteArrayList<T>()

    //布局
    override fun getContentView() = R.layout.base_recyler

    //取得适配器
    abstract fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>


    final override fun initViews() {
        super.initViews()
        //是否添加头部
        val headView = addHeadView()
        if (headView != null) {
            headLayout.addView(layoutInflater.inflate(headView, null))
        }
        //获取适配器
        mAdapter = getAdapter()
        getRecyclerView()?.setAdapter(mAdapter)
        getRecyclerView()?.setOnLoadMoreListener { onLoadMoreListener() }
        getRecyclerView()?.setOnRefreshListener { onRefreshListener() }
        setErrorRetryListener { mViewModel?.loadData() }
        initRecyclerView()
        initView()
    }

    //初始化列表控件
    private fun initRecyclerView() {
        getRecyclerView()?.setItemDivider(getItemDivider())
        getRecyclerView()?.setLayoutManager(getLayoutManager())
        getRecyclerView()?.setRefreshHeader(getRefreshHeader())
        getRecyclerView()?.setRefreshFooter(getRefreshFooter())
        getRecyclerView()?.setIsEnableAutoLoadMore(isEnableAutoLoadMore())
        getRecyclerView()?.setIsEnableLoadMore(isEnableLoadMore())
        getRecyclerView()?.setIsEnableRefresh(isEnableRefresh())
        getRecyclerView()?.setIsEnablePureScrollMode(isEnablePureScrollMode())
        getRecyclerView()?.setIsEnableOverScrollBounce(isEnableOverScrollBounce())
        getRecyclerView()?.setIsShowShadowView(isShowShadowView())
        getRecyclerView()?.initViewParams()
    }

    open fun showData(data: List<T>) {
        //如果是第一页则清除数据、翻页则不清除自动累计
        if (getRecyclerView()?.getPage() == getRecyclerView()?.getStartPage()) {
            mData.clear()
        }
        mData.addAll(data)
        mAdapter?.setList(mData)
        //如果数据为空则显示空白占位图
        getRecyclerView()?.hideRefreshing()
        if (mData.isEmpty()) {
            showEmpty()
        } else {
            showContent()
        }
    }

    //获取当前列表数据
    protected fun getData(): List<T> {
        return mData
    }

    //添加头部
    open fun addHeadView(): Int? {
        return null
    }

    //取得列表对象
    private fun getRecyclerView(): IRecyclerView? {
        return mtRecyclerView
    }

    open fun initView() {}

    //加载更多
    open fun onLoadMoreListener() {
        mViewModel?.loadMore()
        getRecyclerView()?.hideRefreshing(false)
    }

    //下拉刷新
    open fun onRefreshListener() {
        mViewModel?.loadRefresh()
        getRecyclerView()?.hideRefreshing()
    }

    //显示页面空白
    override fun showEmpty(msg: String?) {
        if (getRecyclerView()?.getPage() == getRecyclerView()?.getStartPage()) {
            super.showEmpty(msg)
        } else {
            // 如果不是第一页，并且没有数据了，就不结束加载更多
            getRecyclerView()?.hideRefreshing(false)
        }
    }

    /**
     * 生成分割线
     */
    open fun getItemDivider(): RecyclerView.ItemDecoration? {
        return null
    }

    /**
     * 子类生成布局管理器
     */
    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getVerticalLinearLayoutManager(this)
    }

    //头部刷新控件
    protected open fun getRefreshHeader(): RefreshHeader? {
        return null
    }

    //尾部刷新控件
    open fun getRefreshFooter(): RefreshFooter? {
        return null
    }

    //是否可以自动加载更多，默认可以
    open fun isEnableAutoLoadMore(): Boolean = true

    //是否可以加载更多，默认可以
    open fun isEnableLoadMore(): Boolean = true

    //是否可以刷新，默认可以
    open fun isEnableRefresh(): Boolean = true

    //是否是纯净模式，不展示刷新头和底部，默认false
    open fun isEnablePureScrollMode(): Boolean = false

    //刷新时是否可以越界回弹
    open fun isEnableOverScrollBounce(): Boolean = false

    //是否显示列表顶部阴影
    open fun isShowShadowView(): Boolean = true
}