package com.example.farmy

import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class ProductInfoActivityTest {

    private lateinit var scenario: ActivityScenario<ProductInfoActivity>

    @Before
    fun setUp() {
        // Launch the activity
        scenario = ActivityScenario.launch(ProductInfoActivity::class.java)
    }

    @Test
    fun testProductsDisplayed() {
        // Check that the list view is displayed
        onView(withId(R.id.productsListView)).check(matches(isDisplayed()))

        // Check that the list view contains at least one item
        onView(withId(R.id.productsListView)).check(matches(hasMinimumChildCount(1)))

        // Check that the first item in the list view contains the name and price of a product
        onView(withText(containsString("$"))).check(matches(isDescendantOfA(nthChildOf(withId(R.id.productsListView), 0)))))
    }

    @Test
    fun testAddProductButton() {
        // Click the "Add Product" button
        onView(withId(R.id.editItem)).perform(click())

        // Check that the GroceryActivity is launched
        onView(withId(R.id.groceryActivity)).check(matches(isDisplayed()))
    }
}
