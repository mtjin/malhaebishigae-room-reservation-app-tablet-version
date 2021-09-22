package com.mtjin.free_room_tablet.data.login_reservation.source.remote

import io.reactivex.Single

interface LoginReservationRemoteDataSource {
    fun requestLogin(id: String, pw: String): Single<String>
}