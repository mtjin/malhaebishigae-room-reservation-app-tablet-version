package com.mtjin.free_room_tablet.data.qr_code.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.utils.BUSINESS_CODE
import com.mtjin.free_room_tablet.utils.USER
import io.reactivex.Completable
import javax.inject.Inject

class QrCodeReservationRemoteDataSourceImpl @Inject constructor(private val database: DatabaseReference) :
    QrCodeReservationRemoteDataSource {
    override fun requestQrCodeValidate(userToken: String): Completable {
        return Completable.create { emitter ->
            database.child(USER).child(BUSINESS_CODE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(userToken)) emitter.onComplete()
                        else emitter.onError(Throwable("해당 유저 없음"))
                    }
                })
        }
    }

}