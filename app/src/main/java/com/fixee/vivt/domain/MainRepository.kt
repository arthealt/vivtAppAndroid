package com.fixee.vivt.domain

import com.fixee.vivt.data.remote.models.ResponseBrs
import com.fixee.vivt.data.remote.models.Teachers

interface MainRepository {

    suspend fun getBrs(semester: Int): ResponseBrs

    suspend fun getTeachers(): Teachers

    suspend fun logout()

}