package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.*
import com.allen.base.data.Common
import com.allen.base.data.ListCommon
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


abstract class BaseListViewModel<T : ListCommon<K>, K>(application: Application) : BaseModel(application) {

    abstract fun loadData(page: Int): LiveData<T>
}