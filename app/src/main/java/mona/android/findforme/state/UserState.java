package mona.android.findforme.state;

import mona.android.findforme.model.UserProfile;

/**
 * Created by cheikhna on 31/08/2014.
 */
public interface UserState {

    public void setUserProfile(UserProfile profile);

    public UserProfile getUserProfile();

    public static class UserProfileChangedEvent {

    }

}
