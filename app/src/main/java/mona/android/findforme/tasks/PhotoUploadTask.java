package mona.android.findforme.tasks;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.data.api.FindForMeService;
import mona.android.findforme.tape.Task;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import timber.log.Timber;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask extends Task<PhotoUploadTask.Callback> {

    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
    private static final String STATUS_SUCCESS = "success";

    private File mFile;

    @Inject
    transient FindForMeService mService;

    public PhotoUploadTask(File file) {
        this.mFile = file;
    }

    public interface Callback {
        void onSuccess();

        void onFailure();
    }

    @Override
    public void execute(final Context context, final Callback callback) {
        super.execute(context, callback);

        FindForMeApplication.get(context).inject(this);

        //maybe there's no need to create a new thread
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
