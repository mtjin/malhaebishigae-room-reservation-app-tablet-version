package com.mtjin.free_room_tablet.data.code_input.source

import io.reactivex.Completable

interface CodeInputRepository {

    fun requestEnter(businessCode: String, meetingRoomCode: Long): Completable
    var businessCodeInput: String
    var meetingRoomCodeInput: String
}