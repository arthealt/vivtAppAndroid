package com.fixee.vivt.domain.implementations

import com.fixee.vivt.data.remote.ApiService
import com.fixee.vivt.data.remote.models.ResponseBrs
import com.fixee.vivt.domain.MainRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService): MainRepository {

    override suspend fun getBrs(token: String, semester: Int): Deferred<ResponseBrs> {
        return GlobalScope.async {
            apiService.getBrs(token, semester)
        }
    }

    override suspend fun logout(token: String) {
        GlobalScope.async {
            apiService.logoutServer(token)
        }
    }

}