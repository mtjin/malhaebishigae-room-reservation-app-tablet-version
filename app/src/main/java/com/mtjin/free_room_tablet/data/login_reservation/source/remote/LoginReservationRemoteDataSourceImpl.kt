package com.mtjin.free_room_tablet.data.login_reservation.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.free_room_tablet.model.User
import com.mtjin.free_room_tablet.utils.BUSINESS_CODE
import com.mtjin.free_room_tablet.utils.USER
import io.reactivex.Single
import javax.inject.Inject

class LoginReservationRemoteDataSourceImpl @Inject constructor(private val database: DatabaseReference) :
    LoginReservationRemoteDataSource {
    override fun requestLogin(id: String, pw: String): Single<String> {
        return Single.create<String> { emitter ->
            database.child(USER).child(BUSINESS_CODE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var flag: Boolean = false
                        for (userSnapshot in snapshot.children) {
                            userSnapshot.getValue(User::class.java)?.let { user ->
                                if (user.name == id && user.pw == pw) {
                                    emitter.onSuccess(user.id)
                                    flag = true
                                }
                            }
                        }
                        if (!flag) {
                            emitter.onError(Throwable("일치하는 정보없음"))
                        }
                    }
                })
        }
    }
}