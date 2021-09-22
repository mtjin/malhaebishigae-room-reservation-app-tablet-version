package com.mtjin.free_room_tablet.api

import com.mtjin.free_room_tablet.fcm.NotificationBody
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("fcm/send")
    fun sendNotification(
        @Body notification: NotificationBody
    ): Single<ResponseBody>
}