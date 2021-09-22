package com.mtjin.free_room_tablet.data.qr_code.source

import io.reactivex.Completable

interface QrCodeReservationRepository {
    fun requestQrCodeValidate(userToken: String): Completable
}