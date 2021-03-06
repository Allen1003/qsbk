package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.*
import com.allen.base.data.Common
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : Common<T>>(application: Application) : BaseModel(application) {

    abstract fun loadData(): LiveData<T>
}