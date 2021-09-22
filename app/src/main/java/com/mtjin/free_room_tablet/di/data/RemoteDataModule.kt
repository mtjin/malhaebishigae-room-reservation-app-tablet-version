package com.mtjin.free_room_tablet.di.data

import com.mtjin.free_room_tablet.api.ApiInterface
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSource
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSourceImpl
import com.mtjin.free_room_tablet.data.login_reservation.source.remote.LoginReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.login_reservation.source.remote.LoginReservationRemoteDataSourceImpl
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.qr_code.source.remote.QrCodeReservationRemoteDataSourceImpl
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSource
import com.mtjin.free_room_tablet.data.reservation.source.remote.ReservationRemoteDataSourceImpl
import com.mtjin.free_room_tablet.data.reservation_info.source.remote.ReservationInfoRemoteDataSource
import com.mtjin.free_room_tablet.data.reservation_info.source.remote.ReservationInfoRemoteDataSourceImpl
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSource
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Provides
    @Reusable
    fun provideReservationInfoRemoteDataSource(database: DatabaseReference): ReservationInfoRemoteDataSource {
        return ReservationInfoRemoteDataSourceImpl(database)
    }

    @Provides
    @Reusable
    fun provideTopRemoteDataSource(database: DatabaseReference): TopRemoteDataSource {
        return TopRemoteDataSourceImpl(
            database
        )
    }

    @Provides
    @Reusable
    fun provideCodeInputRemoteDataSource(database: DatabaseReference): CodeInputRemoteDataSource {
        return CodeInputRemoteDataSourceImpl(database)
    }

    @Provides
    @Reusable
    fun provideLoginReservationRemoteDataSource(database: DatabaseReference): LoginReservationRemoteDataSource {
        return LoginReservationRemoteDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideReservationRemoteDataSource(
        database: DatabaseReference,
        apiInterface: ApiInterface
    ): ReservationRemoteDataSource {
        return ReservationRemoteDataSourceImpl(database, apiInterface)
    }

    @Provides
    @Reusable
    fun provideQrCodeReservationRemoteDataSource(database: DatabaseReference): QrCodeReservationRemoteDataSource {
        return QrCodeReservationRemoteDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideFireDatabase(): DatabaseReference {
        return Firebase.database.reference
    }
}