package com.fixee.vivt.data.remote

import com.fixee.vivt.data.remote.models.ResponseBrs
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("get-brs")
    suspend fun getBrs(@Field("token") token: String, @Field("semester") semester: Int): ResponseBrs

}