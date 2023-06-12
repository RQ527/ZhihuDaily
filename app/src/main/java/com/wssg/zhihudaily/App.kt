package com.wssg.zhihudaily

import android.app.Application

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/1
 * @Description:
 */
class App : Application() {
    companion object {
        lateinit var mContext: App
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}