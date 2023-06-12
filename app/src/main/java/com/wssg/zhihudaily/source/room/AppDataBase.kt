package com.wssg.zhihudaily.source.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wssg.zhihudaily.App

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/4/11
 * @Description:
 */
@Database(entities = [RoomBean::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        val INSTANCE by lazy {
            Room.databaseBuilder(App.mContext, AppDataBase::class.java, "baishouzahng.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun dao(): RoomDao
}