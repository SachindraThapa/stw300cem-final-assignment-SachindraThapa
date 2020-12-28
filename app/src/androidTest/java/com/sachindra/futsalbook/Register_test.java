package com.sachindra.futsalbook;

import androidx.test.rule.ActivityTestRule;

import com.samyam.indoorcricket.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class Register_test {
    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void testSignup() {
        onView(withId(R.id.reg_name)).perform(typeText("samyam"));
        closeSoftKeyboard();
        onView(withId(R.id.reg_username)).perform(typeText("samyam001"));
        closeSoftKeyboard();
        onView(withId(R.id.reg_email)).perform(typeText("samyam@gmail.com"));
        closeSoftKeyboard();

        onView(withId(R.id.reg_phone)).perform(typeText("9899899890"));
        closeSoftKeyboard();
        onView(withId(R.id.reg_password)).perform(typeText("android"),closeSoftKeyboard());

        onView(withId(R.id.reg_con_password)).perform(typeText("android"),closeSoftKeyboard());
        closeSoftKeyboard();
        onView( withId( R.id.reg_submit ) ).perform( click() );


    }
}

