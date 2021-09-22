package com.mtjin.free_room_tablet.data.code_input.source.local

import com.mtjin.free_room_tablet.utils.PreferenceManager
import javax.inject.Inject

class CodeInputLocalDataSourceImpl @Inject constructor(private val preferenceManager: PreferenceManager) :
    CodeInputLocalDataSource {
    override var businessCodeInput: String
        get() = preferenceManager.businessCodeInput
        set(value) {
            preferenceManager.businessCodeInput = value
        }

    override var meetingRoomCodeInput: String
        get() = preferenceManager.meetingRoomCodeInput
        set(value) {
            preferenceManager.meetingRoomCodeInput = value
        }

}