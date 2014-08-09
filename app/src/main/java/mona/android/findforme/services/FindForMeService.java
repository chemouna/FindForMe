package mona.android.findforme.services;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by cheikhna on 04/08/2014.
 */
public interface FindForMeService {

    @Multipart
    @POST("/files")
    boolean uploadPhoto(@Part("file") TypedFile photo);
    //@Part("contentLength") TypedString contentLength);

}
