package com.mtjin.free_room_tablet.data.reservation.source

import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSource
import com.mtjin.free_room_tablet.model.Reservation
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationRemoteDataSource: ReservationRemoteDataSource
) :
    ReservationRepository {
    override fun requestAvailableTime(
        timestamp: Long
    ): Flowable<List<Reservation>> =
        reservationRemoteDataSource.requestAvailableTime(timestamp)
            .observeOn(Schedulers.io())

    override fun requestReservation(reservation: Reservation): Completable =
        reservationRemoteDataSource.requestReservation(reservation)
            .observeOn(Schedulers.io())
}