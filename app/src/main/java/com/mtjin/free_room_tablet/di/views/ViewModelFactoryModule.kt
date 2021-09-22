package com.mtjin.free_room_tablet.di.views

import androidx.lifecycle.ViewModelProvider
import com.mtjin.free_room_tablet.di.views.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}
