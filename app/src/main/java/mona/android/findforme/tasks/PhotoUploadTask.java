package mona.android.findforme.tasks;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.tape.Task;

import java.io.File;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import mona.android.findforme.services.FindForMeService;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final long serialVersionUID = 126142781146165256L;

    private static final String FIND_FOR_ME_BACKEND = "http://findforme-backend.herokuapp.com";
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private final File mFile;

    @Inject
    OkHttpClient mOkHttpClient;

    public PhotoUploadTask(File file) {
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

        //OkHttpClient client = new OkHttpClient();
        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(FIND_FOR_ME_BACKEND)
                .setClient(new OkClient(mOkHttpClient))
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
