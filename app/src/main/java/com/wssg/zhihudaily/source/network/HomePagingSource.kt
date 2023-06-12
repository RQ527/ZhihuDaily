package com.wssg.zhihudaily.source.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.zhihudaily.source.data.Story
import java.lang.Exception

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/6
 * @Description:
 */
class HomePagingSource : PagingSource<Int, Story>() {
    private var nextDate = ""

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return return try {
            val page = params.key ?: 1
            val homeData =
                if (nextDate == "") ApiService::class.api.getBannerLatestData() else ApiService::class.api.getBeforeData(
                    nextDate
                )

            nextDate = homeData.date

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (nextDate != "") page + 1 else null
            LoadResult.Page(homeData.stories, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}