package com.fixee.vivt.di.component

import com.fixee.vivt.application.activity.LoginActivity
import com.fixee.vivt.di.module.LoginModule
import com.fixee.vivt.di.scope.PerLoginActivity
import dagger.Subcomponent

@PerLoginActivity
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)

}