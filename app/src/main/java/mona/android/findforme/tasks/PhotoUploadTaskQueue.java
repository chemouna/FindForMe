package mona.android.findforme.tasks;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.tape.FileObjectQueue;
import com.squareup.tape.ObjectQueue;

import java.io.File;
import java.io.IOException;

import hugo.weaving.DebugLog;
import mona.android.findforme.events.PhotoUploadQueueSizeEvent;
import mona.android.findforme.services.PhotoUploadTaskService;
import mona.android.findforme.tape.TaskQueue;
import mona.android.findforme.tape.GsonConverter;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTaskQueue extends TaskQueue<PhotoUploadTask> {

    private static final String FILENAME = "photo_upload_task_queue";

    private final Context context;

    private final Bus bus;

    //TODO: make bus, context injectable and not depend it on them in constructor
    public PhotoUploadTaskQueue(ObjectQueue<PhotoUploadTask> delegate, Context context, Bus bus){
        super(delegate);
        this.context = context;
        this.bus = bus;
        bus.register(this);
        if(size() > 0){
            startService();
        }
    }

    @DebugLog
    private void startService() {
        context.startService(new Intent(context, PhotoUploadTaskService.class));
    }

    @DebugLog
    @Override
    public void add(PhotoUploadTask entry){
        super.add(entry);
        bus.post(produceSizeChanged());
        startService();
    }

    @DebugLog
    @Override
    public void remove(){
        super.remove();
        bus.post(produceSizeChanged());
    }

    @SuppressWarnings("UnusedDeclaration") // Used by event bus.
    @Produce
    public PhotoUploadQueueSizeEvent produceSizeChanged() {
        return new PhotoUploadQueueSizeEvent(size());
    }

    @DebugLog
    public static PhotoUploadTaskQueue create(Context context, Gson gson, Bus bus) {
        FileObjectQueue.Converter<PhotoUploadTask> converter = new GsonConverter<PhotoUploadTask>(gson, PhotoUploadTask.class);
        File queueFile = new File(context.getFilesDir(), FILENAME);
        FileObjectQueue<PhotoUploadTask> delegate;
        try {
            delegate = new FileObjectQueue<PhotoUploadTask>(queueFile, converter);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file queue.", e);
        }
        return new PhotoUploadTaskQueue(delegate, context, bus);
    }

}