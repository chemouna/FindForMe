package mona.android.findforme.state.persistence;

import mona.android.findforme.model.UserProfile;

/**
 * Created by cheikhna on 31/08/2014.
 */
public interface DatabaseHelper {

    UserProfile getUserProfile(String username);

    void put(UserProfile profile);

    void delete(UserProfile profile);

    void close();

    boolean isClosed();

}
