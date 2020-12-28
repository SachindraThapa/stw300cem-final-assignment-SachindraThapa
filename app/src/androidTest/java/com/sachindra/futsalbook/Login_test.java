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

public class Login_test {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testSignup() {

        onView(withId(R.id.login_username)).perform(typeText("samyamdw@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("password"),closeSoftKeyboard());
        closeSoftKeyboard();
        onView( withId( R.id.login_submit ) ).perform( click() );


    }

}
