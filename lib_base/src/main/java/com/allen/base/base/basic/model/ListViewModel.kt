package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.allen.base.base.basic.extras.plus
import com.allen.base.base.basic.extras.post

open class ListViewModel(application: Application) : BaseViewModel(application) {
    private val pagerNumber = MutableLiveData<Int>()

    fun loadMore() {
        pagerNumber plus 1
    }

    fun loadRefresh() {
        pagerNumber post 0
    }
}