package com.mtjin.free_room_tablet.qr_code

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepository
import com.mtjin.free_room_tablet.utils.NetworkManager
import com.mtjin.free_room_tablet.views.qr_code_reservation.QrCodeReservationViewModel
import com.mtjin.free_room_tablet.getOrAwaitValue
import io.reactivex.Completable
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class QrCodeReservationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Mock
    lateinit var repository: QrCodeReservationRepository
    lateinit var viewModel: QrCodeReservationViewModel
    lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        networkManager = NetworkManager(appContext)
        `when`(repository.requestQrCodeValidate("test")).thenReturn(Completable.complete())
        viewModel = QrCodeReservationViewModel(repository, networkManager)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNetworkState() {
    }

    @Test
    fun validateResultShouldTrueAfterRequestQrCodeValidateComplete() {
        viewModel.requestQrCodeValidate("test")
        viewModel.validateResult.getOrAwaitValue()
        Assert.assertTrue(viewModel.validateResult.value!!)
    }
}