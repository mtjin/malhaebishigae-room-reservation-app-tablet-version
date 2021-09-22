package com.mtjin.free_room_tablet.data.top.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.model.MeetingRoom
import com.mtjin.free_room_tablet.utils.BUSINESS_CODE
import com.mtjin.free_room_tablet.utils.MEETING_ROOM
import com.mtjin.free_room_tablet.utils.MEETING_ROOM_CODE
import io.reactivex.Single
import javax.inject.Inject

class TopRemoteDataSourceImpl @Inject constructor(private val database: DatabaseReference) :
    TopRemoteDataSource {
    override fun requestMeetingRoomInfo(): Single<MeetingRoom> {
        return Single.create<MeetingRoom> { emitter ->
            database.child(MEETING_ROOM).child(BUSINESS_CODE).child(MEETING_ROOM_CODE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(MeetingRoom::class.java)?.let {
                            emitter.onSuccess(it)
                        }
                    }
                })
        }
    }
}