package mona.android.findforme.modules;

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
import mona.android.findforme.LoginActivity;
import mona.android.findforme.services.PhotoUploadTaskService;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;
import mona.android.findforme.util.AppUtils;
import timber.log.Timber;

/**
 * Created by cheikhna on 11/08/2014.
 */
@Module(
    injects = {
        FindForMeActivity.class,
        PhotoUploadTaskQueue.class,
        PhotoUploadTaskService.class,
        PhotoUploadTask.class
        //LoginActivity.class
    },
    library = true
)
class MainModule {
    private final Context appContext;

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    MainModule(Context context) {
        this.appContext = context;
    }

    @Provides
    @Singleton
    PhotoUploadTaskQueue provideTaskQueue(Gson gson, Bus bus) {
        return PhotoUploadTaskQueue.create(appContext, gson, bus);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    SocialNetworkManager provideSocialNetworkManager() {
        return SocialNetworkManager.Builder.from(appContext)
                .twitter(AppUtils.TWITTER_APP_ID, AppUtils.TWITTER_API_SECRET)
                .facebook()
                .googlePlus()
                .build();
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient() {
        return createOkHttpClient(appContext);
    }

    static OkHttpClient createOkHttpClient(Context appContext) {
        OkHttpClient client = new OkHttpClient();
        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(appContext.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            Timber.e(e, "Unable to install disk cache.");
        }
        return client;
    }

}
