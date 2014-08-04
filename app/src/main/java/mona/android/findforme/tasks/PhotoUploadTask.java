package mona.android.findforme.tasks;

import android.webkit.MimeTypeMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.tape.Task;

import java.io.File;
import java.util.Date;

import mona.android.findforme.services.FindForMeService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;

/**
 * Created by cheikhna on 03/08/2014.
 */
public class PhotoUploadTask implements Task<PhotoUploadTask.Callback> {

    private static final long serialVersionUID = 126142781146165256L; //TODO: generate a new specific serial id
    private static final String FIND_FOR_ME_BACKEND = "http://findforme-backend.herokuapp.com/";

    private final File mFile;

    private RestAdapter mRestAdapter = new RestAdapter.Builder()
                     .setEndpoint(FIND_FOR_ME_BACKEND)
                     .build();

    private FindForMeService mService = mRestAdapter.create(FindForMeService.class);

    public PhotoUploadTask(File file){
        this.mFile = file;
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
