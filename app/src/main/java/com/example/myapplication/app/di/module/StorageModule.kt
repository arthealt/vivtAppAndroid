package com.example.myapplication.app.di.module

import android.content.Context
import com.example.myapplication.app.data.storage.RoomAppDatabase
import com.example.myapplication.app.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class StorageModule {

    @Provides
    @PerApplication
    fun provideRoomAppDatabase(context: Context): RoomAppDatabase {
        return RoomAppDatabase.buildDataSource(context)
    }

}