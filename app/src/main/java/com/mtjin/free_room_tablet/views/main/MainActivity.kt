package com.mtjin.free_room_tablet.views.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseActivity
import com.mtjin.free_room_tablet.databinding.ActivityMainBinding
import com.mtjin.free_room_tablet.di.MyApplication
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ReservationSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        initSharedViewModel()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initSharedViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ReservationSharedViewModel::class.java)
    }
}