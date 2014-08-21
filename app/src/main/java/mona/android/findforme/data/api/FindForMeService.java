package mona.android.findforme.data.api;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

import mona.android.findforme.data.api.model.*;
import rx.Observable;


/**
 * Created by cheikhna on 04/08/2014.
 */
public interface FindForMeService {

    //TODO: look into maybe here String should be replaced by an Observable result ?
    //because this synchronuous , we can use observable to perform async call
    @Multipart
    @POST("/upload")
    String uploadPhoto(@Part("file") TypedFile photo);

    @GET("/items/{type}/{sort}/{page}")
    Observable<ListResponse> listItems(@Path("type") Type type, @Path("sort") Sort sort, @Path("page") int page);

}
