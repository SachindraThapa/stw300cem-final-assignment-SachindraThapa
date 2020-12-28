package com.sachindra.futsalbook;

import androidx.test.rule.ActivityTestRule;

import com.samyam.indoorcricket.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

public class Booking_test {
    @Rule
    public ActivityTestRule<DashboardActivity> activityTestRule = new ActivityTestRule<>( DashboardActivity.class );
    @Before
    public void setFragment(){
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction();
    }
    @Test
    public void testFragment(){
        onView(withId(R.id.navigation_booking)).perform(click());
        onView(withId(R.id.bookingDate)).perform(typeText("2020-02-12"),closeSoftKeyboard());

        //bookingTime
        onView(withId(R.id.bookingTime)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.book_ground)).perform(click());


    }
}


