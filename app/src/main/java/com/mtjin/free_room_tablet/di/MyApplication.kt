package com.mtjin.free_room_tablet.di

import android.app.Application
import com.mtjin.tabletworks.di.AppComponent
import com.mtjin.tabletworks.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        //initializeComponent()
        DaggerAppComponent.factory().create(this)
    }
}