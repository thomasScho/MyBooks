package fr.schollaert.mybooks.activity;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import fr.schollaert.mybooks.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Thomas on 01/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public class FindFriendsActivityTest {
    @Rule
    public IntentsTestRule<CheckConnectionActivity> mActivityTestRule = new IntentsTestRule<>(CheckConnectionActivity.class);
    private FirebaseAuth mAuth;
    private Random random = new Random();

    @Before
    public void loginBeforeTest() {
        onView(
                allOf(withId(R.id.field_password),
                        withParent(withId(R.id.email_password_fields)),
                        isDisplayed())).perform(replaceText("tototest"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.field_email),
                        withParent(withId(R.id.email_password_fields)),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("testapp3@smart.fr"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                withId(R.id.email_sign_in_button));
        appCompatButton.perform(click());

        onView((withId(R.id.findBookButton))).perform(click());
        SystemClock.sleep(1000);
    }

    @After
    public void removeAuthAfterTest() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            mAuth.signOut();
    }

    @Test
    public void checkFindingExistingFriend(){

    }


}
