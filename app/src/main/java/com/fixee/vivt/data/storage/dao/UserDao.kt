package com.fixee.vivt.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fixee.vivt.data.storage.entity.User

@Dao
interface UserDao {

    @Query("SELECT COUNT(*) FROM user")
    fun getUserExist(): Int

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Insert(onConflict = REPLACE)
    fun pushUser(user: User)

    @Delete
    fun deleteUser(user: User)

}