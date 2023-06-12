package com.wssg.zhihudaily.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.wssg.zhihudaily.Repository
import com.wssg.zhihudaily.source.Resource
import com.wssg.zhihudaily.source.data.HomeData
import com.wssg.zhihudaily.source.data.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/1
 * @Description:
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val topArticle: List<HomeData.TopStory> = emptyList(),
    val date: String = ""
)

class HomeViewModel : BaseViewModel() {
    private val netFlow: MutableStateFlow<Resource<HomeData>> =
        MutableStateFlow(Resource.Loading)

    val uiState: StateFlow<HomeUiState> = netFlow
        .map { processNetFlow(it) }
        .catch { emit(HomeUiState(isLoading = false, isError = true, errorMessage = "出错了！")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(), HomeUiState(isLoading = true)
        )

    init {
        getHomeData()
    }

    private fun getHomeData() {
        launch(netFlow) {
            val data = Repository.getHomeData()
            netFlow.update { Resource.Success(HomeData(data.date, data.stories, data.top_stories)) }
        }
    }

    fun getHomePagingData(): Flow<PagingData<Story>> {
        return Repository.getHomePagingData()
    }

    private fun processNetFlow(resource: Resource<HomeData>): HomeUiState {
        return when (resource) {

            Resource.Loading -> {
                HomeUiState(isLoading = true)
            }

            is Resource.Success -> {
                HomeUiState(
                    isLoading = false,
                    isError = false,
                    topArticle = resource.data.top_stories,
                    date = resource.data.date
                )
            }

            is Resource.Error -> {
                HomeUiState(isLoading = false, isError = true, errorMessage = resource.message)
            }

        }
    }
}