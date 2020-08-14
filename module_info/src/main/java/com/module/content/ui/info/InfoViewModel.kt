package com.module.content.ui.info

import android.app.Application
import androidx.lifecycle.LiveData
import com.allen.app.data.bean.Info
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
class InfoViewModel(application: Application) : ListViewModel<Info, InfoBean>(application) {
    override fun loadData(page:Int): LiveData<Info> = emit {
        InfoRepository.getInstance(API.getInstance()).getNewList(page)
    }

}