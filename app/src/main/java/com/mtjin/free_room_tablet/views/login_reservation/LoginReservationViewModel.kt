package com.mtjin.free_room_tablet.views.login_reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.data.login_reservation.source.LoginReservationRepository
import com.mtjin.free_room_tablet.utils.NetworkManager
import com.mtjin.free_room_tablet.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class LoginReservationViewModel @Inject constructor(
    private val loginReservationRepository: LoginReservationRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {

    val userId = MutableLiveData<String>("")
    val userPw = MutableLiveData<String>("")

    private val _networkState = SingleLiveEvent<Unit>()
    private val _loginSuccess = SingleLiveEvent<String>()
    private val _loginFail = SingleLiveEvent<String>()

    val networkState: LiveData<Unit> get() = _networkState
    val loginSuccess: LiveData<String> get() = _loginSuccess
    val loginFail: LiveData<String> get() = _loginFail


    fun requestLogin() {
        if (!networkManager.checkNetworkState()) {
            _networkState.call()
        } else {
            compositeDisposable.add(
                loginReservationRepository.requestLogin(userId.value!!, userPw.value!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doAfterTerminate { hideProgress() }
                    .subscribe({ userToken ->
                        _loginSuccess.value = userToken
                    }, {
                        Timber.d(it)
                        _loginFail.call()
                    })
            )
        }
    }
}