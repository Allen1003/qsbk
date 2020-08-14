package com.allen.base.base.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.allen.base.base.basic.model.*
import com.allen.base.utils.AppUtils
import com.allen.base.utils.ClassUtil

/**
 * author : Allen
 * date   : 2020/08/06
 * time   : 10:13
 * desc   :
 */
abstract class BaseMvFragment<VM : BaseModel> : BaseFragment() {

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
            mViewModel = ViewModelProvider.AndroidViewModelFactory(AppUtils.getApplication())
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