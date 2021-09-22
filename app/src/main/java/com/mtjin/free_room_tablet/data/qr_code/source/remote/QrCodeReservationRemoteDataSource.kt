package com.mtjin.free_room_tablet.data.qr_code.source.remote

import io.reactivex.Completable

interface QrCodeReservationRemoteDataSource {
    fun requestQrCodeValidate(userToken : String): Completable
}