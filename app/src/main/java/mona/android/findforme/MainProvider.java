package mona.android.findforme;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.qualifiers.ApplicationContext;
import mona.android.findforme.socialnetwork.SocialNetworkFragment;
import mona.android.findforme.socialnetwork.SocialNetworksContract;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;

/**
 * Created by cheikhna on 11/08/2014.
 */
@Module(
    complete = false,
    library = true
)
public final class MainProvider {

    @Provides
    @Singleton
    PhotoUploadTaskQueue provideTaskQueue(@ApplicationContext Context context, Gson gson, Bus bus) {
        return PhotoUploadTaskQueue.create(context, gson, bus);
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
                    /*registerTypeAdapter(PhotoUploadTask.class,
                                new PhotoUploadTaskSerializer()).*/
                    create();
    }

    /*@Provides
    @Singleton
    SocialNetworkManager provideSocialNetworkManager(@ApplicationContext Context context) {
        return SocialNetworkManager.Builder.from(context)
                .twitter(AppUtils.TWITTER_APP_ID, AppUtils.TWITTER_API_SECRET)
                .facebook()
                .googlePlus()
                .build();
    }*/

    @Provides
    @Singleton
    SocialNetworksContract provideSocialNetworksContract() {
        return new SocialNetworkFragment();
    }

    //TODO: provide something like providePrivateFileDirectory to use with taking a phoro where we
    //create a file for taken bitmap photo


}