package com.allen.app.data.network

import com.allen.app.data.bean.Info
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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
    @GET("article/newlist?")
    fun getNewList(@Query("new") page: Int): Call<Info>
}