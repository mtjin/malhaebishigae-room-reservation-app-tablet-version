package com.mtjin.free_room_tablet.code_input

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepository
import com.mtjin.free_room_tablet.views.code_input.CodeInputViewModel
import com.mtjin.free_room_tablet.getOrAwaitValue
import io.reactivex.Completable
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class CodeInputViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: CodeInputRepository
    lateinit var viewModel: CodeInputViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(repository.businessCodeInput).thenReturn("test")
        `when`(repository.meetingRoomCodeInput).thenReturn("test")
        `when`(repository.requestEnter("works_mobile", 1)).thenReturn(Completable.complete())
        viewModel =
            CodeInputViewModel(repository)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun businessCodeAndMeetingRoomCodeShouldSetAfterInitEditText() {
        viewModel.initEditText()
        Assert.assertEquals("test", viewModel.businessCode.getOrAwaitValue())
        Assert.assertEquals("test", viewModel.meetingRoomCode.getOrAwaitValue())

    }
}