package com.sachindra.futsalbook;

import androidx.test.rule.ActivityTestRule;

import com.samyam.indoorcricket.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class UserInfo_test {
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
        onView(withId(R.id.navigation_menu)).perform(click());
        onView(withId(R.id.menu_profile)).perform(click());



    }
}
