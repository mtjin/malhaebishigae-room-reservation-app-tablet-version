package com.mtjin.free_room_tablet.views.reservation_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.data.reservation_info.source.ReservationInfoRepository
import com.mtjin.free_room_tablet.utils.NetworkManager
import com.mtjin.free_room_tablet.utils.SingleLiveEvent
import com.mtjin.free_room_tablet.utils.getTimestamp
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReservationInfoViewModel @Inject constructor(
    private val reservationInfoRepository: ReservationInfoRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {
    private var isExistCurrent = false // 현재 진행중인 회의 있는지

    private val _networkState = SingleLiveEvent<Unit>()
    private val _goReservation = SingleLiveEvent<Unit>()
    private val _reservationList = MutableLiveData<ArrayList<Reservation>>()
    private val _currentRoomStartTimestamp = MutableLiveData<Long>(0)
    private val _currentRoomEndTimestamp = MutableLiveData<Long>(0)
    private val _conferenceName = MutableLiveData<String>()
    private val _register = MutableLiveData<String>()

    val networkState: LiveData<Unit> get() = _networkState
    val goReservation: LiveData<Unit> get() = _goReservation
    val reservationList: LiveData<ArrayList<Reservation>> get() = _reservationList
    val currentRoomStartTimestamp: LiveData<Long> get() = _currentRoomStartTimestamp
    val currentRoomEndTimestamp: LiveData<Long> get() = _currentRoomEndTimestamp
    val conferenceName: LiveData<String> get() = _conferenceName
    val register: LiveData<String> get() = _register

    fun goReservation() {
        _goReservation.call()
    }

    fun requestReservations() {
        if (!networkManager.checkNetworkState()) {
            _networkState.call()
        } else {
            initLiveData()
            compositeDisposable.add(
                Flowable.timer(2, TimeUnit.SECONDS)
                    .repeat()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        reservationInfoRepository.requestReservations()
                            .map { it.filter { room -> room.endTimestamp > getTimestamp() } }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ reservations ->
                                isExistCurrent = false
                                for (reservation in reservations) {
                                    if (reservation.startTimestamp <= getTimestamp()) {
                                        _conferenceName.value = reservation.content
                                        _register.value = reservation.register
                                        _currentRoomStartTimestamp.value =
                                            reservation.startTimestamp
                                        _currentRoomEndTimestamp.value =
                                            reservation.endTimestamp
                                        isExistCurrent = true
                                    }
                                }
                                if (!isExistCurrent) {
                                    initLiveData()
                                }
                                _reservationList.value =
                                    reservations as ArrayList<Reservation>
                            }, {
                                Timber.d(it)
                            })

                    }
            )
        }
    }

    private fun initLiveData() {
        _currentRoomStartTimestamp.value = 0
        _currentRoomEndTimestamp.value = 0
        _conferenceName.value = ""
        _register.value = ""
    }
}