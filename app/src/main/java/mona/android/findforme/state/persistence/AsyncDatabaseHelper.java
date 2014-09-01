package mona.android.findforme.state.persistence;

import mona.android.findforme.model.UserProfile;
import rx.Observable;

/**
 * Created by cheikhna on 31/08/2014.
 */
public interface AsyncDatabaseHelper {

    Observable<UserProfile> getUserProfile(String username); //int socialNetworkId,m

    void put(UserProfile profile);

    void delete(UserProfile profile);

    void close();

}
