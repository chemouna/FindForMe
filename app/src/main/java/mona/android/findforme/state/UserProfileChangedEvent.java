package mona.android.findforme.state;

import mona.android.findforme.model.UserProfile;

/**
 * Created by cheikhna on 14/09/2014.
 */
public class UserProfileChangedEvent {

    private UserProfile profile;

    public UserProfileChangedEvent(UserProfile profile){
        this.profile = profile;
    }

    public UserProfile getUserProfile() {
        return profile;
    }

}