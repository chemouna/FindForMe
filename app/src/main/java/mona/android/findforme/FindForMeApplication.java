package mona.android.findforme;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import dagger.ObjectGraph;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class FindForMeApplication extends Application {
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate(){
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //temporary for debugging
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .penaltyLog()
                    .build());
        } else {
            // TODO Crashlytics.start(this); or Crittercism
            // TODO Timber.plant(new CrashlyticsTree());
        }
        buildObjectGraphAndInject();
    }

    @DebugLog
    public void buildObjectGraphAndInject() {
        mObjectGraph = ObjectGraph.create(Modules.list(this));
        mObjectGraph.inject(this);
    }

    public void inject(Object object){
        mObjectGraph.inject(object);
    }

    public static FindForMeApplication get(Context context) {
        return (FindForMeApplication) context.getApplicationContext();
    }

}
