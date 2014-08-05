package mona.android.findforme;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import mona.android.findforme.services.PhotoUploadTaskService;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class FindForMeApplication extends Application {
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate(){
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new MainModule(this));
    }

    public void inject(Object object){
        mObjectGraph.inject(object);
    }

    @Module(
        injects = {
            FindForMeActivity.class,
            PhotoUploadTaskQueue.class,
            PhotoUploadTaskService.class
        }
    )
    static class MainModule {
        private final Context appContext;

        MainModule(Context context){
            this.appContext = context;
        }

        @Provides @Singleton
        PhotoUploadTaskQueue provideTaskQueue(Gson gson, Bus bus){
            return PhotoUploadTaskQueue.create(appContext, gson, bus);
        }

        @Provides @Singleton
        Bus provideBus(){
            return new Bus();
        }

        @Provides @Singleton Gson provideGson() {
            return new GsonBuilder().create();
        }

    }

}
