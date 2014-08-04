package mona.android.findforme.services;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by cheikhna on 04/08/2014.
 */
public interface FindForMeService {

    @Multipart
    @POST("/photo")
    void uploadPhoto(@Part("photo") TypedFile photo);

}
