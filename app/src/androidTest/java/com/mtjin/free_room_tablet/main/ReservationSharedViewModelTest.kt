package com.mtjin.tabletworks.main

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.FirebaseAuth
import com.mtjin.free_room_tablet.data.reservation.source.ReservationRepository
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.utils.NetworkManager
import com.mtjin.free_room_tablet.utils.getTimestamp
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@RunWith(MockitoJUnitRunner::class)
class ReservationSharedViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: ReservationRepository

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var networkManager: NetworkManager
    private lateinit var sharedViewModel: ReservationSharedViewModel
    private val reservationList = ArrayList<Reservation>()
    private val timestamp = getTimestamp()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkManager = NetworkManager(appContext)
        Mockito.`when`(repository.requestReservation(Reservation(id = 1, register = "test")))
            .thenReturn(
                Completable.complete()
            )

        for (i in 1..3) {
            reservationList.add(
                Reservation(
                    id = i.toLong(),
                    dateTimestamp = timestamp,
                    startTimestamp = timestamp + i * 10000,
                    endTimestamp = timestamp + i * 100000
                )
            )
        }

        for (i in 4..6) {
            reservationList.add(
                Reservation(
                    id = i.toLong(),
                    dateTimestamp = timestamp,
                    startTimestamp = timestamp - i * 10000,
                    endTimestamp = timestamp - i * 100000
                )
            )
        }

        Mockito.`when`(repository.requestAvailableTime(getTimestamp()))
            .thenReturn(Flowable.just(reservationList))

        sharedViewModel =
            ReservationSharedViewModel(
                repository,
                networkManager
            )
    }

    @Test
    fun setUserToken() {
        sharedViewModel.userToken = FirebaseAuth.getInstance().uid.toString()
        Assert.assertNotNull(sharedViewModel.userToken)
        Assert.assertEquals(FirebaseAuth.getInstance().uid.toString(), sharedViewModel.userToken)
    }

    @Test
    fun checkNetwork() {
        sharedViewModel.checkReservation()
        if (networkManager.checkNetworkState()) {
            Assert.assertNotEquals(
                ReservationSharedViewModel.MessageSet.NETWORK_ERROR,
                sharedViewModel.messageSet.value
            )
        } else {
            Assert.assertEquals(
                ReservationSharedViewModel.MessageSet.NETWORK_ERROR,
                sharedViewModel.messageSet.value
            )
        }
    }

    @Test
    fun initViewModel() {
        sharedViewModel.initViewModel()
        Assert.assertEquals("", sharedViewModel.userToken)
        Assert.assertEquals("", sharedViewModel.endTime.value)
        Assert.assertEquals("", sharedViewModel.register.value)
        Assert.assertEquals("", sharedViewModel.content.value)
        Assert.assertEquals("", sharedViewModel.startTime.value)
    }
}
