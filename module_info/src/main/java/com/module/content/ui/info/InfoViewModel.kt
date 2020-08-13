package com.module.content.ui.info

import android.app.Application
import androidx.lifecycle.LiveData
import com.allen.app.data.bean.InfoBean
import com.allen.app.data.network.API
import com.allen.app.data.repository.InfoRepository
import com.allen.base.base.basic.model.ListViewModel

/**
 * author : Allen
 * date   : 2020/08/13
 * time   : 17:07
 * desc   :
 */
class InfoViewModel(application: Application) : ListViewModel<InfoBean>(application) {

    override fun loadData(): LiveData<List<InfoBean>> = emit {
        InfoRepository.getInstance(API.getInstance()).getNewList().items
    }
}