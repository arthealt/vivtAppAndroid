package com.fixee.vivt.di.module

import android.content.Context
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.di.App
import com.fixee.vivt.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    @PerApplication
    fun provideContext(): Context {
        return app
    }

    @Provides
    @PerApplication
    fun provideUtil(context: Context): Util {
        return Util(context)
    }

}