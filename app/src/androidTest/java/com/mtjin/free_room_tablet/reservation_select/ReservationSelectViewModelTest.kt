package com.mtjin.free_room_tablet.reservation_select

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.free_room_tablet.views.reservation_select.ReservationSelectViewModel
import com.mtjin.free_room_tablet.TAG
import com.mtjin.free_room_tablet.observeForTesting
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class ReservationSelectViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    lateinit var viewModel: ReservationSelectViewModel

    @Before
    fun setUp() {
        viewModel =
            ReservationSelectViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getGoQrCodeReservation() {
        viewModel.goLoginReservation()
        viewModel.goLoginReservation.observeForTesting {
            Log.d(TAG, "옵저빙됨")
        }
    }
}