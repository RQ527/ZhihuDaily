package com.wssg.zhihudaily.source.network

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/4/10
 * @Description:
 */
object ApiGenerator {
    private const val baseUrl = "https://news-at.zhihu.com/api/4/"
    private var retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(GsonConverterFactory.create())
        client(OkHttpClient().newBuilder().build())
    }.build()

    fun <T : Any> getApiService(clazz: KClass<T>): T = retrofit.create(clazz.java)
}

interface IApi {
    companion object {
        internal val MAP = HashMap<KClass<out IApi>, IApi>()
    }
}

/**
 * 不带token
 */
@Suppress("UNCHECKED_CAST")
val <I : IApi> KClass<I>.api: I
    get() = IApi.MAP.getOrPut(this) {
        ApiGenerator.getApiService(this)
    } as I