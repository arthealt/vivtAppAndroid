package com.fixee.vivt.di.module

import com.fixee.vivt.data.remote.AuthService
import com.fixee.vivt.di.scope.PerLoginActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoginModule {

    @Provides
    @PerLoginActivity
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

}