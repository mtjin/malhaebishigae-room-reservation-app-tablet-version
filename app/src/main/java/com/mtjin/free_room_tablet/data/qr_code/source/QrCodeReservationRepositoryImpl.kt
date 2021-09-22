package com.mtjin.free_room_tablet.data.qr_code.source

import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QrCodeReservationRepositoryImpl @Inject constructor(private val qrCodeReservationRemoteDataSource: QrCodeReservationRemoteDataSource) :
    QrCodeReservationRepository {
    override fun requestQrCodeValidate(userToken: String) =
        qrCodeReservationRemoteDataSource.requestQrCodeValidate(userToken)
            .observeOn(Schedulers.io())
}