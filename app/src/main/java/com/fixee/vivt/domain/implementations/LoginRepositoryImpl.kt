package com.fixee.vivt.domain.implementations

import com.fixee.vivt.data.remote.AuthService
import com.fixee.vivt.data.remote.models.Auth
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.domain.LoginRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(private val roomAppDatabase: RoomAppDatabase, private val authService: AuthService): LoginRepository {

    override suspend fun auth(email: String, password: String, fcmToken: String): Deferred<Auth> {
        return GlobalScope.async {
            authService.authAsync(email, password, fcmToken, "android")
        }
    }

    override suspend fun pushToken(token: String, userStatus: String) {
        GlobalScope.async {
            roomAppDatabase.tokenDao().pushToken(Token(token, userStatus))
        }
    }
}