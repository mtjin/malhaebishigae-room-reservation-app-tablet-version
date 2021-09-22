package com.mtjin.free_room_tablet.data.reservation_info.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class ReservationInfoRemoteDataSourceImpl @Inject constructor(private val database: DatabaseReference) :
    ReservationInfoRemoteDataSource {
    override fun requestReservations(): Flowable<List<Reservation>> {
        return Flowable.create<List<Reservation>>({ emitter ->
            val reserveListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val reservationLists: ArrayList<Reservation> = ArrayList()
                    for (reserveSnapShot: DataSnapshot in snapshot.children) {
                        reserveSnapShot.getValue(Reservation::class.java)?.let {
                            if (it.meetingRoomId == MEETING_ROOM_CODE.toLong()) reservationLists.add(
                                it
                            )
                        }
                    }
                    emitter.onNext(reservationLists)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(databaseError.toException())
                }
            }
            database.child(RESERVATION).child(BUSINESS_CODE)
                .orderByChild(DATE_TIMESTAMP).equalTo(
                    getTimestamp().convertCurrentTimestampToDateTimestamp().toDouble()
                )
                .addValueEventListener(reserveListener)
            emitter.setCancellable {
                database.child(RESERVATION).child(BUSINESS_CODE)
                    .removeEventListener(reserveListener)
            }
        }, BackpressureStrategy.BUFFER)
    }
}