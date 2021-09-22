package com.mtjin.free_room_tablet.code_input

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSource
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSourceImpl
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
class CodeInputRemoteDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var remote: CodeInputRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Firebase.initialize(appContext)
        remote =
            CodeInputRemoteDataSourceImpl(
                Firebase.database.reference
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestEnter() {
        remote.requestEnter("works_mobile", 1).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertComplete()
            }
    }

    @Test
    fun requestEnterNoMeetingRoomCodeShouldOnError() {
        remote.requestEnter("works_mobile", 100000).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.onError(Throwable("No Room Code"))
            }
    }

    @Test
    fun requestEnterNoBusinessCodeShouldOnError() {
        remote.requestEnter("error", 1).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.onError(Throwable("No Business Code"))
            }

    }
}