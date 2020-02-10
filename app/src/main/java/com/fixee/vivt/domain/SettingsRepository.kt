package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.Status
import kotlinx.coroutines.Deferred

interface SettingsRepository {

    suspend fun updatePushChange(field: Int, value: Boolean): Deferred<Status>

}