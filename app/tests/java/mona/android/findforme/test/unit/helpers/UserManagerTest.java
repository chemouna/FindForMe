package mona.android.findforme.test.unit.helpers;

import android.test.AndroidTestCase;

import mona.android.findforme.helpers.UserManager;
import mona.android.findforme.state.UserState;
import mona.android.findforme.state.persistence.AsyncDatabaseHelper;

import static org.mockito.Mockito.mock;

public class UserManagerTest extends AndroidTestCase {

    private UserState mUserState;
    private AsyncDatabaseHelper mDbHelper;
    private UserManager mUserManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().toString());
        mUserState = mock(UserState.class);
        mDbHelper = mock(AsyncDatabaseHelper.class);
        mUserManager = new UserManager(mUserState, mDbHelper);
    }

    /*public void testSetupUserProfile() {
        Observable<UserProfile> userProfileObservable = mock(Observable.class);
        when(mDbHelper.getUserProfile("monawheretwit")).thenReturn(userProfileObservable);
        //when(mUserState.getUserProfile()).thenReturn(null);

        mUserManager.start();
        assertEquals(mDbHelper.getUserProfile("monawheretwit"), userProfileObservable.toBlocking().single());
        assertEquals(mUserState.getUserProfile(), mDbHelper.getUserProfile("monawheretwit"));
    }*/


}
