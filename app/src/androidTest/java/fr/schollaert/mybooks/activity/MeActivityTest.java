package fr.schollaert.mybooks.activity;

/**
 * Created by Thomas on 28/02/2017.
 */

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;

import fr.schollaert.mybooks.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Math.random;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeActivityTest {
    @Rule
    public IntentsTestRule<CheckConnectionActivity> mActivityTestRule = new IntentsTestRule<>(CheckConnectionActivity.class);
    private FirebaseAuth mAuth;
    private Random random = new Random();
    ArrayList<String> sexe = new ArrayList<>();


    public void fillSexeArray(){
        sexe.add("Homme");
        sexe.add("Femme");
        sexe.add("Indéterminé");
    }

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

        onView((withId(R.id.meButton))).perform(click());
        fillSexeArray();
        SystemClock.sleep(1000);
    }


    @After
    public void removeAuthAfterTest() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            mAuth.signOut();
    }

    @Test
    public void checkIntentUserLibrary() {
        onView(withId(R.id.btn_seeBooks)).perform(click());
        intending(hasComponent(hasShortClassName(".MyLibraryActivity")));
    }

    @Test
    public void checkIntentUserFriends() {
        onView(withId(R.id.btn_seeFriends)).perform(click());
        intending(hasComponent(hasShortClassName(".MyFriendsActivity")));
    }

    @Test
    public void checkEditPseudo_noError() {
        String fakePseudo = "TEST ANDROID"+ random();
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(1).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakePseudo), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(1).check(matches(hasDescendant(withText(fakePseudo))));
    }

    @Test
    public void checkEditSexe_noError() {
        String fakeSexe = sexe.get(random.nextInt(sexe.size()));
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(2).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakeSexe), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(2).check(matches(hasDescendant(withText(fakeSexe))));
    }

    @Test
    public void checkEditSexe_InputError() {
        String fakeSexe = "TestSexeIncorrect";
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(2).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakeSexe), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(2).check(matches(not(hasDescendant(withText(fakeSexe)))));
    }

    @Test
    public void checkEditEmailIsImpossible() {
        String fakeEmail = "TEST ANDROID"+ random();
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(0).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakeEmail), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(0).check(matches(not(withText(fakeEmail))));
    }

    @Test
    public void checkEditAge_noError() {
        String fakeAge = Integer.toString(random.nextInt(105));
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(3).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakeAge), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(3).check(matches(hasDescendant(withText(fakeAge))));
    }

    @Test
    public void checkEditAge_InputError() {
        String fakeAge = "TestModificationAge";
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(3).perform(click());
        onView(withId(R.id.et_paramValue)).perform(replaceText(fakeAge), closeSoftKeyboard());
        onView(withId(R.id.btn_saveParam)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listParam)).atPosition(3).check(matches(not(hasDescendant(withText(fakeAge)))));
    }
}
