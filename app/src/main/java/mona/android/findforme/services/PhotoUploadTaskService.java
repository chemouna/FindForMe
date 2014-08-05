package mona.android.findforme.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.events.PhotoUploadSuccessEvent;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTaskService extends Service implements PhotoUploadTask.Callback {

    @Inject PhotoUploadTaskQueue mQueue;
    @Inject Bus mBus;

    private boolean mRunning = false;

    @Override
    public void onCreate(){
        super.onCreate();
        ((FindForMeApplication) getApplication()).inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        executeNext();
        return START_STICKY;
    }

    private void executeNext(){

    }

    @Override
    public void onSuccess(String url) {
        mRunning = false;
        mQueue.remove();
        mBus.post(new PhotoUploadSuccessEvent(url));
        executeNext();
    }

    @Override
    public void onFailure() {
        Log.i("TEST", " Upload failure ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}