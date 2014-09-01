package mona.android.findforme.ui;

import android.content.Context;
import android.widget.GridView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.FindForMeActivity;
import mona.android.findforme.LoginActivity;
import mona.android.findforme.ui.grid.GridContainer;

/**
 * Created by cheikhna on 16/08/2014.
 */
@Module(
    injects = {
        FindForMeActivity.class,
        GridView.class
    },
    complete = false,
    library = true
)
public final class UiModule {

    @Provides @Singleton
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }


}
