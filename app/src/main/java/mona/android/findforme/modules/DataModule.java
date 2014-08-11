package mona.android.findforme.modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.LoginActivity;

/**
 * Created by cheikhna on 11/08/2014.
 */
@Module(
    injects = {
        LoginActivity.class
    },
    includes = MainModule.class
)
class DataModule {

    private final Context appContext;

    DataModule(Context context){
        this.appContext = context;
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences() {
        return appContext.getSharedPreferences("FindForMe", Context.MODE_PRIVATE);
    }

}
