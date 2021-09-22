package com.mtjin.free_room_tablet.top

import android.util.Log
import com.mtjin.free_room_tablet.data.top.source.TopRepository
import com.mtjin.free_room_tablet.data.top.source.TopRepositoryImpl
import com.mtjin.free_room_tablet.data.top.source.local.TopLocalDataSource
import com.mtjin.free_room_tablet.data.top.source.remote.TopRemoteDataSource
import com.mtjin.free_room_tablet.model.MeetingRoom
import com.mtjin.free_room_tablet.utils.MEETING_ROOM_CODE
import com.mtjin.free_room_tablet.TAG
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.*
import org.junit.Assert.fail
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class TopRepositoryImplTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var remote: TopRemoteDataSource

    @Mock
    lateinit var local: TopLocalDataSource

    private lateinit var repository: TopRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(local.getMeetingRoomById(1))
            .thenReturn(Single.just(MeetingRoom(id = 1, name = "테스트")))

        `when`(
            local.insertMeetingRoom(MeetingRoom(id = 1, name = "테스트"))
        ).thenReturn(Completable.complete())

        `when`(remote.requestMeetingRoomInfo()).thenReturn(
            Single.just(
                MeetingRoom(
                    id = 1,
                    name = "테스트"
                )
            )
        )
        repository = TopRepositoryImpl(remote, local)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestMeetingRoomInfo() {
        repository.requestRemoteMeetingRoomInfo().test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed() // Subscribe 되었는지
                Assert.assertNotNull(it) // Null 체크
                if (it.values()[0].id <= 0) fail("id는 항상 0보다 커야합니다.")
                Log.d(TAG, it.values().toString())
            }
    }

    @Test
    fun requestRemoteMeetingRoomInfoShouldGetSameRoomId() {
        MEETING_ROOM_CODE = 1.toString()
        repository.requestRemoteMeetingRoomInfo().test()
            .awaitDone(3000, TimeUnit.MILLISECONDS)
            .assertOf { it ->
                it.assertSubscribed()
                it.assertNoErrors()
                if (it.values().isNotEmpty()) {
                    it.values()[0].let { room ->
                        Log.d(TAG, room.toString())
                        if (room.id != MEETING_ROOM_CODE.toLong()) {
                            fail("불러온 회의실 아이디가 요청한거와 다릅니다.")
                        }
                    }
                }
            }
    }

}