package com.mtjin.free_room_tablet.data.code_input.source

import com.mtjin.free_room_tablet.data.code_input.source.CodeInputRepository
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSource
import com.mtjin.free_room_tablet.data.code_input.source.remote.CodeInputRemoteDataSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CodeInputRepositoryImpl @Inject constructor(
    private val remoteCodeInputRemoteDataSource: CodeInputRemoteDataSource,
    private val codeInputLocalDataSource: CodeInputLocalDataSource
) : CodeInputRepository {

    override fun requestEnter(businessCode: String, meetingRoomCode: Long): Completable {
        return remoteCodeInputRemoteDataSource.requestEnter(businessCode, meetingRoomCode)
            .observeOn(Schedulers.io())
            .doOnComplete {
                businessCodeInput = businessCode
                meetingRoomCodeInput = meetingRoomCode.toString()
            }
    }

    override var businessCodeInput: String
        get() = codeInputLocalDataSource.businessCodeInput
        set(value) {
            codeInputLocalDataSource.businessCodeInput = value
        }

    override var meetingRoomCodeInput: String
        get() = codeInputLocalDataSource.meetingRoomCodeInput
        set(value) {
            codeInputLocalDataSource.meetingRoomCodeInput = value
        }

}