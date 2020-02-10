package com.fixee.vivt.domain.implementations

import com.fixee.vivt.data.remote.AuthService
import com.fixee.vivt.data.remote.models.Auth
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.User
import com.fixee.vivt.domain.LoginRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(private val roomAppDatabase: RoomAppDatabase, private val authService: AuthService): LoginRepository {

    override suspend fun auth(email: String, password: String, fcmToken: String): Auth {
        val auth = GlobalScope.async {
            authService.authAsync(email, password, fcmToken, "android")
        }.await()

        if (auth.status == "success") {
            pushUser(auth.token, auth.token_for_login, auth.userStatus, auth.qr)
        }

        return auth
    }

    override suspend fun pushUser(token: String, loginToken: String, userStatus: String, qr: String) {
        GlobalScope.async {
            roomAppDatabase.userDao().pushUser(User(token, loginToken, userStatus, qr))
        }.await()
    }

}