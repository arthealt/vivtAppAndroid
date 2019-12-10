package com.fixee.vivt.di.component

import com.fixee.vivt.application.activity.MainActivity
import com.fixee.vivt.application.fragments.BrsFragment
import com.fixee.vivt.di.module.MainModule
import com.fixee.vivt.di.scope.PerMainActivity
import dagger.Subcomponent

@PerMainActivity
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(brsFragment: BrsFragment)

}