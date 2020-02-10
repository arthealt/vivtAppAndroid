package com.fixee.vivt.domain.implementations

import com.fixee.vivt.data.remote.ApiService
import com.fixee.vivt.data.remote.models.ResponseBrs
import com.fixee.vivt.data.remote.models.Teachers
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.User
import com.fixee.vivt.domain.MainRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(@param:Named("fcmToken") private val fcmToken: String, private val roomAppDatabase: RoomAppDatabase, private var user: User, private val apiService: ApiService): MainRepository {

    override suspend fun getBrs(semester: Int): ResponseBrs {
        var responseBrs = GlobalScope.async {
            apiService.getBrs(user.token, semester)
        }.await()

        if (responseBrs.error.isNotEmpty() && responseBrs.error[0].code == 300) {

            if (loginWithToken()) {
                responseBrs = GlobalScope.async {
                    apiService.getBrs(user.token, semester)
                }.await()
            }
        }

        return responseBrs
    }

    override suspend fun getTeachers(): Teachers {
        var teachers = GlobalScope.async {
            apiService.getTeachers(user.token)
        }.await()

        if (teachers.error.isNotEmpty() && teachers.error[0].code == 300) {

            if (loginWithToken()) {
                teachers = GlobalScope.async {
                    apiService.getTeachers(user.token)
                }.await()
            }
        }

        return teachers
    }

    override suspend fun logout() {
        GlobalScope.launch {
            apiService.logoutServer(user.token)
            deleteUser()
        }
    }

    private suspend fun loginWithToken(): Boolean {
        val auth = GlobalScope.async {
            apiService.loginWithToken(user.loginToken, fcmToken, "android")
        }.await()

        return if (auth.status == "success") {
            user = User(auth.token, auth.token_for_login, auth.userStatus, auth.qr)
            pushUser(user)
            true
        } else {
            false
        }
    }

    private fun pushUser(user: User) {
        GlobalScope.launch {
            roomAppDatabase.userDao().pushUser(User(user.token, user.loginToken, user.userStatus, user.qr))
        }
    }

    private fun deleteUser() {
        GlobalScope.launch {
            roomAppDatabase.userDao().deleteUser(user)
        }
    }

}