package com.mtjin.free_room_tablet.data.code_input.source.remote

import io.reactivex.Completable

interface CodeInputRemoteDataSource {
    fun requestEnter(businessCode: String, meetingRoomCode: Long): Completable
}