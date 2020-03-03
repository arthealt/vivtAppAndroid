package com.fixee.vivt.data.remote

import com.fixee.vivt.data.remote.models.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("get-brs")
    suspend fun getBrs(@Field("token") token: String, @Field("semester") semester: Int): ResponseBrs

    @FormUrlEncoded
    @POST("get-pps-list")
    suspend fun getTeachers(@Field("token") token: String): Teachers

    @FormUrlEncoded
    @POST("get-notifications")
    suspend fun getNotifications(@Field("token") token: String): Notifications

    @FormUrlEncoded
    @POST("push-permission-change")
    suspend fun pushUpdate(@Field("type_os") typeOS: String, @Field("fcm_token") fcmToken: String, @Field("field") field: Int, @Field("value") value: Boolean): Status

    @FormUrlEncoded
    @POST("login-with-token")
    suspend fun loginWithToken(@Field("token_for_login") loginToken: String, @Field("fcm_token") fcmToken: String, @Field("type_os") typeOS: String): Auth

    @FormUrlEncoded
    @POST("logout")
    suspend fun logoutServer(@Field("app_token") token: String)

}