package com.mtjin.free_room_tablet.utils

// root data
const val MEETING_ROOM = "meeting_room" //회의실
const val RESERVATION = "reservation" // 회의실에약
const val USER = "user" // 유저프로필

// second child data
const val RESERVED_MEETING_ROOM = "reservedMeetingRoom" // 유저가 에약한 회의실

// query 에 사용할 child data
const val DATE_TIMESTAMP = "dateTimestamp" // 시간분초를 제와한 날짜 스탬프
const val ID = "id" // id

// 변할 수 있는 회사코드
var BUSINESS_CODE = "BUSINESS_CODE_EXAMPLE"
var MEETING_ROOM_CODE: String = "-1" // 회의실코드

var FCM_KEY: String =
    "AAAA_pVa0S0:APA91bEpiCDii31yuQV6_s9oEgHdo1sO24FBOaZfjoOjWb8_46e7xcNyGLFb4b2NrK3Sj22TTZRCxZveeErOlvjiE027EoLUOYvxDjpVYqNgl8OYE5T-t9x_uCARwCin1I71shlXgRqw"

const val EXTRA_NOTIFICATION_TITLE = "EXTRA_NOTIFICATION_TITLE"
const val EXTRA_NOTIFICATION_MESSAGE = "EXTRA_NOTIFICATION_MESSAGE"