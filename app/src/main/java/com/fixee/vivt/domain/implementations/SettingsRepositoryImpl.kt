package com.fixee.vivt.domain.implementations

import com.fixee.vivt.data.remote.ApiService
import com.fixee.vivt.data.remote.models.Status
import com.fixee.vivt.domain.SettingsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val apiService: ApiService): SettingsRepository {

    override suspend fun updatePushChange(fcmToken: String, field: Int, value: Boolean): Deferred<Status> {
        return GlobalScope.async {
            apiService.pushUpdate("android", fcmToken, field, value)
        }
    }

}