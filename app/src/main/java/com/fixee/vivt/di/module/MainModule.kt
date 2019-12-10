package com.fixee.vivt.di.module

import com.fixee.vivt.data.remote.ApiService
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.di.scope.PerMainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Provides
    @PerMainActivity
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @PerMainActivity
    fun provideToken(roomAppDatabase: RoomAppDatabase): Token {
        return if (roomAppDatabase.tokenDao().getTokenExist() > 0) roomAppDatabase.tokenDao().getToken() else Token ("", "")
    }

}