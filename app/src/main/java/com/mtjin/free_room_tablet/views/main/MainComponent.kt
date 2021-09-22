package com.mtjin.free_room_tablet.views.main

import com.mtjin.free_room_tablet.di.ActivityScope
import com.mtjin.free_room_tablet.views.date_select.DateSelectFragment
import com.mtjin.free_room_tablet.views.login_reservation.LoginReservationFragment
import com.mtjin.free_room_tablet.views.qr_code_reservation.QrCodeReservationFragment
import com.mtjin.free_room_tablet.views.reservation.ReservationFragment
import com.mtjin.free_room_tablet.views.reservation_info.ReservationInfoFragment
import com.mtjin.free_room_tablet.views.reservation_select.ReservationSelectFragment
import com.mtjin.free_room_tablet.views.time_select.TimeSelectFragment
import com.mtjin.free_room_tablet.views.top.TopFragment
import dagger.Subcomponent

// Scope annotation that the RegistrationComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(reservationInfoFragment: ReservationInfoFragment)
    fun inject(reservationSelectFragment: ReservationSelectFragment)
    fun inject(loginReservationFragment: LoginReservationFragment)
    fun inject(qrCodeReservationFragment: QrCodeReservationFragment)
    fun inject(topFragment: TopFragment)
    fun inject(dateSelectFragment: DateSelectFragment)
    fun inject(timeSelectFragment: TimeSelectFragment)
    fun inject(reservationFragment: ReservationFragment)
}