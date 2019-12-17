package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.ResponseBrs
import com.fixee.vivt.data.remote.models.Teachers
import kotlinx.coroutines.Deferred

interface MainRepository {

    suspend fun getBrs(token: String, semester: Int): Deferred<ResponseBrs>

    suspend fun getTeachers(token: String): Deferred<Teachers>

    suspend fun logout(token: String)

}