package com.mtjin.free_room_tablet.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var id: String = "",
    var fcm: String = "",
    var name: String = "",
    var pw: String = ""
)