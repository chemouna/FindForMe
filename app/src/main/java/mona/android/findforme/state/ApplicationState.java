package mona.android.findforme.state;


import android.support.annotation.Nullable;

import mona.android.findforme.model.UserProfile;

/**
 * Created by cheikhna on 31/08/2014.
 */
public final class ApplicationState implements UserState {

    private UserProfile mUserProfile;

    @Override
    public void setUserProfile(UserProfile profile) {
        //if (!Objects.equal(profile, mUserProfile)) { //-> add this if you need to issue an event for changed User Profile
            this.mUserProfile = profile;
        //}
    }

    @Override
    public UserProfile getUserProfile() {
        return mUserProfile;
    }

}
