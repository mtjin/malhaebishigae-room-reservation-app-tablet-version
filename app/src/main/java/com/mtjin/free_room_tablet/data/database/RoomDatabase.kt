package com.mtjin.free_room_tablet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtjin.free_room_tablet.model.MeetingRoom
import com.mtjin.free_room_tablet.data.top.source.local.MeetingRoomDao

@Database(entities = [MeetingRoom::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun meetingRoomDao(): MeetingRoomDao
}