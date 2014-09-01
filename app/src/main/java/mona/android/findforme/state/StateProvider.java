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
    library = true
    //includes = MainProvider.class
)
public final class StateProvider {

    //may need Bus bus to be injected in it
    @Provides @Singleton
    public ApplicationState provideApplicationState(){
        return new ApplicationState();
    }

    @Provides @Singleton
    public UserState provideUserState(ApplicationState state){
        return state;
    }

}
