package com.fixee.vivt.di.module

import android.content.Context
import androidx.preference.PreferenceManager
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [AppModule::class])
class StorageModule {

    @Provides
    @PerApplication
    fun provideRoomAppDatabase(context: Context): RoomAppDatabase =
        RoomAppDatabase.buildDataSource(context)

    @Provides
    @PerApplication
    fun provideToken(roomAppDatabase: RoomAppDatabase): Token =
        if (roomAppDatabase.tokenDao().getTokenExist() > 0) roomAppDatabase.tokenDao().getToken() else Token ("", "")

    @Provides
    @PerApplication
    @Named("fcmToken")
    fun provideFcmToken(context: Context): String = PreferenceManager.getDefaultSharedPreferences(context).getString("fcm_token", "")!!

}