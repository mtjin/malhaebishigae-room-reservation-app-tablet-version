package com.mtjin.free_room_tablet.data.top.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mtjin.free_room_tablet.model.MeetingRoom
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MeetingRoomDao {

    @Insert(onConflict = REPLACE)
    fun insertMeetingRoom(meetingRoom: MeetingRoom): Completable

    @Query("SELECT * FROM meetingRoom WHERE id = :id limit 1")
    fun getMeetingRoomById(id: Long): Single<MeetingRoom>

    @Delete
    fun deleteMeetingRoom(meetingRoom: MeetingRoom): Completable
}