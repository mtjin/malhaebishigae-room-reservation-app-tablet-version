package com.mtjin.tabletworks.top

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSource
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSourceImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class TopRemoteDataSourceImplTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    // context
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var remote: TopRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Firebase.initialize(appContext)
        remote = TopRemoteDataSourceImpl(database = Firebase.database.reference)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestMeetingRoomInfo() {
        remote.requestMeetingRoomInfo().test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertNoErrors()
            }
    }
}