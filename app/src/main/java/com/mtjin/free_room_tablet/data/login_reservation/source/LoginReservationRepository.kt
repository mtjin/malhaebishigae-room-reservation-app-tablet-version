package com.mtjin.free_room_tablet.data.login_reservation.source

import io.reactivex.Single

interface LoginReservationRepository {
    fun requestLogin(id: String, pw: String): Single<String>
}