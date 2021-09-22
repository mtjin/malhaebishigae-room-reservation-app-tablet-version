package com.mtjin.free_room_tablet.reservation

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mtjin.free_room_tablet.api.ApiInterface
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSourceImpl
import com.mtjin.free_room_tablet.fcm.NotificationBody
import com.mtjin.free_room_tablet.fcm.NotificationData
import com.mtjin.free_room_tablet.utils.convertDateTimestamp
import io.reactivex.Single
import junit.framework.Assert.fail
import okhttp3.ResponseBody
import org.junit.After
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
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ReservationRemoteDataSourceImplTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    // context
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var remote: ReservationRemoteDataSource

    @Mock
    lateinit var fcmInterface: ApiInterface

    @Mock
    lateinit var responseBody: ResponseBody

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Firebase.initialize(appContext)
        remote =
            ReservationRemoteDataSourceImpl(
                database = Firebase.database.reference,
                fcmInterface = fcmInterface
            )

        Mockito.`when`(
            fcmInterface.sendNotification(
                notification = NotificationBody(
                    to = "test",
                    data = NotificationData("test", "test", "test")
                )
            )
        ).thenReturn(Single.just(responseBody))
    }

    @After
    fun tearDown() {
    }

    @Test //오늘 날짜에 해당하는 시간만 가져와야합니다.
    fun requestAvailableTimeShouldToady() {
        val todayDateTimestamp = System.currentTimeMillis().convertDateTimestamp()
        remote.requestAvailableTime(todayDateTimestamp).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertNoErrors()
                it.assertNotComplete()
                for (reservation in it.values()[0]) {
                    if (reservation.dateTimestamp != todayDateTimestamp) {
                        fail("오늘 회의실 시간표는 오늘날짜에 해당하는 것만 불러와져야합니다.")
                    }
                }
            }
    }
}