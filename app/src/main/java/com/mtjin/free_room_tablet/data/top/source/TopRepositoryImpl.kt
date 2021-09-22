package com.mtjin.free_room_tablet.data.top.source

import com.mtjin.free_room_tablet.data.top.source.local.TopLocalDataSource
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSource
import com.mtjin.free_room_tablet.model.MeetingRoom
import com.mtjin.free_room_tablet.utils.MEETING_ROOM_CODE
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val topRemoteDataSource: TopRemoteDataSource,
    private val topLocalDataSource: TopLocalDataSource
) : TopRepository {
    override fun requestMeetingRoomInfo(): Flowable<MeetingRoom> {
        return topLocalDataSource.getMeetingRoomById(MEETING_ROOM_CODE.toLong())
            .observeOn(Schedulers.io())
            .onErrorReturn { MeetingRoom() }
            .flatMapPublisher { cachedRoom ->
                if (cachedRoom.id <= 0) {
                    requestRemoteMeetingRoomInfo().toFlowable().onErrorReturn { MeetingRoom() }
                } else {
                    val local = Single.just(cachedRoom)
                    val remote = requestRemoteMeetingRoomInfo().onErrorResumeNext { local }
                    Single.concat(local, remote)
                }
            }
    }

    override fun requestRemoteMeetingRoomInfo(): Single<MeetingRoom> {
        return topRemoteDataSource.requestMeetingRoomInfo()
            .observeOn(Schedulers.io())
            .flatMap { room ->
                topLocalDataSource.insertMeetingRoom(room).andThen(Single.just(room))
            }
    }
}