package com.mtjin.free_room_tablet.data.top.source

import com.mtjin.free_room_tablet.model.MeetingRoom
import io.reactivex.Flowable
import io.reactivex.Single

interface TopRepository {
    fun requestMeetingRoomInfo(): Flowable<MeetingRoom>
    fun requestRemoteMeetingRoomInfo(): Single<MeetingRoom>
}