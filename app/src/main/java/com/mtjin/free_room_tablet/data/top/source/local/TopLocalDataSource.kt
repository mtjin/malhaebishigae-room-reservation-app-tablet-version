package com.mtjin.free_room_tablet.data.top.source.local

import com.mtjin.free_room_tablet.model.MeetingRoom
import io.reactivex.Completable
import io.reactivex.Single

interface TopLocalDataSource {
    fun insertMeetingRoom(meetingRoom: MeetingRoom): Completable
    fun getMeetingRoomById(id: Long): Single<MeetingRoom>
    fun deleteMeetingRoom(meetingRoom: MeetingRoom): Completable
}