package com.example.myapplication.app.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class People (@PrimaryKey(autoGenerate = true) val id: Int?, val name: String, val role: String)