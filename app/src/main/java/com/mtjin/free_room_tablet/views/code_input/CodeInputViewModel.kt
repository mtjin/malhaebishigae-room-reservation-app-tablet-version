package com.mtjin.free_room_tablet.views.code_input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepository
import com.mtjin.free_room_tablet.utils.BUSINESS_CODE
import com.mtjin.free_room_tablet.utils.MEETING_ROOM_CODE
import com.mtjin.free_room_tablet.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CodeInputViewModel @Inject constructor(private val codeInputRepository: CodeInputRepository) :
    BaseViewModel() {
    // two-way binding
    val businessCode = MutableLiveData<String>("")
    val meetingRoomCode = MutableLiveData<String>("")

    private val _isBusinessCodeEmpty = SingleLiveEvent<Unit>()
    private val _isMeetingRoomEmpty = SingleLiveEvent<Unit>()
    private val _loginErrorMsg = SingleLiveEvent<Unit>()
    private val _goMain = SingleLiveEvent<Unit>()

    val isBusinessCodeEmpty: LiveData<Unit> get() = _isBusinessCodeEmpty
    val isMeetingRoomEmpty: LiveData<Unit> get() = _isMeetingRoomEmpty
    val loginErrorMsg: LiveData<Unit> get() = _loginErrorMsg
    val goMain: LiveData<Unit> get() = _goMain


    fun requestEnter() {
        if (checkValidate()) {
            compositeDisposable.add(
                codeInputRepository.requestEnter(
                    businessCode.value!!, // 초깃값 세팅되있기 때문에 null 불가
                    meetingRoomCode.value!!.toLong()
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doAfterTerminate { hideProgress() }
                    .subscribe({
                        BUSINESS_CODE = businessCode.value.toString()
                        MEETING_ROOM_CODE = meetingRoomCode.value.toString()
                        _goMain.call()
                    }, {
                        _loginErrorMsg.call()
                        Timber.d(it)
                    })
            )
        }
    }

    fun initEditText() {
        businessCode.value = codeInputRepository.businessCodeInput
        meetingRoomCode.value = codeInputRepository.meetingRoomCodeInput
    }

    private fun checkValidate(): Boolean {
        return when {
            businessCode.value.isNullOrBlank() -> {
                _isBusinessCodeEmpty.call()
                false
            }
            meetingRoomCode.value.isNullOrBlank() -> {
                _isMeetingRoomEmpty.call()
                false
            }
            else -> true
        }
    }
}
