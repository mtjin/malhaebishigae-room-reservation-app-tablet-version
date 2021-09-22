package com.mtjin.free_room_tablet.reservation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSource
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.utils.getTimestamp
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ReservationRepositoryImplTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var remote: ReservationRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(remote.requestReservation(Reservation(id = 1, register = "test"))).thenReturn(
            Completable.complete()
        )
        `when`(remote.requestAvailableTime(getTimestamp())).thenReturn(Flowable.just(ArrayList<Reservation>()))

    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestAvailableTime() {
        remote.requestReservation(Reservation(id = 1, register = "test")).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertNoErrors()
                it.assertComplete()
                it.assertTerminated()
            }
    }
}