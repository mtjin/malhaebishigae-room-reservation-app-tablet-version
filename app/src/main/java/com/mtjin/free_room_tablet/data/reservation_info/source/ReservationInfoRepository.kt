package com.mtjin.free_room_tablet.data.reservation_info.source

import com.mtjin.free_room_tablet.model.Reservation
import io.reactivex.Flowable

interface ReservationInfoRepository {
    fun requestReservations(): Flowable<List<Reservation>>
}