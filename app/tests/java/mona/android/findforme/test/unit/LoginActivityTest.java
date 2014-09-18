package mona.android.findforme.test.unit;

import android.test.ActivityInstrumentationTestCase2;

import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mona.android.findforme.LoginActivity;

import static org.mockito.Mockito.mock;

/**
 * Created by cheikhna on 13/09/2014.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    //private SocialNetworkManager mSocialNetworkManager;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }


}
