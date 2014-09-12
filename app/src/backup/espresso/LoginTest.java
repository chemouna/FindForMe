package mona.android.findforme.test.espresso;

import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

import mona.android.findforme.LoginActivity;
import mona.android.findforme.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * Created by cheikhna on 05/09/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    /*public void testFacebookLogin(){
        onView(withId(R.id.ib_login_facebook)).perform(click());
        Spoon.screenshot(getActivity(), "click_login_fb");
        Spoon.screenshot(getActivity(), "toast_clicked_fb");
    }*/

    public void testTwitterLogin(){
        onView(withId(R.id.ib_login_twitter)).perform(click());
        Spoon.screenshot(getActivity(), "click_login_twitter");

        onView(withId(getInstrumentation().getTargetContext().getResources()
                .getIdentifier("com.twitter.android:id/sign_in", null, null)))
                .perform(click());

        onView(withId(getInstrumentation().getTargetContext().getResources()
                .getIdentifier("com.twitter.android:id/login_username", null, null)))
        .perform((typeText("username")));

        onView(withId(getInstrumentation().getTargetContext().getResources()
                .getIdentifier("com.twitter.android:id/login_password", null, null)))
                .perform((typeText("password")));

        onView(withId(getInstrumentation().getTargetContext().getResources()
                .getIdentifier("com.twitter.android:id/login_login", null, null)))
                .perform(click());

    }

}
