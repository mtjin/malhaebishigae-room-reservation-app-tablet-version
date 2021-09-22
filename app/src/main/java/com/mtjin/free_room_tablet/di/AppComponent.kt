package com.mtjin.tabletworks.di

import android.content.Context
import com.mtjin.free_room_tablet.di.AppSubComponentsModule
import com.mtjin.free_room_tablet.di.data.ApiModule
import com.mtjin.free_room_tablet.di.data.LocalDataModule
import com.mtjin.free_room_tablet.di.data.RemoteDataModule
import com.mtjin.free_room_tablet.di.data.RepositoryModule
import com.mtjin.free_room_tablet.di.views.ViewModelFactoryModule
import com.mtjin.free_room_tablet.di.views.ViewModelModule
import com.mtjin.free_room_tablet.views.code_input.CodeInputComponent
import com.mtjin.free_room_tablet.views.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class, ViewModelFactoryModule::class, AppSubComponentsModule::class,
        RepositoryModule::class, RemoteDataModule::class, LocalDataModule::class, ApiModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun codeInputComponent(): CodeInputComponent.Factory

}