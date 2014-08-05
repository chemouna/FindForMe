package mona.android.findforme.tasks;

import android.content.Context;
import android.webkit.MimeTypeMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final String FIND_FOR_ME_BACKEND = "http://findforme-backend.herokuapp.com/";

    private final File mFile;
    private RestAdapter mRestAdapter;
    private FindForMeService mService;

    public PhotoUploadTask(File file){
        this.mFile = file;
        init();
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
            init();
        //}
        this.mFile = file;
    }

    public void init(){
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(FIND_FOR_ME_BACKEND)
                .build();
        mService = mRestAdapter.create(FindForMeService.class);
    }

    public interface Callback {
        void onSuccess(String url);
        void onFailure();
    }

    @Override
    public void execute(Callback callback) {
        //TODO: add code here to send photo to server
        //maybe use retrofit's with POST
        mService.uploadPhoto(new TypedFile("images/png", mFile));
    }

}
