package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.Auth


interface LoginRepository {

    suspend fun auth(email: String, password: String, fcmToken: String): Auth

    suspend fun pushUser(token: String, loginToken: String, userStatus: String, qr: String)

}