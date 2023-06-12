package com.wssg.zhihudaily.source.network

import com.wssg.zhihudaily.source.data.HomeData
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/4/10
 * @Description:
 */
interface ApiService : IApi {
    @GET("news/latest")
    suspend fun getBannerLatestData(): HomeData

    @GET("news/before/{date}")
    suspend fun getBeforeData(@Path("date") date: String): HomeData
}