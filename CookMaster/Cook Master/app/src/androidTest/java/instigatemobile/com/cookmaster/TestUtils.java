package instigatemobile.com.cookmaster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class TestUtils {
    public static void performLogeIn() {
        onView(withId(R.id.input_email)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("student123@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("student123"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void openNavigationDrower() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));
        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
    }

    public static void skipWelcomePage() {
        onView(withText("skip")).perform(click());
    }

    public static void openPage(String pageName) {
        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withText(pageName)).check(matches(isDisplayed()));
        onView(withText(pageName)).perform(click());
    }

    public static void clearData() {
        File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
        String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
        for (String fileName : sharedPreferencesFileNames) {
            InstrumentationRegistry.getTargetContext()
                    .getSharedPreferences(fileName.replace(".xml", ""),
                            Context.MODE_PRIVATE).edit().clear().commit();
        }
    }
}
