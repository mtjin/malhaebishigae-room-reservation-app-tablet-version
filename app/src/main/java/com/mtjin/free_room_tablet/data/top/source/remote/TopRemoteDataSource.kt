package com.mtjin.free_room_tablet.data.top.source.remote

import com.mtjin.free_room_tablet.model.MeetingRoom
import io.reactivex.Single

interface TopRemoteDataSource {
    fun requestMeetingRoomInfo(): Single<MeetingRoom>
}