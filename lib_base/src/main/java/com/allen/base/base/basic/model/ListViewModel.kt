package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.allen.base.base.basic.extras.plus
import com.allen.base.base.basic.extras.post

abstract class ListViewModel<K>(application: Application) : BaseViewModel<K>(application) {
    private val pagerNumber = MutableLiveData<Int>()
    val mListData = MutableLiveData<List<String>>()

    fun loadMore() {
        pagerNumber plus 1
    }

    fun loadRefresh() {
        pagerNumber post 0
    }
}