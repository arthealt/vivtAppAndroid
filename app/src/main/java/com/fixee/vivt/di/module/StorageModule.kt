package com.fixee.vivt.di.module

import android.content.Context
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class StorageModule {

    @Provides
    @PerApplication
    fun provideRoomAppDatabase(context: Context): RoomAppDatabase {
        return RoomAppDatabase.buildDataSource(context)
    }

}