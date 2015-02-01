package mona.android.findforme.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.FindForMeActivity;
import mona.android.findforme.LoginActivity;
import mona.android.findforme.data.api.FindForMeService;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.ui.grid.GridContainer;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import timber.log.Timber;

/**
 * Created by cheikhna on 11/08/2014.
 */
@Module(
    complete = false,
    library = true
)
public final class DataModule {

    private static final String FIND_FOR_ME_BACKEND = "your-backend-url-here";
    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("FindForMe", Context.MODE_PRIVATE);
    }

    @Provides @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
            .downloader(new OkHttpDownloader(client))
            .listener(new Picasso.Listener() {
                @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                    Timber.e(e, "Failed to load image: %s", uri);
                }
            })
            .build();
    }

    @Provides @Singleton
    Endpoint provideEndpoint(){
        return Endpoints.newFixedEndpoint(FIND_FOR_ME_BACKEND);
    }

    @Provides @Singleton
    Client provideClient(OkHttpClient client){
        return new OkClient(client);
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client){
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(client)
                .setEndpoint(endpoint)
                .build();
    }

    @Provides @Singleton
    //@Named("realService")
    FindForMeService provideFindForMeService(RestAdapter restAdapter){
        return restAdapter.create(FindForMeService.class);
    }

    @Provides @Singleton
    ItemsLoader provideItemsLoader(FindForMeService service){
        return new ItemsLoader(service);
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient(Application app) {
        return createOkHttpClient(app);
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