package mona.android.findforme;

import android.app.Application;
import android.content.Context;

import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.FindForMeActivity;
import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.LoginActivity;
import mona.android.findforme.services.PhotoUploadTaskService;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;
import mona.android.findforme.tasks.PhotoUploadTaskSerializer;
import mona.android.findforme.util.AppUtils;
import timber.log.Timber;

/**
 * Created by cheikhna on 11/08/2014.
 */
@Module(
    complete = false,
    library = true
)
public final class MainModule {

    @Provides
    @Singleton
    PhotoUploadTaskQueue provideTaskQueue(Application app, Gson gson, Bus bus) {
        return PhotoUploadTaskQueue.create(app, gson, bus);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().
                    registerTypeAdapter(PhotoUploadTask.class,
                                new PhotoUploadTaskSerializer()).
                    create();
    }

    @Provides
    @Singleton
    SocialNetworkManager provideSocialNetworkManager(Application app) {
        return SocialNetworkManager.Builder.from(app)
                .twitter(AppUtils.TWITTER_APP_ID, AppUtils.TWITTER_API_SECRET)
                .facebook()
                .googlePlus()
                .build();
    }

}
