package com.mtjin.free_room_tablet.views.qr_code_reservation

import androidx.lifecycle.LiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.data.qr_code.source.QrCodeReservationRepository
import com.mtjin.free_room_tablet.utils.NetworkManager
import com.mtjin.free_room_tablet.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class QrCodeReservationViewModel @Inject constructor(
    private val qrCodeReservationRepository: QrCodeReservationRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {

    private val _networkState = SingleLiveEvent<Unit>()
    private val _validateResult = SingleLiveEvent<Boolean>()

    val networkState: LiveData<Unit> get() = _networkState
    val validateResult: LiveData<Boolean> get() = _validateResult

    fun requestQrCodeValidate(userToken: String) {
        if (!networkManager.checkNetworkState()) {
            _networkState.call()
        } else {
            compositeDisposable.add(
                qrCodeReservationRepository.requestQrCodeValidate(userToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doAfterTerminate { hideProgress() }
                    .subscribe({
                        _validateResult.value = true

                    }, {
                        Timber.d(it)
                        _validateResult.value = false
                    })
            )
        }
    }
}