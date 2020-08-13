package com.allen.app.data.network

import com.allen.app.data.bean.Info
import retrofit2.Call
import retrofit2.http.GET

/**
 * author : Allen
 * date   : 2020/08/11
 * time   : 17:52
 * desc   :
 */
interface APIService {
    /**
     * 首页专享列表
     */
    @GET("article/newlist?new=1")
    fun getNewList(): Call<Info>
}