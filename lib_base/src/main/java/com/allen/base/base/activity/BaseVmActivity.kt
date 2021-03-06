package com.allen.base.base.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.allen.base.base.basic.model.*
import com.allen.base.utils.ClassUtil

/**
 * author : Allen
 * date   : 2020/08/04
 * time   : 14:50
 * desc   :
 */
open class BaseVmActivity<VM : BaseModel> : BaseNavActivity() {

    protected var mViewModel: VM? = null

    override fun initViews() {
        initViewModel()
        initViewModelAction()
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

    private fun initViewModelAction() {
        mViewModel?.let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                when (stateActionState) {
                    LoadState -> showLoading()
                    SuccessState -> showContent()
                    is ErrorState -> {
                        showError()
                        stateActionState.message?.apply { showToast(this) }
                    }
                }
            })
        }
    }
}