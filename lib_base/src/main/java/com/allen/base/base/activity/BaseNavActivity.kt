package com.allen.base.base.activity

import android.view.View
import com.allen.base.databinding.BaseActivityNavBinding
import com.allen.base.widget.PlaceholderView
import kotlinx.android.synthetic.main.base_layout_toolbar.*

/**
 * author : Allen
 * date   : 2020/08/03
 * time   : 20:07
 * desc   : 带标题的基类
 */
open class BaseNavActivity : BaseActivity() {

    private val mBinding by lazy { BaseActivityNavBinding.inflate(layoutInflater) }

    override fun getPlaceholderView(): PlaceholderView? = mBinding.mPlaceholderView
    override fun getLayoutView(): View = mBinding.root

    override fun initViews() {
        super.initViews()
        // 设置是否展示标题栏
        setShowToolBar(isShowToolBar())
        mDelegate.setBackIcon(toolbar, isShowBackIcon()) { onBackClick() }
        // 设置填充容器
        if (mBinding.navLayout.childCount > 0) {
            mBinding.navLayout.removeAllViews()
        }
        getContentView()?.let { mBinding.navLayout.addView(it) }
    }

    /**
     * 执行返回操作
     */
    protected open fun onBackClick() {
        finish()
    }

    /**
     * 设置ToolBar的展示状态
     * @param isShow 是否展示
     */
    private fun setShowToolBar(isShow: Boolean) {
        setVisibility(mBinding.appBarLayout, isShow)
        setVisibility(mBinding.statusView, isShow)
    }

    /**
     * 子类继续填充内容容器布局
     */
    protected open fun getContentView(): View? = null

    /**
     * 是否展示ToolBar，如果设置为false则不展示。
     * 如果不展示标题栏，则状态栏也不会展示。
     */
    protected open fun isShowToolBar(): Boolean = true

    /**
     * 是否可以返回，如果可以则展示返回按钮，并且设置返回事件
     * 默认可以返回
     */
    protected open fun isShowBackIcon(): Boolean = true
}