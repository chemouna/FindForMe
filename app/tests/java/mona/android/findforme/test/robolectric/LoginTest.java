package mona.android.findforme.test.robolectric;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import mona.android.findforme.LoginActivity;
import mona.android.findforme.R;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by cheikhna on 05/09/2014.
 */
@Config(manifest = "../src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class LoginTest {
    private Context context;
    private Activity activity;
    @Before
    public void setup() {
        ShadowToast.reset();
        context = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void testFacebookLogin(){
        Button loginFbButton = (Button) activity.findViewById(R.id.ib_login_facebook);
        loginFbButton.performClick();
        assertNotEquals(context.getString(R.string.click_fb), ShadowToast.getTextOfLatestToast());
    }

}
