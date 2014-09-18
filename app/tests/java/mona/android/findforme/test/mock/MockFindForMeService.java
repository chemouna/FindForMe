package mona.android.findforme.test.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mona.android.findforme.data.api.FindForMeService;
import mona.android.findforme.data.api.Type;
import mona.android.findforme.data.api.model.FindItem;
import mona.android.findforme.data.api.model.ListResponse;
import mona.android.findforme.data.api.model.Sort;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.http.Part;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by cheikhna on 14/09/2014.
 */
public class MockFindForMeService implements FindForMeService {

    private Map<Type, Map<Sort, List<FindItem>>> mSavedItems; //page not represented here ?!

    //TODO: maybe move to a json stubbed client(like http://www.mdswanson.com/blog/2014/02/24/integration-testing-rest-apis-for-android.html)
    public MockFindForMeService(){
        //type = clothing ; popular, page 1

        mSavedItems = new LinkedHashMap<Type, Map<Sort, List<FindItem>>>();

        addItem(Type.CLOTHING, Sort.POPULAR, "files/findforme1407298136.png", "2014-08-27T11:21:52.000Z",
                "https://s3.amazonaws.com/findforme-backend-storage/files/findforme1407298136.png", 100, 100);

        addItem(Type.CLOTHING, Sort.POPULAR, "files/findforme1407527493.png", "2014-08-09T21:19:44.000Z",
                "https://s3.amazonaws.com/findforme-backend-storage/files/findforme1407527493.png", 100, 100);

        addItem(Type.CLOTHING, Sort.POPULAR, "files/findforme1407619299.png", "2014-08-09T21:35:38.000Z",
                "https://s3.amazonaws.com/findforme-backend-storage/files/findforme1407619299.png", 100, 100);
    }

    private void addItem(Type type, Sort sort, String key, String lastModified,
                                        String imageLink, int width, int height) {
        Map<Sort, List<FindItem>> sortedItems = mSavedItems.get(type);
        if (sortedItems == null) {
            sortedItems = new LinkedHashMap<Sort, List<FindItem>>();
            mSavedItems.put(type, sortedItems);
        }
        List<FindItem> items = sortedItems.get(sort);
        if (items == null) {
            items = new ArrayList<FindItem>();
            sortedItems.put(sort, items);
        }
        items.add(new FindItem(key, lastModified, imageLink, width, height));
    }

    public Observable<ListResponse> listItems(final Type type, final Sort sort, int page) {
        return Observable.create(new Observable.OnSubscribe<ListResponse>() {
            @Override
            public void call(final Subscriber<? super ListResponse> s) {
                ListResponse response = new ListResponse(200, true, mSavedItems.get(type).get(sort));
                s.onNext(response);
                s.onCompleted();
            }
        });
    }

    public String uploadPhoto(@Part("file") TypedFile photo) {
        return null;
    }

    public List<FindItem> getItems(Type type, Sort sort) {
        return mSavedItems.get(type).get(sort);
    }

}