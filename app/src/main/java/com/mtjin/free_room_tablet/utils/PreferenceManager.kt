package com.mtjin.free_room_tablet.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(RESERVATION_APP, Context.MODE_PRIVATE)

    var businessCodeInput: String
        get() = sharedPref.getString(BUSINESS_CODE_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(BUSINESS_CODE_KEY, value)
            editor.apply()
        }

    var meetingRoomCodeInput: String
        get() = sharedPref.getString(MEETING_ROOM_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(MEETING_ROOM_KEY, value)
            editor.apply()
        }

    var registerInput: String
        get() = sharedPref.getString(REGISTER_INPUT_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(REGISTER_INPUT_KEY, value)
            editor.apply()
        }

    var contentInput: String
        get() = sharedPref.getString(CONTENT_INPUT_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(CONTENT_INPUT_KEY, value)
            editor.apply()
        }

    companion object {
        private const val BUSINESS_CODE_KEY = "BUSINESS_CODE_KEY"
        private const val MEETING_ROOM_KEY = "MEETING_ROOM_KEY"
        private const val RESERVATION_APP = "RESERVATION_APP"
        private const val REGISTER_INPUT_KEY = "REGISTER_INPUT_KEY"
        private const val CONTENT_INPUT_KEY = "CONTENT_INPUT_KEY"
    }
}