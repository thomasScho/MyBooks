package fr.schollaert.mybooks.activity;

import android.app.Application;
import android.content.Context;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.test.espresso.Root;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import fr.schollaert.mybooks.R;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Math.random;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Thomas on 28/02/2017.
 */
@RunWith(AndroidJUnit4.class)
public class FindBooksActivityTest {
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
    public void check_findImpossible_error(){
        String bookUnreachable = "TEST ANDROID"+ random();
        onView(withId(R.id.findingText)).perform(replaceText(bookUnreachable), closeSoftKeyboard());
        onView(withId(R.id.envoyerRecherche)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listBookView)).atPosition(0).check(matches(not(isDisplayed())));

        //onView(withText("Aucun livre n'a été trouvé avec ce titre")).inRoot(new ToastMatcher()).check(matches(withText("Aucun livre n'a été trouvé avec ce titre")));
    }

    @Test
    public void check_find_noError(){
        String bookReachable = "Harry Potter";
        onView(withId(R.id.findingText)).perform(replaceText(bookReachable), closeSoftKeyboard());
        onView(withId(R.id.envoyerRecherche)).perform(click());
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.listBookView)).check(matches(hasDescendant(withText(bookReachable))));

        //onView(withText("Aucun livre n'a été trouvé avec ce titre")).inRoot(new ToastMatcher()).check(matches(withText("Aucun livre n'a été trouvé avec ce titre")));
    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {
        @Override public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    //means this window isn't contained by any other windows.
                }
            }
            return false;
        }
    }
}
