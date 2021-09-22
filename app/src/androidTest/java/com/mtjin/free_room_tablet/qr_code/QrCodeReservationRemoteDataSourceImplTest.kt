package com.mtjin.free_room_tablet.qr_code

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSourceImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class QrCodeReservationRemoteDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    lateinit var remote: QrCodeReservationRemoteDataSource

    @Before
    fun setUp() {
        Firebase.initialize(appContext)
        remote =
            QrCodeReservationRemoteDataSourceImpl(
                Firebase.database.reference
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestQrCodeValidateShouldNotCompleteAndErrorIfNoUserToken() {
        remote.requestQrCodeValidate("no token test").test().awaitDone(2000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertNotComplete()
                it.onError(Throwable("해당 유저 없음"))
            }
    }
}