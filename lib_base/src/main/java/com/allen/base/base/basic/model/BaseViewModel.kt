package com.allen.base.base.basic.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    open fun loadData(){

    }
}