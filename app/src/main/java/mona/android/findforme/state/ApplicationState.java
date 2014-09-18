package mona.android.findforme.state;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.squareup.otto.Bus;

import mona.android.findforme.model.UserProfile;

/**
 * Created by cheikhna on 31/08/2014.
 */
public final class ApplicationState implements UserState {

    private UserProfile mUserProfile;

    private final Bus mBus;

    public ApplicationState(Bus bus){
        mBus = bus;
    }

    @Override
    public void setUserProfile(UserProfile profile) {
        Preconditions.checkNotNull(profile, " Profile cannot be null");
        if (!Objects.equal(profile, mUserProfile)) {
            this.mUserProfile = profile;
            mBus.post(new UserProfileChangedEvent(profile));
        }
    }

    @Override
    public void registerForEvents(Object receiver) {
        mBus.register(receiver);
    }

    @Override
    public void unregisterForEvents(Object receiver) {
        mBus.unregister(receiver);
    }

    @Override
    public UserProfile getUserProfile() {
        return mUserProfile;
    }

}
