package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.Notifications
import com.fixee.vivt.data.remote.models.ResponseBrs
import com.fixee.vivt.data.remote.models.Status
import com.fixee.vivt.data.remote.models.Teachers

interface MainRepository {

    suspend fun getBrs(semester: Int): ResponseBrs

    suspend fun getTeachers(): Teachers

    suspend fun getNotifications(): Notifications

    suspend fun updatePushChange(field: Int, value: Boolean): Status

    suspend fun logout()

}