package com.mtjin.free_room_tablet.data.code_input.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.utils.MEETING_ROOM
import io.reactivex.Completable
import javax.inject.Inject

class CodeInputRemoteDataSourceImpl @Inject constructor(private val database: DatabaseReference) :
    CodeInputRemoteDataSource {
    override fun requestEnter(businessCode: String, meetingRoomCode: Long): Completable {
        return Completable.create { emitter ->
            database.child(MEETING_ROOM)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) =
                        if (snapshot.hasChild(businessCode)) {
                            if (snapshot.child(businessCode)
                                    .hasChild(meetingRoomCode.toString())
                            ) {
                                emitter.onComplete()
                            } else {
                                emitter.onError(Throwable("No Room Code"))
                            }
                        } else {
                            emitter.onError(Throwable("No Business Code"))
                        }
                })
        }
    }

}