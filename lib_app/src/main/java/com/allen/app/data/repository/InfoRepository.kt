package com.allen.app.data.repository

import com.allen.app.data.network.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author : Allen
 * date   : 2020/08/13
 * time   : 17:01
 * desc   :
 */
class InfoRepository(private val api: API) {

    suspend fun getNewList(page:Int) = getApiNewList(page)

    private suspend fun getApiNewList(page:Int) = withContext(Dispatchers.IO) {
        val response = api.getNewList(page)
        response
    }

    companion object {

        private var repository: InfoRepository? = null

        fun getInstance(api: API): InfoRepository {
            if (repository == null) {
                synchronized(InfoRepository::class.java) {
                    if (repository == null) {
                        repository = InfoRepository(api)
                    }
                }
            }
            return repository!!
        }
    }
}