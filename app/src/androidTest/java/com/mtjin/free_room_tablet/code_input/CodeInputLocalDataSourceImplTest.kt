package com.mtjin.free_room_tablet.code_input

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSource
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSourceImpl
import com.mtjin.free_room_tablet.utils.PreferenceManager
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class CodeInputLocalDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    // context
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    // 로컬
    private lateinit var preferenceManager: PreferenceManager

    lateinit var local: CodeInputLocalDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        preferenceManager = PreferenceManager(context = appContext)
        local =
            CodeInputLocalDataSourceImpl(
                preferenceManager
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getBusinessCodeInputShouldNotReturnNull() {
        Assert.assertNotNull(local.businessCodeInput)
    }

    @Test
    fun setBusinessCodeInput() {
        local.businessCodeInput = "Test"
        Assert.assertEquals("Test", local.businessCodeInput)
    }

    @Test
    fun getMeetingRoomCodeInputShouldNotReturnNull() {
        Assert.assertNotNull(local.meetingRoomCodeInput)
    }

    @Test
    fun setMeetingRoomCodeInput() {
        local.meetingRoomCodeInput = "Test"
        Assert.assertEquals("Test", local.meetingRoomCodeInput)
    }
}