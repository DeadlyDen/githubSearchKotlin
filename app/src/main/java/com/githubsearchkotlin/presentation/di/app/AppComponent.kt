package com.githubsearchkotlin.presentation.di.app

import com.githubsearchkotlin.App
import com.githubsearchkotlin.presentation.di.login.module.LoginBindModule
import com.githubsearchkotlin.presentation.di.login.module.LoginModule
import com.githubsearchkotlin.presentation.di.main.module.MainBindModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    DataModule::class,
    LoginBindModule::class,
    MainBindModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}