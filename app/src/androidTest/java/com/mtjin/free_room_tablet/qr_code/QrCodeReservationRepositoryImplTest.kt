package com.mtjin.free_room_tablet.qr_code

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepository
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepositoryImpl
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSource
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class QrCodeReservationRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var remote: QrCodeReservationRemoteDataSource

    lateinit var repository: QrCodeReservationRepository

    @Before
    fun setUp() {
        `when`(remote.requestQrCodeValidate("test")).thenReturn(Completable.complete())
        repository =
            QrCodeReservationRepositoryImpl(
                remote
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestQrCodeValidate() {
        repository.requestQrCodeValidate("test").test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertComplete()
                it.onComplete()
            }
    }
}