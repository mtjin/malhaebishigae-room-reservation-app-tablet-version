package com.mtjin.free_room_tablet.di.views

import androidx.lifecycle.ViewModel
import com.mtjin.free_room_tablet.views.code_input.CodeInputViewModel
import com.mtjin.free_room_tablet.views.login_reservation.LoginReservationViewModel
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import com.mtjin.free_room_tablet.views.qr_code_reservation.QrCodeReservationViewModel
import com.mtjin.free_room_tablet.views.reservation_info.ReservationInfoViewModel
import com.mtjin.free_room_tablet.views.reservation_select.ReservationSelectViewModel
import com.mtjin.free_room_tablet.views.top.TopViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReservationInfoViewModel::class)
    abstract fun bindReservationInfoViewModel(reservationInfoViewModel: ReservationInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopViewModel::class)
    abstract fun bindTopViewModel(topViewModel: TopViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CodeInputViewModel::class)
    abstract fun bindCodeInputViewModel(codeInputViewModel: CodeInputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReservationSelectViewModel::class)
    abstract fun bindReservationSelectViewModel(reservationSelectViewModel: ReservationSelectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginReservationViewModel::class)
    abstract fun bindLoginReservationViewModel(loginReservationViewModel: LoginReservationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QrCodeReservationViewModel::class)
    abstract fun bindQrCodeReservationViewModel(qrCodeReservationViewModel: QrCodeReservationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReservationSharedViewModel::class)
    abstract fun bindReservationSharedViewModel(reservationSharedViewModel: ReservationSharedViewModel): ViewModel
}