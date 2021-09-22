package com.mtjin.free_room_tablet.data.reservation.source.remote

import com.mtjin.free_room_tablet.api.ApiInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.model.User
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.fcm.NotificationBody
import com.mtjin.free_room_tablet.fcm.NotificationData
import com.mtjin.free_room_tablet.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ReservationRemoteDataSourceImpl @Inject constructor(
    private val database: DatabaseReference,
    private val fcmInterface: ApiInterface
) :
    ReservationRemoteDataSource {
    override fun requestAvailableTime(
        timestamp: Long
    ): Flowable<List<Reservation>> {
        return Flowable.create<List<Reservation>>({ emitter ->
            val reserveListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val reservationInfoList = ArrayList<Reservation>()
                    for (reserveSnapShot: DataSnapshot in snapshot.children) {
                        reserveSnapShot.getValue(Reservation::class.java)?.let {
                            if (it.meetingRoomId == MEETING_ROOM_CODE.toLong()) reservationInfoList.add(
                                it
                            )
                        }
                    }
                    emitter.onNext(reservationInfoList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(databaseError.toException())
                }
            }
            database.child(RESERVATION).child(BUSINESS_CODE)
                .orderByChild(DATE_TIMESTAMP).equalTo(timestamp.toDouble())
                .addValueEventListener(reserveListener)
            emitter.setCancellable {
                database.child(RESERVATION).child(BUSINESS_CODE)
                    .removeEventListener(reserveListener)
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun requestReservation(reservation: Reservation): Completable {
        return Completable.create { emitter ->
            database.child(RESERVATION).child(BUSINESS_CODE)
                .child(reservation.id.toString()).setValue(reservation)
                .addOnSuccessListener {
                    sendFCM(reservation)
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun sendFCM(reservation: Reservation) {
        database.child(USER).child(BUSINESS_CODE).child(reservation.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.d(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let {
                        fcmInterface.sendNotification(
                            NotificationBody(
                                it.fcm,
                                NotificationData(
                                    title = reservation.content,
                                    body = convertTimeToFcmMessage(
                                        date = reservation.dateTimestamp,
                                        startTime = reservation.startTimestamp
                                    ),
                                    isScheduled = "true",
                                    scheduledTime = reservation.startTimestamp.toString()
                                )
                            )
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    }
                }

            })
    }


}