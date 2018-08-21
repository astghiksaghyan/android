package com.instigatemobile.grapes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import android.support.test.espresso.Espresso;

import com.instigatemobile.grapes.activities.LogInActivity;
import com.instigatemobile.grapes.activities.PreviewActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<PreviewActivity> mActivityRule = new ActivityTestRule<>(
            PreviewActivity.class);


    @Test
    public void checkSkipButton() {
        String nickname = "Michael";
        //String dialogText = "Allow Grapes to access photos, media, and files on your device?";
        onView(withId(R.id.btn_skip))
                .perform(click());
        onView(withId(R.id.image)).check(matches(isDisplayed()));
        onView(withId(R.id.nickname)).check(matches(isDisplayed()));
        onView(withId(R.id.nickname)).check(matches(withHint("Nickname")));
        onView(withId(R.id.submit)).check(matches(isDisplayed()));
        onView(withId(R.id.submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.nickname)).perform(typeText(nickname));
        onView(withId(R.id.submit)).check(matches(isEnabled()));
        onView(withId(R.id.submit)).perform(click());
        //onView(withText(dialogText)).check(matches(isDisplayed()));
    }
}
