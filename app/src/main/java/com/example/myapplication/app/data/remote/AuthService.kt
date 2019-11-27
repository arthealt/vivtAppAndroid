package com.example.myapplication.app.data.remote

import com.example.myapplication.app.data.remote.models.Auth
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("login")
    suspend fun authAsync(@Field("email") email: String, @Field("password_mobile") password: String): Auth

}