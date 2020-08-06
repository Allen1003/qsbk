package com.allen.base.base.activity

import androidx.lifecycle.ViewModelProvider
import com.allen.base.base.basic.model.BaseViewModel
import com.allen.base.utils.ClassUtil

/**
 * author : Allen
 * date   : 2020/08/04
 * time   : 14:50
 * desc   :
 */
open class BaseVmActivity<VM : BaseViewModel> : BaseNavActivity() {

    protected var mViewModel: VM? = null

    override fun initViews() {
        initViewModel()
        super.initViews()
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtil.getViewModel<VM>(this)
        if (viewModelClass != null) {
            mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
                .create(viewModelClass)
        }
    }
}