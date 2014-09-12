package mona.android.findforme.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import mona.android.findforme.helpers.UserManager;
import mona.android.findforme.model.UserProfile;
import mona.android.findforme.state.UserState;
import mona.android.findforme.state.persistence.AsyncDatabaseHelper;
import rx.Observable;
import rx.functions.Action0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cheikhna on 07/09/2014.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "./app/src/main/AndroidManifest.xml", emulateSdk = 18)
public class UserManagerTest {

    private UserState mUserState;
    private AsyncDatabaseHelper mDbHelper;

    @Before
    public void setUp() throws Exception {
        mUserState = mock(UserState.class);
        mDbHelper = mock(AsyncDatabaseHelper.class);
    }

    @Test
    public void testSetupUserProfile() {
        Observable<UserProfile> userProfileObservable = mock(Observable.class);
        when(mDbHelper.getUserProfile("monawheretwit")).thenReturn(userProfileObservable);
        when(mUserState.getUserProfile()).thenReturn(null);

        //what happens if we set a mock to return some value and then later set it to something else ?
        UserManager usermanager = new UserManager(mUserState, mDbHelper);
        //usermanager.start();
        assertEquals(1, 1);
        //userProfileObservable.observeOn(Schedulers.immediate()).
        /*userProfileObservable.doOnCompleted(new Action0() {
            @Override
            public void call() {
                assertEquals(mUserState.getUserProfile(), mDbHelper.getUserProfile("monawheretwit"));
            }
        });
        assertTrue(false);*/
    }

}
