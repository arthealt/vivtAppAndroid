package com.example.myapplication.app.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.app.data.storage.entity.People

@Dao
interface PeopleDao {

    @Query("SELECT * FROM people")
    fun getAll(): List<People>

    @Query("SELECT * FROM people WHERE id = :id")
    fun getById(id: Int): People

    @Insert
    fun insert(people: People)

}