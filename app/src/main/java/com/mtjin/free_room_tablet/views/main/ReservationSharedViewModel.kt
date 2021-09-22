package com.mtjin.free_room_tablet.views.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.data.reservation.source.ReservationRepository
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ReservationSharedViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {


    private var disabledTimeList = ArrayList<Pair<Long, Long>>() //이용 불가능한 시간리스트
    private var startTimestamp: Long = -1 // 시작 시간 타임스탬프
    private var endTimestamp: Long = -1 // 종료 시간 타임스탬프
    private var selectedStartTimestamp: Long = -1 // 시작 시간 타임스탬프
    private var selectedEndTimestamp: Long = -1 // 종료 시간 타임스탬프
    private val isAllSelected: Boolean get() = (date.value != "" && startTime.value != "" && endTime.value != "") // 모든 경우의 수 선택 유무
    var userToken = ""

    private val _messageSet = SingleLiveEvent<MessageSet>()
    private val _reserveList = MutableLiveData<ArrayList<Reservation>>()
    private val _successReservation = SingleLiveEvent<Boolean>()
    private val _date = MutableLiveData<String>("")
    private val _startTime = MutableLiveData<String>("")
    private val _endTime = MutableLiveData<String>("")
    private val _checkReservationDialog = SingleLiveEvent<Unit>()
    private val _goStartTimeSelect = SingleLiveEvent<Unit>()
    private val _goReservation = SingleLiveEvent<Unit>()

    // two-way binding
    var content: MutableLiveData<String> = MutableLiveData("")
    var register: MutableLiveData<String> = MutableLiveData("")

    val messageSet: LiveData<MessageSet> get() = _messageSet
    val reservationList: LiveData<ArrayList<Reservation>> get() = _reserveList
    val successReservation: LiveData<Boolean> get() = _successReservation
    val date: LiveData<String> get() = _date
    val startTime: LiveData<String> get() = _startTime
    val endTime: LiveData<String> get() = _endTime
    val checkReservationDialog: LiveData<Unit> get() = _checkReservationDialog
    val goStartTimeSelect: LiveData<Unit> get() = _goStartTimeSelect
    val goReservation: LiveData<Unit> get() = _goReservation


    fun requestAvailableTime(timestamp: Long) {
        if (networkManager.checkNetworkState()) {
            compositeDisposable.add(
                reservationRepository.requestAvailableTime(timestamp = timestamp)
                    .map { it.filter { room -> room.endTimestamp > getTimestamp() } }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doAfterTerminate { hideProgress() }
                    .subscribe({ reservations ->
                        //초기화
                        disabledTimeList.clear()
                        for (reservation in reservations) {
                            disabledTimeList.add(
                                Pair(
                                    reservation.startTimestamp,
                                    reservation.endTimestamp
                                )
                            )
                        }
                        _reserveList.value = reservations as ArrayList<Reservation>
                    }, {
                        Timber.d(it)
                    })
            )
        } else {
            _messageSet.value = MessageSet.NETWORK_ERROR
        }
    }

    private fun checkReservationAvailable(): Boolean {
        when {
            !networkManager.checkNetworkState() -> {
                _messageSet.value = MessageSet.NETWORK_ERROR
                return false
            }
            !isAllSelected || (!(checkStartTimeAvailable(startTimestamp) && checkEndTimeAvailable(
                endTimestamp
            ))) -> {
                _messageSet.value = MessageSet.RESERVATION_ERROR
                return false
            }
            content.value.toString().trim().isEmpty() -> {
                _messageSet.value = MessageSet.CONTENT_EMPTY
                return false
            }
            register.value.toString().trim().isEmpty() -> {
                _messageSet.value = MessageSet.REGISTER_EMPTY
                return false
            }
            !(checkStartTimeAvailable(startTimestamp) && checkEndTimeAvailable(endTimestamp)) -> {
                _messageSet.value = MessageSet.RESERVATION_ERROR
                return false
            }
        }
        return true
    }

    fun checkReservation() { // 예약할건지 확인 다이얼로그
        _checkReservationDialog.call()
    }

    fun requestReservation() {
        if (checkReservationAvailable()) {
            val reserve = Reservation(
                id = combineTimestamp(
                    "${date.value}-${endTime.value}".convertFullDateToTimestamp(),
                    getRandomKey()
                ),
                meetingRoomId = MEETING_ROOM_CODE.toLong(),
                dateTimestamp = date.value.toString().convertDateToTimestamp(),
                startTimestamp = startTimestamp,
                endTimestamp = endTimestamp,
                userId = userToken,
                register = register.value.toString().trim(),
                content = content.value.toString().trim()
            )
            compositeDisposable.add(
                reservationRepository.requestReservation(reserve)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showLottieProgress() }
                    .doAfterTerminate { hideLottieProgress() }
                    .subscribe({
                        _successReservation.value = true
                    }, {
                        Timber.d(it)
                        _successReservation.value = false
                    })
            )
        }

    }

    private fun checkStartTimeAvailable(timestamp: Long): Boolean {
        for (time in disabledTimeList) {
            if (timestamp >= time.first && timestamp < time.second) {
                return false
            }
        }
        startTimestamp = timestamp
        if (timestamp <= getTimestamp()) { // 현재시간보다 이전의 경우
            return false
        }
        return true
    }

    private fun checkEndTimeAvailable(timestamp: Long): Boolean {
        for (time in disabledTimeList) {
            if ((timestamp > time.first && timestamp <= time.second) || (startTimestamp <= time.first && timestamp > time.first)) {
                return false
            }
        }
        if (timestamp <= startTimestamp) {
            return false
        }
        endTimestamp = timestamp
        return true
    }

    fun setDate(date: String) {
        _date.value = date
        _startTime.value = ""
        _endTime.value = ""
    }

    fun setStartTime(timestamp: Long) {
        selectedStartTimestamp = timestamp
        _startTime.value = timestamp.convertTimestampToTime()
    }

    fun setEndTime(timestamp: Long) {
        selectedEndTimestamp = timestamp
        _endTime.value = timestamp.convertTimestampToTime()
    }

    fun goStartTimeSelect() {
        _goStartTimeSelect.call()
    }

    fun goReservation() {
        when {
            startTime.value == "" -> _messageSet.value = MessageSet.START_TIME_EMPTY
            endTime.value == "" -> _messageSet.value = MessageSet.END_TIME_EMPTY
            checkStartTimeAvailable(selectedStartTimestamp) && checkEndTimeAvailable(
                selectedEndTimestamp
            ) -> _goReservation.call()
            else -> _messageSet.value = MessageSet.RESERVATION_ERROR
        }
    }

    fun initViewModel() {
        disabledTimeList = ArrayList<Pair<Long, Long>>()
        startTimestamp = -1
        endTimestamp = -1
        selectedStartTimestamp = -1
        selectedEndTimestamp = -1
        userToken = ""
        _reserveList.value = ArrayList()
        _date.value = ""
        _startTime.value = ""
        _endTime.value = ""
        content = MutableLiveData("")
        register = MutableLiveData("")
    }

    enum class MessageSet {
        CONTENT_EMPTY,
        REGISTER_EMPTY,
        START_TIME_EMPTY,
        END_TIME_EMPTY,
        NETWORK_ERROR,
        RESERVATION_ERROR,
    }

}