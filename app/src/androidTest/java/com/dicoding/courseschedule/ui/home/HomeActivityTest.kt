package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun assertAddCourseActivityOnAddCourseClick() {
        onView(withId(R.id.action_add)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.ed_course_name)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.ed_lecturer)).check(ViewAssertions.matches(isDisplayed()))
    }
}