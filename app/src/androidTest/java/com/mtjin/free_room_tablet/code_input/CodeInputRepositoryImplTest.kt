package com.mtjin.free_room_tablet.code_input

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepository
import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepositoryImpl
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSource
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSource
import io.reactivex.Completable
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class CodeInputRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var local: CodeInputLocalDataSource

    @Mock
    lateinit var remote: CodeInputRemoteDataSource

    lateinit var repository: CodeInputRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(local.businessCodeInput).thenReturn("test")
        `when`(local.meetingRoomCodeInput).thenReturn("test")
        `when`(remote.requestEnter("1", 1)).thenReturn(Completable.complete())
        repository =
            CodeInputRepositoryImpl(
                remote,
                local
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestEnter() {
        repository.requestEnter("1", 1).test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertComplete()
                Assert.assertEquals(local.businessCodeInput, "test")
                Assert.assertEquals(local.meetingRoomCodeInput, "test")
                it.assertTerminated()
            }
    }

    @Test
    fun getBusinessCodeInput() {
        Assert.assertEquals(local.businessCodeInput, "test")
    }

    @Test
    fun getMeetingRoomCodeInput() {
        Assert.assertEquals(local.meetingRoomCodeInput, "test")
    }
}