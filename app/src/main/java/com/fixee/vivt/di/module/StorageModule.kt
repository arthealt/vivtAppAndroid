package com.fixee.vivt.di.module

import android.content.Context
import androidx.preference.PreferenceManager
import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.data.storage.entity.User
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
    fun provideUser(roomAppDatabase: RoomAppDatabase): User =
        if (roomAppDatabase.userDao().getUserExist() > 0) roomAppDatabase.userDao().getUser() else User("","","","")

    @Provides
    @Named("fcmToken")
    fun provideFcmToken(context: Context): String = PreferenceManager.getDefaultSharedPreferences(context).getString("fcmToken", "")!!

}