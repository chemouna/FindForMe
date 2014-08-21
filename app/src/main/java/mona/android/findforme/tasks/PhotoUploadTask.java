package mona.android.findforme.tasks;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.tape.Task;

import java.io.File;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import mona.android.findforme.data.api.FindForMeService;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import timber.log.Timber;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final long serialVersionUID = 126142781146165256L;

    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
    private static final String STATUS_SUCCESS = "success";

    private final File mFile;

    @Inject
    FindForMeService mService;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uploadedStatus = mService.uploadPhoto(new TypedFile("images/png", mFile));
                Timber.d(" uploadedStatus : %s ", uploadedStatus);
                boolean result = uploadedStatus.equals(STATUS_SUCCESS);
                //temporary
                Timber.d("result : "+ result);
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
