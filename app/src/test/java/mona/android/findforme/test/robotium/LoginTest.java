package mona.android.findforme.test.robotium;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.squareup.spoon.Spoon;

import mona.android.findforme.FindForMeActivity;
import mona.android.findforme.LoginActivity;
import mona.android.findforme.R;

/**
 * Created by cheikhna on 07/09/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testTwitterLogin() throws Exception {
        solo.clickOnButton(getActivity().getString(R.string.login_twitter));
        By byLoginUsername = By.xpath("//input[@type='text'][1]");
        solo.waitForWebElement(byLoginUsername);

        takeScreenshot("twitter_login_page");
        solo.typeTextInWebElement(byLoginUsername, "monawheretwit");

        By byLoginPasswd = By.xpath("//input[@type='password'][1]");
        solo.typeTextInWebElement(byLoginPasswd, "monappandroidwheretwit");
        takeScreenshot("twitter_credentials_entered");

        solo.clickOnWebElement(By.xpath("//input[@type='submit'][1]"));
        //getInstrumentation().waitForIdleSync();
        solo.assertCurrentActivity("wrong activity after login", FindForMeActivity.class);
    }

    public void testFacebookLogin() {
        solo.clickOnButton(getActivity().getString(R.string.login_facebook));
        By byMail = By.xpath("//input[@type='text'][1]");
        solo.waitForWebElement(byMail);
        solo.typeTextInWebElement(byMail, "cheikhnemouna@yahoo.fr");
        By byPassword = By.xpath("//input[@type='password'][1]");
        solo.typeTextInWebElement(byPassword, "Mauritanie1");
        solo.clickOnWebElement(By.xpath("//button[@type='submit'][1]"));
        solo.assertCurrentActivity(" wrong activity after login", FindForMeActivity.class);
    }

    /*public void testGooglePlusLogin() {
        solo.clickOnButton(getActivity().getString(R.string.login_googleplus));
        //solo.waitForText("tweetrplus@gmail.com");
        solo.clickOnText("tweetrplus@gmail.com");
        solo.clickOnButton(getActivity().getString(android.R.string.yes));
        assertFalse(solo.waitForText(getActivity().getString(R.string.login_internal_error)));
    }*/

    public void takeScreenshot(String imageName) {
        getInstrumentation().waitForIdleSync();
        Spoon.screenshot(solo.getCurrentActivity(), imageName);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
