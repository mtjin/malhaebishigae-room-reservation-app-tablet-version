package com.mtjin.free_room_tablet.data.top.source.local

import com.mtjin.free_room_tablet.model.MeetingRoom
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TopLocalDataSourceImpl @Inject constructor(private val meetingRoomDao: MeetingRoomDao) :
    TopLocalDataSource {
    override fun insertMeetingRoom(meetingRoom: MeetingRoom): Completable =
        meetingRoomDao.insertMeetingRoom(meetingRoom)

    override fun getMeetingRoomById(id: Long): Single<MeetingRoom> =
        meetingRoomDao.getMeetingRoomById(id)

    override fun deleteMeetingRoom(meetingRoom: MeetingRoom): Completable =
        meetingRoomDao.deleteMeetingRoom(meetingRoom)
}