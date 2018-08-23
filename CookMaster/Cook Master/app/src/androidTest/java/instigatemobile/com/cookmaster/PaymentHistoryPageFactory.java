package instigatemobile.com.cookmaster;

import android.support.test.espresso.ViewInteraction;
import android.view.View;


import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PaymentHistoryPageFactory {
    private Matcher<View> m_hamburger;
    private Matcher<View> m_title;
    private Matcher<View> m_settings;
    private Matcher<View> m_list;

    PaymentHistoryPageFactory() {
        m_hamburger = withId(R.id.drawer_layout);
        m_title = withText("History");
        m_settings = withContentDescription("More options");
        m_list = withId(R.id.list_view);
    }

    public Matcher<View> getM_hamburger() {
        return m_hamburger;
    }

    public Matcher<View> getM_title() {
        return m_title;
    }

    public Matcher<View> getM_settings() {
        return m_settings;
    }

    public Matcher<View> getM_list() {
        return m_list;
    }


    public void findAllElements() {

    }
}
