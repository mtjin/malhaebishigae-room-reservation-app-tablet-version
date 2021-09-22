package com.mtjin.free_room_tablet.data.login_reservation.source

import com.mtjin.free_room_tablet.data.login_reservation.source.LoginReservationRepository
import com.mtjin.free_room_tablet.data.login_reservation.source.remote.LoginReservationRemoteDataSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginReservationRepositoryImpl @Inject constructor(private val loginReservationRemoteDataSource: LoginReservationRemoteDataSource) :
    LoginReservationRepository {
    override fun requestLogin(id: String, pw: String): Single<String> =
        loginReservationRemoteDataSource.requestLogin(id, pw).observeOn(Schedulers.io())

}