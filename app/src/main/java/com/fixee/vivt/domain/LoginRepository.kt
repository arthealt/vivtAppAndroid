package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.Auth
import kotlinx.coroutines.Deferred


interface LoginRepository {

    suspend fun auth(email: String, password: String, fcmToken: String): Deferred<Auth>

    suspend fun pushToken(token: String, userStatus: String)

}