package com.allen.qsbk.ui

import androidx.lifecycle.Observer
import com.allen.base.base.activity.BaseRecyclerActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MainActivity : BaseRecyclerActivity<MainModel, String>() {

    override fun isShowBackIcon() = false

    override fun getAdapter(): BaseQuickAdapter<String, BaseViewHolder> =MainAdapter()

    override fun initView() {
        showLoading()
        //数据变化
        mViewModel?.mListData?.observe(this, Observer {
            showData(it)
        })
    }


    override fun initData() {
        super.initData()
        loadData()
    }

    override fun onRefreshListener() {
        loadData()
    }

    private fun loadData() {
        mViewModel?.loadData()
    }
}