package com.fixee.vivt.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fixee.vivt.data.storage.dao.UserDao
import com.fixee.vivt.data.storage.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, "Vivt.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}