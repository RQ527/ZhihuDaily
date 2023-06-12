package com.wssg.zhihudaily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wssg.zhihudaily.source.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/1
 * @Description:
 */
open class BaseViewModel : ViewModel() {
    fun <T> launch(uiState: MutableStateFlow<Resource<T>>, block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                uiState.update { Resource.Error("网络错误~") }
            }
        }
    }
}