package com.fixee.vivt.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fixee.vivt.data.storage.entity.Token

@Dao
interface TokenDao {

    @Query("SELECT COUNT(*) FROM token")
    fun getTokenExist(): Int

    @Query("SELECT * FROM token")
    fun getToken(): Token

    @Insert(onConflict = REPLACE)
    fun pushToken(token: Token)

    @Delete
    fun deleteToken(token: Token)

}