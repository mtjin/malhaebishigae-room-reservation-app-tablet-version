package com.mtjin.free_room_tablet.di.data

import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepository
import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepositoryImpl
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSource
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSource
import com.mtjin.free_room_tablet.data.login_reservation.source.LoginReservationRepository
import com.mtjin.free_room_tablet.data.login_reservation.source.LoginReservationRepositoryImpl
import com.mtjin.free_room_tablet.data.login_reservation.source.remote.LoginReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepository
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepositoryImpl
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.reservation.source.ReservationRepository
import com.mtjin.free_room_tablet.data.reservation.source.ReservationRepositoryImpl
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.reservation_info.source.ReservationInfoRepository
import com.mtjin.free_room_tablet.data.reservation_info.source.ReservationInfoRepositoryImpl
import com.mtjin.free_room_tablet.data.reservation_info.source.remote.ReservationInfoRemoteDataSource
import com.mtjin.free_room_tablet.data.top.source.TopRepository
import com.mtjin.free_room_tablet.data.top.source.TopRepositoryImpl
import com.mtjin.free_room_tablet.data.top.source.local.TopLocalDataSource
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Reusable
    fun provideReservationInfoRepository(reservationInfoRemoteDataSource: ReservationInfoRemoteDataSource): ReservationInfoRepository =
        ReservationInfoRepositoryImpl(reservationInfoRemoteDataSource)

    @Provides
    @Reusable
    fun provideTopRepository(
        topRemoteDataSource: TopRemoteDataSource,
        topLocalDataSource: TopLocalDataSource
    ): TopRepository =
        TopRepositoryImpl(
            topRemoteDataSource,
            topLocalDataSource
        )

    @Provides
    @Reusable
    fun provideCodeInputRepository(
        codeInputRemoteDataSource: CodeInputRemoteDataSource,
        codeInputLocalDataSource: CodeInputLocalDataSource
    ): CodeInputRepository =
        CodeInputRepositoryImpl(codeInputRemoteDataSource, codeInputLocalDataSource)

    @Provides
    @Reusable
    fun provideLoginReservationRepository(
        loginReservationRemoteDataSource: LoginReservationRemoteDataSource
    ): LoginReservationRepository =
        LoginReservationRepositoryImpl(loginReservationRemoteDataSource)

    @Provides
    @Singleton
    fun provideReservationRepository(
        reservationRemoteDataSource: ReservationRemoteDataSource
    ): ReservationRepository =
        ReservationRepositoryImpl(reservationRemoteDataSource)

    @Provides
    @Reusable
    fun provideQrCodeReservationRepository(
        qrCodeReservationRemoteDataSource: QrCodeReservationRemoteDataSource
    ): QrCodeReservationRepository =
        QrCodeReservationRepositoryImpl(qrCodeReservationRemoteDataSource)
}
