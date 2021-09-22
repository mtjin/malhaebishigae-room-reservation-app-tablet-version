package com.mtjin.free_room_tablet.views.reservation_select

import androidx.lifecycle.LiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.utils.SingleLiveEvent
import javax.inject.Inject

class ReservationSelectViewModel @Inject constructor() : BaseViewModel() {
    private val _goQrCodeReservation = SingleLiveEvent<Unit>()
    private val _goAnonymousReservation = SingleLiveEvent<Unit>()
    private val _goLoginReservation = SingleLiveEvent<Unit>()

    val goQrCodeReservation: LiveData<Unit> get() = _goQrCodeReservation
    val goAnonymousReservation: LiveData<Unit> get() = _goAnonymousReservation
    val goLoginReservation: LiveData<Unit> get() = _goLoginReservation

    fun goLoginReservation() {
        _goLoginReservation.call()
    }

    fun goQrCodeReservation() {
        _goQrCodeReservation.call()
    }

    fun goAnonymousReservation() {
        _goAnonymousReservation.call()
    }
}