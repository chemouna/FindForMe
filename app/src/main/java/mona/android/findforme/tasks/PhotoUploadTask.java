package mona.android.findforme.tasks;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.tape.Task;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;

import hugo.weaving.DebugLog;
import mona.android.findforme.services.FindForMeService;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import static com.github.kevinsawicki.http.HttpRequest.post;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final long serialVersionUID = 126142781146165256L;

    private static final String FIND_FOR_ME_BACKEND = "http://findforme-backend.herokuapp.com";
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private final File mFile;

    public PhotoUploadTask(File file){
        this.mFile = file;
    }

    public interface Callback {
        void onSuccess();
        void onFailure();
    }

    @DebugLog
    @Override
    public void execute(final Callback callback) {
        //TODO: add code here to send photo to server
        //maybe use retrofit's with POST

        OkHttpClient client = new OkHttpClient();
        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(FIND_FOR_ME_BACKEND)
                .setClient(new OkClient(client))
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
                }
                else {
                    MAIN_THREAD.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure();
                        }
                    });
                }
            }
        }).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest request = post(FIND_FOR_ME_BACKEND)
                            .part("photo", mFile);

                    if (request.ok()) {
                        Log.i("TEST", "Upload success! ");

                        // Get back to the main thread before invoking a callback.
                        MAIN_THREAD.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess();
                            }
                        });
                    } else {
                        Log.i("TEST", "Upload failed :(  Will retry.");
                        // Get back to the main thread before invoking a callback.
                        MAIN_THREAD.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure();
                            }
                        });
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }).start();*/

    }

}
