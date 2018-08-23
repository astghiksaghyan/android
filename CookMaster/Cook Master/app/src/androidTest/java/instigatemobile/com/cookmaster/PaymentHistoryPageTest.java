package instigatemobile.com.cookmaster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import instigatemobile.com.cookmaster.activities.WelcomeActivity;

import static android.provider.Settings.Global.getString;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class PaymentHistoryPageTest {
    private PaymentHistoryPageFactory paymentHistoryPageFactory;

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule =
            new ActivityTestRule<>(WelcomeActivity.class, false, false);

    @Before
    public void setUp() {
        TestUtils.clearData();
        activityTestRule.launchActivity(null);
        paymentHistoryPageFactory = new PaymentHistoryPageFactory();
        TestUtils.skipWelcomePage();
        TestUtils.performLogeIn();
        TestUtils.openPage("Payment History");
    }

    @Test
    public void verifyPaymentHistoryPage() {
        onView(paymentHistoryPageFactory.getM_hamburger()).check(matches(isDisplayed()));
        onView(paymentHistoryPageFactory.getM_title()).check(matches(isDisplayed()));
        onView(paymentHistoryPageFactory.getM_settings()).check(matches(isDisplayed()));
        onView(paymentHistoryPageFactory.getM_list()).check(matches(isDisplayed()));
    }

    @Test
    public void checkListView() {
        final List<String> mMonthsList = new ArrayList<>();

        DatabaseReference mMonthReference = FirebaseDatabase.getInstance().getReference();

        mMonthReference.child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMonthsList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    mMonthsList.add(item.getKey());
                    onData(allOf(is(instanceOf(Map.class)), hasEntry(equalTo(item.getKey()), null)))
                .check(matches(isDisplayed()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



}