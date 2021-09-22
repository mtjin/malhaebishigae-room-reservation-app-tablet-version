package com.mtjin.free_room_tablet.code_input

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.views.code_input.CodeInputActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CodeInputActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(CodeInputActivity::class.java)
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkBusinessCodeEditTextInput() {
        onView(withId(R.id.et_business_code)).perform(closeSoftKeyboard())
        onView(withId(R.id.et_business_code))
            .perform(typeText("works_mobile"), closeSoftKeyboard())
        onView(withId(R.id.et_business_code)).check(matches(withText("works_mobile")))
    }

    @Test
    fun checkRoomCodeEditTextInput() {
        onView(withId(R.id.et_room_code)).perform(closeSoftKeyboard())
        onView(withId(R.id.et_room_code)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.et_room_code)).check(matches(withText("1")))
    }

    @Test
    fun checkAllEditText() {
        onView(withId(R.id.et_room_code)).perform(closeSoftKeyboard())
        onView(withId(R.id.et_business_code))
            .perform(typeText("works_mobile"), closeSoftKeyboard())
        onView(withId(R.id.et_business_code)).check(matches(withText("works_mobile")))
        onView(withId(R.id.et_room_code)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.et_room_code)).check(matches(withText("1")))
    }

    @Test
    fun checkBusinessCodeEditTextError() {
        onView(withId(R.id.et_room_code)).perform(closeSoftKeyboard())
        onView(withId(R.id.et_room_code)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.et_room_code)).check(matches(withText("1")))
        onView(withId(R.id.btn_enter)).perform(click())
        onView(withId(R.id.et_business_code))
            .check(matches(ViewMatchers.hasErrorText(appContext.getString(R.string.enter_business_code_text))))
    }

    @Test
    fun checkRoomCodeEditTextError() {
        onView(withId(R.id.et_business_code)).perform(typeText("works_mobile"), closeSoftKeyboard())
        onView(withId(R.id.et_business_code)).check(matches(withText("works_mobile")))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.btn_enter)).perform(click())
        onView(withId(R.id.et_room_code))
            .check(matches(ViewMatchers.hasErrorText(appContext.getString(R.string.enter_meeting_room_code_text))))
    }

}