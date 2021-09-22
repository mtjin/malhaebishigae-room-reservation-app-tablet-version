package com.mtjin.free_room_tablet.data.reservation_info.source.remote

import com.mtjin.free_room_tablet.model.Reservation
import io.reactivex.Flowable

interface ReservationInfoRemoteDataSource {
    fun requestReservations(): Flowable<List<Reservation>>
}