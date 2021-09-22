package com.mtjin.free_room_tablet.data.reservation.source

import com.mtjin.free_room_tablet.model.Reservation
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReservationRepository {
    fun requestAvailableTime(timestamp: Long): Flowable<List<Reservation>>

    fun requestReservation(reservation: Reservation): Completable
}