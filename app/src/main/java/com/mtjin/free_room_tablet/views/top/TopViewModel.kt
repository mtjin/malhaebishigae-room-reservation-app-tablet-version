package com.mtjin.free_room_tablet.views.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.free_room_tablet.base.BaseViewModel
import com.mtjin.free_room_tablet.data.top.source.TopRepository
import com.mtjin.free_room_tablet.utils.getTimestamp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopViewModel @Inject constructor(private val topRepository: TopRepository) :
    BaseViewModel() {
    private val _meetingRoomName = MutableLiveData<String>()
    private val _meetingRoomImage = MutableLiveData<String>()
    private val _date = MutableLiveData<Long>()
    private val _time = MutableLiveData<Long>()

    val meetingRoomName: LiveData<String> get() = _meetingRoomName
    val meetingRoomImage: LiveData<String> get() = _meetingRoomImage
    val date: LiveData<Long> get() = _date
    val time: LiveData<Long> get() = _time

    fun requestMeetingRoomInfo() {
        compositeDisposable.add(
            topRepository.requestMeetingRoomInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribe({ room ->
                    _meetingRoomName.value = room.name
                    _meetingRoomImage.value = room.image
                }, {
                    Timber.d(it)
                })
        )
    }

    fun initDateTime() {
        _date.value = getTimestamp()
        _time.value = getTimestamp()
        compositeDisposable.add(
            Observable.timer(4, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _date.value = getTimestamp()
                    _time.value = getTimestamp()
                }
        )
    }
}