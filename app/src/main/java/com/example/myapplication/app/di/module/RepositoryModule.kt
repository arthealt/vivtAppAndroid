package com.example.myapplication.app.di.module

import com.example.myapplication.app.data.remote.AuthService
import com.example.myapplication.app.data.storage.RoomAppDatabase
import com.example.myapplication.app.di.scope.PerApplication
import com.example.myapplication.app.domain.TestRepository
import com.example.myapplication.app.domain.implementations.TestRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [StorageModule::class, NetworkModule::class])
class RepositoryModule {

    @Provides
    @PerApplication
    fun provideTestRepositoryImpl(roomAppDatabase: RoomAppDatabase, authService: AuthService): TestRepository {
        return TestRepositoryImpl(roomAppDatabase, authService)
    }

}