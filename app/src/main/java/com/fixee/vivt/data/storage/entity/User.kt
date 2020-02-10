package com.fixee.vivt.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (@PrimaryKey val token: String, val loginToken: String, val userStatus: String, val qr: String)