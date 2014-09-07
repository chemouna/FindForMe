package mona.android.findforme.state;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.MainProvider;

/**
 * Created by cheikhna on 31/08/2014.
 */
@Module(
    library = true,
    complete = false
)
public final class StateProvider {

    @Provides @Singleton
    public ApplicationState provideApplicationState(Bus bus){
        return new ApplicationState(bus);
    }

    @Provides @Singleton
    public UserState provideUserState(ApplicationState state){
        return state;
    }

}
