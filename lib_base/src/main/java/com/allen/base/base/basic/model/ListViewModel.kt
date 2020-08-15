package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.allen.base.base.basic.extras.plus
import com.allen.base.base.basic.extras.post
import com.allen.base.data.ListCommon

abstract class ListViewModel<T : ListCommon<K>, K>(application: Application) : BaseListViewModel<T, K>(application) {
    val pagerNumber = MutableLiveData<Int>()

    fun loadMore() {
        pagerNumber plus 1
    }

    fun loadRefresh() {
        pagerNumber post 0
    }
}