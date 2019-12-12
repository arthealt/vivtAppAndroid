package com.fixee.vivt.di.module

import android.content.Context
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.Token
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

    @Provides
    fun provideToken(roomAppDatabase: RoomAppDatabase): Token {
        return if (roomAppDatabase.tokenDao().getTokenExist() > 0) roomAppDatabase.tokenDao().getToken() else Token ("", "")
    }

}