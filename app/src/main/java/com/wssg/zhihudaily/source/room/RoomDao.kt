package com.wssg.zhihudaily.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/4/11
 * @Description:
 */
@Dao
interface RoomDao {
    @Query("SELECT * FROM RoomData")
    suspend fun getAll(): List<RoomBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(roomBean: RoomBean)
}