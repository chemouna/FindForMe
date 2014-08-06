package mona.android.findforme.tasks;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.MimeTypeMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.tape.Task;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import mona.android.findforme.services.FindForMeService;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final long serialVersionUID = 1L;

    private static final String FIND_FOR_ME_BACKEND = "http://findforme-backend.herokuapp.com/";
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private final File mFile;

    public PhotoUploadTask(File file){
        this.mFile = file;
    }

    public PhotoUploadTask(Context context, File file){
        /*try {
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
            Cache cache = new Cache(cacheDirectory, cacheSize);
            OkHttpClient client = new OkHttpClient();
            client.setCache(cache);

            mRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(FIND_FOR_ME_BACKEND)
                    .setClient(new OkClient(client))
                    .build();
            mService = mRestAdapter.create(FindForMeService.class);
        }
        catch(IOException e){*/
            //init();
        //}
        this.mFile = file;
    }

    public interface Callback {
        void onSuccess();
        void onFailure();
    }

    @Override
    public void execute(final Callback callback) {
        //TODO: add code here to send photo to server
        //maybe use retrofit's with POST
        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(FIND_FOR_ME_BACKEND)
                .build();
        final FindForMeService mService = mRestAdapter.create(FindForMeService.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = mService.uploadPhoto(new TypedFile("images/png", mFile));
                if (result) {
                    MAIN_THREAD.post(new Runnable() {
                         @Override
                         public void run() {
                             callback.onSuccess();
                         }
                     });
                } else {
                    MAIN_THREAD.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure();
                        }
                    });
                }
            }
        }).start();
    }

}
