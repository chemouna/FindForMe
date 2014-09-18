package mona.android.findforme.helpers;

import javax.inject.Inject;
import javax.inject.Singleton;

import mona.android.findforme.model.UserProfile;
import mona.android.findforme.state.UserState;
import mona.android.findforme.state.persistence.AsyncDatabaseHelper;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by cheikhna on 06/09/2014.
 */
@Singleton
public class UserManager { //not sure if this is the correct name -> TODO: refactor it if you find a better name

    private final UserState mUserState;
    private final AsyncDatabaseHelper mDbHelper;

    //TODO: create an interface for these method common to ***Manager

    @Inject
    public UserManager(UserState userState,
                        AsyncDatabaseHelper dbHelper) {
        mUserState = userState;
        mDbHelper = dbHelper;
    }

    public final void start(){
        onStart();
    }

    protected void onStart(){
        mUserState.registerForEvents(this);

        final UserProfile userProfile = mUserState.getUserProfile();
        if(userProfile == null) {
            //from where to get username ?
            Observable<UserProfile> userProfileObservable = mDbHelper.getUserProfile("monawheretwit"); //en dure temp
            userProfileObservable.doOnNext(new Action1<UserProfile>() {
                @Override
                public void call(UserProfile profile) {
                    mUserState.setUserProfile(profile);
                }
            });
        }
        //TODO: when to unsubscribe from userProfileSubscription
    }

    public final void stop(){
        onStop();
    }

    protected void onStop(){
        mUserState.unregisterForEvents(this);
    }

}