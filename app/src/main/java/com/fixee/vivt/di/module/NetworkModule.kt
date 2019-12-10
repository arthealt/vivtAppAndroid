package com.fixee.vivt.di.module

import com.fixee.vivt.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

    @Provides
    @PerApplication
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://vivt.ru/mobile/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}