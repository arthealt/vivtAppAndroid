package com.example.myapplication.app.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.app.data.storage.dao.PeopleDao
import com.example.myapplication.app.data.storage.entity.People

@Database(entities = [People::class], version = 1, exportSchema = false)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun peopleDao(): PeopleDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, "PeopleTest.db"
        ).fallbackToDestructiveMigration().build()
    }
}