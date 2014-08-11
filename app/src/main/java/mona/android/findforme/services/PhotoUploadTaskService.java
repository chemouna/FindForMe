package mona.android.findforme.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.events.PhotoUploadSuccessEvent;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTaskService extends Service implements PhotoUploadTask.Callback {

    public static final String TAG = "PhotoUploadTaskService";

    @Inject PhotoUploadTaskQueue mQueue;
    @Inject Bus mBus;

    private boolean mRunning = false;

    @Override
    public void onCreate(){
        super.onCreate();
        ((FindForMeApplication) getApplication()).inject(this);
    }

    @DebugLog
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        executeNext();
        return START_STICKY;
    }

    @DebugLog
    private void executeNext(){
        if (mRunning) return; // Only one task at a time.

        PhotoUploadTask task = mQueue.peek();
        if (task != null) {
            task.execute(this);
            mRunning = true;
        } else {
            Log.i(TAG, "Service stopping!");
            stopSelf(); // No more tasks are present. Stop.
        }
    }

    @DebugLog
    @Override
    public void onSuccess() {
        Log.i("TEST", " onSuccess ");
        mRunning = false;
        mQueue.remove();
        mBus.post(new PhotoUploadSuccessEvent());
        executeNext();
    }

    @DebugLog
    @Override
    public void onFailure() {
        Log.i("TEST", " Upload failure ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}