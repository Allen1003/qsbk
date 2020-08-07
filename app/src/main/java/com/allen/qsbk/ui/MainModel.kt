package com.allen.qsbk.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.allen.base.base.basic.model.ListViewModel
import com.allen.base.utils.AppUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * author : Allen
 * date   : 2020/08/06
 * time   : 10:31
 * desc   :
 */
class MainModel(application: Application) : ListViewModel(application) {

    override fun loadData() {
        AppUtils.postDelayed(Runnable {
            val length = Random().nextInt(30)
            val list = ArrayList<String>()
            for (i in 0..length) {
                list.add("测试列表==>$i")
            }
            mListData.value = list
        }, 200)
    }
}