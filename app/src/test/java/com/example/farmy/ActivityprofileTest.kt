package com.example.farmy

import android.content.Intent
import android.view.View
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.runner.AndroidJUnit4
import androidx.test.espresso.Espresso.*


@RunWith(AndroidJUnit4ClassRunner::class)
class ActivityprofileTest {
    @Test
    fun testActivityLaunch() {
        // Test that clicking the scan button launches the Scan activity
        // (replace the activity class name and button ID with the actual values)
        val scenario = launch(Activityprofile::class.java)
        onView(withId(R.id.heal_btn)).perform(click())
        val nextActivity = scenario.result?.peekNextIntent()?.component?.className
        assertEquals(Scan::class.java.name, nextActivity)
    }

    // Add more unit tests for each button click and activity launch here

    @Test
    fun testNavigation() {
        val scenario = launch(Activityprofile::class.java)
        onView(withId(R.id.bottomNavigation)).perform(click())
        onView(withId(R.id.bottom_scan)).perform(click())
        val nextActivity = scenario.result?.peekNextIntent()?.component?.className
        assertEquals(Scan::class.java.name, nextActivity)
    }
}
