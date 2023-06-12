package com.wssg.zhihudaily

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.wssg.zhihudaily.source.data.HomeData
import com.wssg.zhihudaily.source.data.Story
import com.wssg.zhihudaily.source.network.ApiGenerator
import com.wssg.zhihudaily.source.network.ApiService
import com.wssg.zhihudaily.source.network.HomePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/6
 * @Description:
 */
object Repository {
    fun getHomePagingData(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { HomePagingSource() }).flow
    }

    suspend fun getHomeData(): HomeData =
        ApiGenerator.getApiService(ApiService::class).getBannerLatestData()
}