package com.dicoding.todoapp.ui.list

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.todoapp.R
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
@RunWith(AndroidJUnit4ClassRunner::class)
class TaskActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(TaskActivity::class.java)
    }

    @Test
    fun assertGetCircumference() {
        onView(withId(R.id.fab)).perform(click())

        //checking add_tv_due_date in activity_add_task  displayed
        onView(withId(R.id.add_tv_due_date))
            .check(matches(isDisplayed()))
    }
}