package com.mtjin.free_room_tablet.data.reservation_info.source

import com.mtjin.free_room_tablet.data.reservation_info.source.remote.ReservationInfoRemoteDataSource
import com.mtjin.free_room_tablet.model.Reservation
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationInfoRepositoryImpl @Inject constructor(private val reservationInfoRemoteDataSource: ReservationInfoRemoteDataSource) :
    ReservationInfoRepository {
    override fun requestReservations(): Flowable<List<Reservation>> =
        reservationInfoRemoteDataSource.requestReservations()
            .observeOn(Schedulers.io())
}