package mona.android.findforme.services;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by cheikhna on 04/08/2014.
 */
public interface FindForMeService {

    @Multipart
    @POST("/photo")
    boolean uploadPhoto(@Part("photo") TypedFile photo);
    /*@PUT("/addPhoto")
    boolean uploadPhoto(@Part("photo") TypedFile photo, @Part("filename") TypedString filename,
                    @Part("mimeType") TypedString mimeType);*/

}
