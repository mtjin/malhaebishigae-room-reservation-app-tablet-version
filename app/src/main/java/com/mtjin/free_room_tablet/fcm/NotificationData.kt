package com.mtjin.free_room_tablet.fcm

data class NotificationData(
    val title: String,
    val body: String,
    var isScheduled: String = "false",
    var scheduledTime: String = ""
)