package mona.android.findforme.state.persistence;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.FindForMeModule;
import mona.android.findforme.MainProvider;
import mona.android.findforme.qualifiers.ApplicationContext;

/**
 * Created by cheikhna on 31/08/2014.
 */
@Module(
    library = true,
    complete = false
)
public final class PersistenceProvider {

    @Provides @Singleton
    public DatabaseHelper provideDatabaseHelper(@ApplicationContext Context context){
        return new FindForMeSQLiteOpenHelper(context);
    }

    //TODO: may rename AsyncDatabaseHelper to ObservableDatabaseHelper to be more accurate
    @Provides @Singleton
    public AsyncDatabaseHelper provideAsyncDatabaseHelper(DatabaseHelper databaseHelper){
        return new AsyncDatabaseHelperImpl(databaseHelper);
    }


}
