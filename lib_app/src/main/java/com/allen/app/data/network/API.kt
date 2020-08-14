package com.allen.app.data.network

import retrofit2.await

/**
 * author : Allen
 * date   : 2020/08/11
 * time   : 20:00
 * desc   :
 */
class API {

    private val api = ServiceCreator.create(APIService::class.java)

    suspend fun getNewList(page:Int) = api.getNewList(page).await()

    companion object {

        private var api: API? = null
        fun getInstance(): API {
            if (api == null) {
                synchronized(API::class.java) {
                    if (api == null) {
                        api = API()
                    }
                }
            }
            return api!!
        }
    }
}