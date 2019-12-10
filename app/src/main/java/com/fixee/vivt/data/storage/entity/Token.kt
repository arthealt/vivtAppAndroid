package com.fixee.vivt.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Token (@PrimaryKey val token: String, val userStatus: String)