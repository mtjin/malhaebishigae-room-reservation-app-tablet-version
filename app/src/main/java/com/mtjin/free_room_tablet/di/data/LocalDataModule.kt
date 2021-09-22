package com.mtjin.free_room_tablet.di.data

import android.content.Context
import androidx.room.Room
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSource
import com.mtjin.free_room_tablet.data.code_input.source.local.CodeInputLocalDataSourceImpl
import com.mtjin.free_room_tablet.data.database.RoomDatabase
import com.mtjin.free_room_tablet.data.top.source.local.MeetingRoomDao
import com.mtjin.free_room_tablet.data.top.source.local.TopLocalDataSource
import com.mtjin.free_room_tablet.data.top.source.local.TopLocalDataSourceImpl
import com.mtjin.free_room_tablet.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Reusable
    fun provideTopLocalDataSource(meetingRoomDao: MeetingRoomDao): TopLocalDataSource {
        return TopLocalDataSourceImpl(
            meetingRoomDao
        )
    }

    @Provides
    @Reusable
    fun provideMeetingRoomDao(roomDatabase: RoomDatabase): MeetingRoomDao =
        roomDatabase.meetingRoomDao()

    @Provides
    @Reusable
    fun provideCodeInputLocalDataSource(preferenceManager: PreferenceManager): CodeInputLocalDataSource =
        CodeInputLocalDataSourceImpl(preferenceManager)


    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "MeetingRoom.db"
        ).build()
    }
}