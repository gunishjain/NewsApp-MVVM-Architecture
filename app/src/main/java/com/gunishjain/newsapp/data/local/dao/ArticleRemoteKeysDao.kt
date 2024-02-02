package com.gunishjain.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gunishjain.newsapp.data.local.entity.ArticleRemoteKeys

@Dao
interface ArticleRemoteKeysDao {

    @Query("SELECT * FROM ArticleRemoteKeys WHERE id=:id")
    suspend fun getRemoteKeys(id: String): ArticleRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ArticleRemoteKeys>)

    @Query("DELETE FROM ArticleRemoteKeys")
    suspend fun deleteAllRemoteKeys()

}