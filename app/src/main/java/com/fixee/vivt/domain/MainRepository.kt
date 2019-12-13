package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.ResponseBrs
import kotlinx.coroutines.Deferred

interface MainRepository {

    suspend fun getBrs(token: String, semester: Int): Deferred<ResponseBrs>

    suspend fun logout(token: String)

}