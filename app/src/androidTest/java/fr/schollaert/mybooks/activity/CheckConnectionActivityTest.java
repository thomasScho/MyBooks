package fr.schollaert.mybooks.activity;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.schollaert.mybooks.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CheckConnectionActivityTest {
    private FirebaseAuth mAuth;

    @Rule
    public IntentsTestRule<CheckConnectionActivity> mActivityTestRule = new IntentsTestRule<>(CheckConnectionActivity.class);

    @After
    public void removeAuthAfterTest(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
            mAuth.signOut();
    }

    @Test
    public void checkConnectionActivityTest_noError() {
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.field_password),
                        withParent(withId(R.id.email_password_fields)),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("tototest"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.field_email),
                        withParent(withId(R.id.email_password_fields)),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("testapp3@smart.fr"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                withId(R.id.email_sign_in_button));
        appCompatButton.perform(click());

        intending(hasComponent(hasShortClassName(".MenuActivity")));
    }

    @Test
    public void checkConnectionActivityTest_errorEmptyPassword() {
        onView(withId(R.id.field_email)).perform(replaceText("testEmail@test.fr"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.field_password)).check(matches(withError("Required password")));
    }

    @Test
    public void checkConnectionActivityTest_errorEmptyEmail() {
        onView(withId(R.id.field_password)).perform(replaceText("testPassword"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.field_email)).check(matches(withError("Required email")));
    }


    @Test
    public void checkConnectionActivityTest_errorEmptyEmailPassword() {
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.field_email)).check(matches(withError("Required email")));
        onView(withId(R.id.field_password)).check(matches(withError("Required password")));
    }
    

    private static Matcher withError(final String expected) {
        return new TypeSafeMatcher() {
            @Override
            protected boolean matchesSafely(Object item) {
                if (item instanceof EditText) {
                    return ((EditText)item).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message [" + expected + "]");
            }
        };
    }
}
